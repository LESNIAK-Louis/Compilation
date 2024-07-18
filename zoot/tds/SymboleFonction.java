package zoot.tds;

import zoot.types.Type;

import java.util.ArrayList;

public class SymboleFonction extends Symbole {

    private int noEtiquette;
    private ArrayList<SymboleVariable> params;

    public SymboleFonction(int noEtiquette, Type type, ArrayList<SymboleVariable> params) {
        super(type);
        if(params == null)
            this.params = new ArrayList<>();
        else
            this.params = params;
        this.noEtiquette = noEtiquette;
    }

    public int getEtiquette(){
        return this.noEtiquette;
    }

    public int getDeplacementParam(int i){
        return params.get(i).getDeplacement();
    }

    public int getNbParams() { return this.params.size(); }

    public Type getTypeParam(int i){ return this.params.get(i).getType(); }

    @Override
    public boolean estUneFonction(){
        return true;
    }
}
