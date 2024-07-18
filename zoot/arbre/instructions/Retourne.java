package zoot.arbre.instructions;

import zoot.Programme;
import zoot.arbre.expressions.Expression;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;
import zoot.tds.TDS;
import zoot.types.Type;

import java.util.List;

public class Retourne extends Instruction {

    private Type typeRetour;
    private Expression e;

    public Retourne(Expression e, int n) {
        super(n);

        this.e = e;
    }

    public void setTypeRetour(Type t){
        this.typeRetour = t;
    }

    @Override
    public boolean estUnRetourne(){
        return true;
    }

    @Override
    public void verifier() throws AnalyseException {

        e.verifier();
        if(typeRetour != null && !e.getType().equals(typeRetour))
            throw new AnalyseSemantiqueException(getNoLigne(), "type retourné non compatible avec le type de la fonction");
    }

    @Override
    public String toMIPS(List<String> registres) {

        StringBuilder sb = new StringBuilder();
        int nbParamsDecal = 4 * Programme.getInstance().getFonctionActuelle().getNbParams();
        sb.append("#fin de la fonction\n");
        sb.append(e.toMIPS(registres));
        sb.append("sw $v0, " + (int)(nbParamsDecal + 12) + "($s7) #sauvegarde du résultat dans l'espace réservé\n");
        sb.append("addi $sp, $s7, " + (int)(nbParamsDecal + 12) + " #restauration du sommet de pile\n");
        sb.append("lw $s7, 4($s7) #restauration de la base\n");
        sb.append("lw $ra, -" + (int)(4 + nbParamsDecal) + "($sp) #chargement de l'adresse de retour\n");
        sb.append("jr $ra #branchement à l'adresse de retour\n");

        return sb.toString();
    }
}
