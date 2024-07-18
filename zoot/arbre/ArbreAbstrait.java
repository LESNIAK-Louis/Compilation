package zoot.arbre;

import zoot.exceptions.AnalyseException;

import java.util.List;

public abstract class ArbreAbstrait {
    
    // numéro de ligne du début de l'instruction
    protected int noLigne ;
    
    protected ArbreAbstrait(int n) {
        noLigne = n ;
    }
    
    public int getNoLigne() {
            return noLigne ;
    }

    public abstract void verifier() throws AnalyseException;
    public abstract String toMIPS(List<String> registres);

}
