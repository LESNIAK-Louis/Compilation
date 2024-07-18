package zoot.arbre.expressions;

import zoot.exceptions.AnalyseException;
import zoot.types.Type;

public abstract class Constante extends Expression {

    protected String cste ;
    
    protected Constante(String texte, int n) {
        super(n) ;
        cste = texte ;
    }
    
    @Override
    public void verifier() throws AnalyseException {}

    public abstract Type getType();

    @Override
    public String toString() {
        return cste ;
    }
}
