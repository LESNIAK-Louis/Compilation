package zoot.arbre.expressions;

import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.tds.*;
import zoot.types.Type;

import java.util.List;

public class Variable extends Expression {

    private String idf;
    private Type type;
    private int deplacement;
    private int blocDeclaration;

    public Variable(String idf, int n) {
        super(n);

        this.idf = idf;
    }

    @Override
    public void verifier() throws AnalyseException {

        int blocActuel = TDS.getInstance().getBlocActuel();

        Symbole s = TDS.getInstance().identifier(new EntreeVariable(idf, blocActuel));
        if(s == null){
            s = TDS.getInstance().identifier(new EntreeVariable(idf, 0));
            if(s == null){

                throw new AnalyseSemantiqueException(getNoLigne(), "identifiant " + idf + " non déclaré");
            }else{
                blocDeclaration = 0;
            }
        }else{
            blocDeclaration = blocActuel;
        }
        type = s.getType();
        deplacement = ((SymboleVariable)s).getDeplacement();
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();

        String base = blocDeclaration == 0 ? "$s3" : "$s7";
        int dep = blocDeclaration == 0 ? deplacement-12 : deplacement;

        sb.append("lw $v0, ").append(dep).append("("+base+")\n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return type;
    }
}
