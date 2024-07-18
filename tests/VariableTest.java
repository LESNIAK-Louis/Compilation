package tests;


import org.junit.Test;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.arbre.expressions.Variable;
import zoot.tds.Entree;
import zoot.tds.EntreeVariable;
import zoot.tds.SymboleVariable;
import zoot.tds.TDS;
import zoot.types.Entier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class VariableTest {

    @Test
    public void toMIPS() {
        TDS.getInstance().ajouter(new EntreeVariable("a", 0), new SymboleVariable(new Entier(), "a"));
        Variable v1 = new Variable("a", 1);
        String string = "lw $v0, -0($s7)\n";

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(string, v1.toMIPS(registres));

        TDS.getInstance().ajouter(new EntreeVariable("b", 0), new SymboleVariable(new Entier(), "b"));
        Variable v2 = new Variable("b", 1);
        string = "lw $v0, -0($s7)\n";

        assertEquals(string, v2.toMIPS(registres));
    }
}