package zoot.arbre.expressions;

import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.tds.*;
import zoot.types.Type;

import java.util.ArrayList;
import java.util.List;

public class AppelFonction extends Expression {

    private String idf;
    private ArrayList<Expression> params;
    private Type type;

    public AppelFonction(String idf, ArrayList<Expression> params, int n) {
        super(n);

        this.idf = idf;
        this.params = params;
    }

    @Override
    public void verifier() throws AnalyseException {

        SymboleFonction s = (SymboleFonction)TDS.getInstance().identifier(new EntreeFonction(idf, 0, params.size()));

        StringBuilder message = new StringBuilder();

        if(s == null){
            message.append((new AnalyseSemantiqueException(getNoLigne(), "identifiant " + idf + " avec " + params.size() + " paramètres non déclaré\n")).getMessage());
        }else {
            for (int i = 0; i < params.size(); i++) {
                try {
                    params.get(i).verifier();
                    if (!s.getTypeParam(i).equals(params.get(i).getType())) {
                        message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");
                    }
                } catch (AnalyseException exp) {
                    message.append(exp.getMessage()).append("\n");
                }
            }
            type = s.getType();
        }
        if(message.length() > 0){

            throw new AnalyseException(message.toString()) {};
        }
    }

    @Override
    public int getNombreDePlaces(){
        return 5;
    }

    @Override
    public String toMIPS(List<String> registres) {
        SymboleFonction s = (SymboleFonction)TDS.getInstance().identifier(new EntreeFonction(idf, 0, params.size()));
        int etiquette = s.getEtiquette();

        StringBuilder sb = new StringBuilder();

        sb.append("addi $sp, $sp, -4 # Réserver la place pour le résultat de la fonction\n");

        for(int i = 0; i < s.getNbParams(); i++){
            sb.append(params.get(i).toMIPS(registres));
            sb.append("sw $v0, 0($sp) # Empilement du paramètre\n");
            sb.append("addi $sp, $sp, -4\n");
        }

        sb.append("jal ").append("etif").append(etiquette).append(" # Appel à la fonction\n");

        sb.append("lw $v0, 0($sp) # Sauvegarde du résultat dans v0\n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return type;
    }
}
