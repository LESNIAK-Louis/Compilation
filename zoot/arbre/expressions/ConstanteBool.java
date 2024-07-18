package zoot.arbre.expressions;

import zoot.types.Booleen;
import zoot.types.Entier;
import zoot.types.Type;

import java.util.List;

public class ConstanteBool extends Constante {

    public ConstanteBool(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        sb.append("li $v0, ").append(cste.contentEquals("faux")?"0":"1").append("\n");

        return sb.toString();
    }

    @Override
    public Type getType() {
        return new Booleen();
    }
}
