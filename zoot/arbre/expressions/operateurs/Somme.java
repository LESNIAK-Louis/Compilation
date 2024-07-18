package zoot.arbre.expressions.operateurs;

import zoot.arbre.expressions.Binaire;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.types.Entier;
import zoot.types.Type;

import java.util.List;

public class Somme extends Binaire {


    public Somme(Expression e1, Expression e2, int n) {
        super(e1, e2, n);
    }

    @Override
    public void verifier() throws AnalyseException {
        StringBuilder message = new StringBuilder();

        try{
            e1.verifier();
        } catch (AnalyseException exp) {
            message.append(exp.getMessage()).append("\n");
        }
        try {
            e2.verifier();
        } catch (AnalyseException exp) {
            message.append(exp.getMessage()).append("\n");
        }

        if(!e1.getType().equals(new Entier()))
            message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");

        if(!e2.getType().equals(new Entier()))
            message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");

        if(message.length() > 0){
            throw new AnalyseException(message.toString()) {};
        }
    }

    @Override
    public Type getType(){
        return new Entier();
    }

    @Override
    public String toMIPS(List<String> registres) {
        StringBuilder sb = new StringBuilder();

        sb.append(this.e1.toMIPS(registres));

        if(registres.size() >= this.e2.getNombreDePlaces()){
            sb.append("move ").append(registres.get(1)).append(", ").append(registres.get(0)).append("\n"); // t1 <- v0
            String save = registres.get(1);
            registres.remove(1);
            sb.append(this.e2.toMIPS(registres));
            registres.add(1,save);
            sb.append("add $v0, " + save + ", $v0\n");
        }
        else {
            sb.append("sw $v0,($sp)\n");
            sb.append("add $sp,$sp,-4\n");
            sb.append(this.e2.toMIPS(registres) + "\n");
            sb.append("add $sp,$sp,4\n");
            sb.append("lw $t8,($sp)\n");
            sb.append("add $v0, $t8, $v0\n");
        }

        return sb.toString();
    }


}
