package zoot.arbre.instructions;

import zoot.arbre.ArbreAbstrait;
import zoot.types.Type;

public abstract class Instruction extends ArbreAbstrait {

    protected Instruction(int n) {
        super(n);
    }

    public boolean estUnRetourne(){
        return false;
    }

    public boolean estUnSousBloc(){
        return false;
    }

    public void setTypeRetour(Type t) {}

    public boolean aUnRetourne() { return false;}


}
