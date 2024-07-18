package zoot.arbre.expressions;

import zoot.arbre.ArbreAbstrait;
import zoot.types.Type;

public abstract class Expression extends ArbreAbstrait {
    
    protected Expression(int n) {
        super(n) ;
    }

    public abstract Type getType();

    public int getNombreDePlaces(){
        return 1;
    }
}
