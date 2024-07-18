package zoot.arbre.instructions;

import zoot.Compteur;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.types.Booleen;

import java.util.List;

public class Ecrire extends Instruction {

    protected Expression exp ;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
    }

    @Override
    public void verifier() throws AnalyseException {
        exp.verifier();
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();

        sb.append(exp.toMIPS(registres));

        if(exp.getType().equals(new Booleen()))
        {
            int numero = Compteur.getInstance().nombreUnique();
            sb.append("si").append(numero).append(":\n");
            sb.append("bnez $v0, sinon").append(numero).append("\n");

            sb.append("alors").append(numero).append(":\n");
            sb.append("la $a0, faux\n");
            sb.append("b finsi").append(numero).append("\n");

            sb.append("sinon").append(numero).append(":\n");
            sb.append("la $a0, vrai\n");

            sb.append("finsi").append(numero).append(":\n");
            sb.append("li $v0, 4\n");
        }
        else{
            sb.append("move $a0, $v0\n");
            sb.append("li $v0, 1\n");
        }
        sb.append("syscall\n");

        // Nouvelle ligne ...
        sb.append("addi $a0, $0, 0xA\n"); //  newline: .asciiz "\n" si 0xA ne convient pas
        sb.append("addi $v0, $0, 0xB\n"); // syscall numéro 11
        sb.append("syscall\n");

        return sb.toString();
    }

}
