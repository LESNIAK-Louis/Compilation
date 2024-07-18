package zoot.arbre.expressions.operateurs;

import zoot.arbre.expressions.Binaire;
import zoot.arbre.expressions.Expression;
import zoot.arbre.expressions.Unaire;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.types.Entier;
import zoot.types.Type;

import java.util.List;

public class Moins extends Unaire{


    public Moins(Expression e, int n) {
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

        if(!e.getType().equals(new Entier()))
            message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");

        if(message.length() > 0){
            throw new AnalyseException(message.toString()) {};
        }
    }

    @Override
    public String toMIPS(List<String> registres) {
        StringBuilder sb = new StringBuilder();

        sb.append(this.e.toMIPS(registres));
        sb.append("subu $v0, $zero, $v0\n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return new Entier();
    }
}
