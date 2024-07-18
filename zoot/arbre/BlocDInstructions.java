package zoot.arbre;

import zoot.arbre.instructions.Condition;
import zoot.arbre.instructions.Instruction;
import zoot.arbre.instructions.Retourne;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.tds.TDS;
import zoot.types.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
    protected ArrayList<Instruction> programme ;
    protected int noBloc;
    protected Type typeRetour;

    protected int nbParams;

    protected boolean aUnRetourne;
    protected boolean estUnSousBloc;

    public BlocDInstructions(int n, int noBloc) {
        super(n) ;
        programme = new ArrayList<>() ;
        typeRetour = null;
        this.noBloc = noBloc;
        this.aUnRetourne = false;
        this.estUnSousBloc = false;
    }

    public int getNbParams() {
        return nbParams;
    }

    public void setNbParams(int nbParams) {
        this.nbParams = nbParams;
    }

    public int getTailleBloc(){ return programme.size(); }

    public int getNoBloc() { return noBloc; }
    
    public void ajouter(Instruction i) {
        programme.add(i) ;
    }

    public void setTypeRetour(Type type){
        typeRetour = type;
    }

    public void setEstUnSousBloc(boolean estUnSousBloc){
        this.estUnSousBloc = estUnSousBloc;
    }

    @Override
    public void verifier() throws AnalyseException {
        StringBuilder message = new StringBuilder();
        for(int k = 0; k < programme.size(); k++) {
            Instruction i = programme.get(k);

            if(typeRetour != null) {
                if(i.estUnRetourne() || i.estUnSousBloc()){
                    if(i.estUnRetourne())
                        ((Retourne)i).setTypeRetour(typeRetour);
                    if(i.estUnSousBloc())
                        (i).setTypeRetour(this.typeRetour);
                    try {
                        i.verifier();
                        if(i.aUnRetourne()){
                            aUnRetourne = true;
                            break;
                        }
                    } catch (AnalyseException e) {
                        message.append(e.getMessage()).append("\n");
                    }
                    if(i.estUnRetourne()) {
                        aUnRetourne = true;
                        break;
                    }
                }else if (k == programme.size() - 1 && !this.estUnSousBloc) {
                    message.append(new AnalyseSemantiqueException(i.getNoLigne(), " la fonction ne comporte pas d'instruction retourne").getMessage()).append("\n");
                }
            }else if(i.estUnRetourne()){
                message.append(new AnalyseSemantiqueException(i.getNoLigne(), " instruction retourne dans un bloc qui n'est pas une fonction").getMessage()).append("\n");
            }
            try {
                i.verifier();
            } catch (AnalyseException e) {
                message.append(e.getMessage()).append("\n");
            }
        }

        if(message.length() > 0){
            throw new AnalyseException(message.toString()) {
            };
        }
    }

    public boolean aUnRetourne(){
        return aUnRetourne;
    }
    
    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();

        for(Instruction i : programme){
            sb.append(i.toMIPS(registres));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return programme.toString() ;
    }

}
