package zoot.tds;

import zoot.types.Type;

public abstract class Symbole {

    private Type type;

    public Symbole(Type type){

        this.type = type;
    }

    public Type getType(){
        return this.type;
    }

    public boolean estUneVariable(){
        return false;
    }

    public boolean estUneFonction(){
        return false;
    }
}
