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

public class Boucle extends Instruction {

    protected Expression exp ;
    BlocDInstructions bloc;

    public Boucle (BlocDInstructions bloc, Expression e, int n) {
        super(n) ;
        exp = e ;
        this.bloc = bloc;
        bloc.setEstUnSousBloc(true);
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
            bloc.verifier();
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
    public boolean aUnRetourne(){
        return bloc.aUnRetourne();
    }

    @Override
    public void setTypeRetour(Type t) { bloc.setTypeRetour(t); }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        int nb = Compteur.getInstance().nombreUnique();

        sb.append("boucle"+nb+":\n");
        sb.append(this.bloc.toMIPS(registres));
        sb.append(this.exp.toMIPS(registres));
        sb.append("beqz $v0, boucle"+nb+"\n");
        sb.append("bouclefin"+nb+":\n");


        return sb.toString();
    }

}
