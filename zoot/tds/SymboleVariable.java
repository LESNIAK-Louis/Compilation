package zoot.tds;

import zoot.types.Type;

public class SymboleVariable extends Symbole{

    private int deplacement;

    private String idf;

    public SymboleVariable(Type type, String idf) {
        super(type);
        this.deplacement = 0;
        this.idf = idf;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public String getIdf() { return this.idf; }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    @Override
    public boolean estUneVariable(){
        return true;
    }
}
