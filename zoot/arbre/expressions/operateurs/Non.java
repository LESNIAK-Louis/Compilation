package zoot.arbre.expressions.operateurs;

import zoot.Compteur;
import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Unaire;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.types.Booleen;
import zoot.types.Type;

import java.util.List;

public class Non extends Unaire {

    public Non(Expression e, int n) {
        super(e, n);
    }
    @Override
    public void verifier() throws AnalyseException {
        StringBuilder message = new StringBuilder();

        try{
            e.verifier();
        } catch (AnalyseException exp) {
            message.append(exp.getMessage()).append("\n");
        }

        if(!e.getType().equals(new Booleen()))
            message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");

        if(message.length() > 0){
            throw new AnalyseException(message.toString()) {};
        }
    }

    @Override
    public String toMIPS(List<String> registres) {
        StringBuilder sb = new StringBuilder();

        int nb = Compteur.getInstance().nombreUnique();

        sb.append(this.e.toMIPS(registres));

        sb.append("beq $v0, $zero, non").append(nb).append("\n");
        sb.append("li $v0, 0\n");
        sb.append("j fin_non").append(nb).append("\n");
        sb.append("non").append(nb).append(": li $v0, 1\n");
        sb.append("fin_non").append(nb).append(": \n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return new Booleen();
    }
}
