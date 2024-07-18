package zoot.arbre.expressions.operateurs;

import zoot.Compteur;
import zoot.arbre.expressions.Binaire;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.types.Booleen;
import zoot.types.Entier;
import zoot.types.Type;

import java.util.List;

public class Inf extends Binaire {
    public Inf(Expression e1, Expression e2, int n) {
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
    public String toMIPS(List<String> registres) {
        StringBuilder sb = new StringBuilder();

        sb.append(this.e1.toMIPS(registres));

        int nb = Compteur.getInstance().nombreUnique();

        if(registres.size() >= this.e2.getNombreDePlaces()){
            sb.append("move ").append(registres.get(1)).append(", ").append(registres.get(0)).append("\n"); // t1 <- v0
            String save = registres.get(1);
            registres.remove(1);
            sb.append(this.e2.toMIPS(registres));
            registres.add(1,save);
            sb.append("blt " + save + ", $v0, inf").append(nb).append("\n");
            sb.append("li $v0, 0\n");
            sb.append("j fin_inf").append(nb).append("\n");
            sb.append("inf").append(nb).append(": li $v0, 1\n");
            sb.append("fin_inf").append(nb).append(": \n");
        }
        else {
            sb.append("sw $v0,($sp)\n");
            sb.append("add $sp,$sp,-4\n");
            sb.append(this.e2.toMIPS(registres) + "\n");
            sb.append("add $sp,$sp,4\n");
            sb.append("lw $t8,($sp)\n");
            sb.append("blt $t8, $v0, inf").append(nb).append("\n");
            sb.append("li $v0, 0\n");
            sb.append("j fin_inf").append(nb).append("\n");
            sb.append("inf").append(nb).append(": li $v0, 1\n");
            sb.append("fin_inf").append(nb).append(": \n");
        }

        return sb.toString();
    }

    @Override
    public Type getType() {
        return new Booleen();
    }
}