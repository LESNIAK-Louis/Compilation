package zoot.arbre.instructions;

import zoot.arbre.expressions.Constante;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.exceptions.AnalyseSyntaxiqueException;
import zoot.tds.*;
import zoot.types.Type;

import java.util.List;

public class Affectation extends Instruction {
    private String idf;
    private int deplacement;
    private Expression droite;
    private int blocDeclaration;

    public Affectation(String idf, Expression droite, int n) {
        super(n);

        this.idf = idf;
        this.droite = droite;
    }

    @Override
    public void verifier() throws AnalyseException {

        StringBuilder message = new StringBuilder();

        int blocActuel = TDS.getInstance().getBlocActuel();
        Symbole s = TDS.getInstance().identifier(new EntreeVariable(idf, blocActuel));
        if(s == null){
            s = TDS.getInstance().identifier(new EntreeVariable(idf, 0));
            if(s == null){
                message.append((new AnalyseSemantiqueException(getNoLigne(), "identifiant " + idf + " non déclaré").getMessage())).append("\n");
            }else{
                blocDeclaration = 0;
            }
        }else{
            blocDeclaration = blocActuel;
        }

        this.deplacement = ((SymboleVariable)s).getDeplacement();

        droite.verifier();
        if(!s.getType().equals(droite.getType())) {
            message.append((new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage())).append("\n");
        }

        if(message.length() > 0)
            throw new AnalyseException(message.toString()){};
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        sb.append(droite.toMIPS(registres));

        String base = blocDeclaration == 0 ? "$s3" : "$s7";
        int dep = blocDeclaration == 0 ? deplacement-12 : deplacement;

        sb.append("sw $v0, ").append(dep).append("("+base+")\n");

        return sb.toString();
    }
}