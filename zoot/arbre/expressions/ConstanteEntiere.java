package zoot.arbre.expressions;

import zoot.types.Entier;
import zoot.types.Type;

import java.util.List;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        sb.append("li $v0, ").append(cste).append("\n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return new Entier();
    }
}
