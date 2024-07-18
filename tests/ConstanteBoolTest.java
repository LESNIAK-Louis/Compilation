package tests;


import org.junit.Test;
import zoot.arbre.expressions.ConstanteBool;
import zoot.arbre.expressions.ConstanteEntiere;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ConstanteBoolTest {

    @Test
    public void toMIPS() {
        ConstanteBool cb1 = new ConstanteBool("vrai", 1);
        String string = "li $v0, 1\n";

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(string, cb1.toMIPS(registres));

        ConstanteBool cb2 = new ConstanteBool("faux", 1);
        string = "li $v0, 0\n";

        assertEquals(string, cb2.toMIPS(registres));
    }
}