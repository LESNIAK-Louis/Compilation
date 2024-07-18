package zoot.arbre.expressions;

import zoot.exceptions.AnalyseException;
import zoot.types.Type;

import java.util.List;

public abstract class Unaire extends Expression {

    protected Expression e;

    protected Unaire(Expression e, int n) {
        super(n);
        this.e = e;
    }

    @Override
    public void verifier() throws AnalyseException {

    }

    @Override
    public String toMIPS(List<String> registres) {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public int getNombreDePlaces(){

        int n = e.getNombreDePlaces();
        return n;
    }
}
