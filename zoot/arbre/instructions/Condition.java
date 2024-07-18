package zoot.arbre.instructions;

import zoot.Compteur;
import zoot.arbre.BlocDInstructions;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.types.Booleen;
import zoot.types.Type;

import java.util.ArrayList;
import java.util.List;

public class Condition extends Instruction {

    protected Expression exp ;
    BlocDInstructions blocSi;
    BlocDInstructions blocSinon;

    public Condition (Expression e, BlocDInstructions blocSi, BlocDInstructions blocSinon, int n) {
        super(n) ;
        exp = e ;
        this.blocSi = blocSi;
        this.blocSinon = blocSinon;
        blocSi.setEstUnSousBloc(true);
        blocSinon.setEstUnSousBloc(true);
    }

    @Override
    public void verifier() throws AnalyseException {
        StringBuilder message = new StringBuilder();

        try {
            this.exp.verifier();
        } catch (AnalyseException e) {
            message.append(e.getMessage()).append("\n");
        }

        if(!exp.getType().equals(new Booleen())){
            message.append(new AnalyseSemantiqueException(getNoLigne(), "types non compatibles").getMessage()).append("\n");
        }
        try {
            blocSi.verifier();
        } catch (AnalyseException e) {
            message.append(e.getMessage()).append("\n");
        }


        try {
            blocSinon.verifier();
        } catch (AnalyseException e) {
            message.append(e.getMessage()).append("\n");
        }

        if(message.length() > 0){
            throw new AnalyseException(message.toString()) {};
        }
    }

    @Override
    public boolean estUnSousBloc(){
        return true;
    }

    @Override
    public void setTypeRetour(Type t) { blocSi.setTypeRetour(t); blocSinon.setTypeRetour(t);}

    @Override
    public boolean aUnRetourne(){
        return blocSi.aUnRetourne() && blocSinon.aUnRetourne();
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        int nb = Compteur.getInstance().nombreUnique();

        sb.append(this.exp.toMIPS(registres));

        sb.append("si"+nb+":\n");
        sb.append("beq $v0, $zero, sinon"+nb+"\n");
        sb.append("alors"+nb+":\n");
        if(blocSi != null)
            sb.append(blocSi.toMIPS(registres));
        sb.append("j finsi"+nb+"\n");
        sb.append("sinon"+nb+":\n");
        if(blocSinon != null)
            sb.append(blocSinon.toMIPS(registres));
        sb.append("finsi"+nb+":\n");


        return sb.toString();
    }

}
