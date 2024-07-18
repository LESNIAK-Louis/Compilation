package tests;

import java_cup.runtime.Symbol;
import org.junit.Test;
import zoot.arbre.expressions.ConstanteBool;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.arbre.expressions.Variable;
import zoot.arbre.instructions.Affectation;
import zoot.arbre.instructions.Ecrire;
import zoot.tds.Entree;
import zoot.tds.EntreeVariable;
import zoot.tds.SymboleVariable;
import zoot.tds.TDS;
import zoot.types.Entier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class AffectationTest {

    @Test
    public void toMIPS() {

        Affectation a1 = new Affectation("a", new ConstanteEntiere("1", 1), 2);

        StringBuilder sb1 = new StringBuilder();
        sb1.append("li $v0, 1\n");
        sb1.append("sw $v0, -0($s7)\n");

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(sb1.toString(), a1.toMIPS(registres));

        Affectation a2 = new Affectation("b", new ConstanteBool("vrai", 1), 2);

        StringBuilder sb2 = new StringBuilder();
        sb2.append("li $v0, 1\n");
        sb2.append("sw $v0, -0($s7)\n");

        assertEquals(sb2.toString(), a2.toMIPS(registres));

        TDS.getInstance().ajouter(new EntreeVariable("a", 0), new SymboleVariable(new Entier(), "a"));
        TDS.getInstance().ajouter(new EntreeVariable("b", 0), new SymboleVariable(new Entier(), "b"));
        Affectation a3 = new Affectation("b", new Variable("a", 1), 2);

        StringBuilder sb3 = new StringBuilder();
        sb3.append("lw $v0, -0($s7)\n");
        sb3.append("sw $v0, -0($s7)\n");

        assertEquals(sb3.toString(), a3.toMIPS(registres));

    }
}