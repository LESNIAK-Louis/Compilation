package zoot.arbre.expressions;

import zoot.exceptions.AnalyseException;
import zoot.types.Type;

import java.util.List;

public abstract class Binaire extends Expression {

    protected Expression e1;
    protected Expression e2;


    protected Binaire(Expression e1, Expression e2, int n) {
        super(n);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void verifier() throws AnalyseException {

    }

    @Override
    public String toMIPS(List<String> registres) {
        return null;
    }

    @Override
    public int getNombreDePlaces(){

        int n1 = e1.getNombreDePlaces();
        int n2 = e2.getNombreDePlaces();

        if(n1!=n2){
            return Math.max(n1,n2);
        }else{
            return n1 + 1;
        }
    }
}
