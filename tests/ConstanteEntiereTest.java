package tests;



import zoot.arbre.expressions.ConstanteEntiere;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ConstanteEntiereTest {

    @Test
    public void toMIPS() {
        ConstanteEntiere ce = new ConstanteEntiere("12", 1);
        String string = "li $v0, 12\n";

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(string, ce.toMIPS(registres));
    }
}