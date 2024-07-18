package tests;


import org.junit.Test;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.types.Booleen;
import zoot.types.Entier;
import zoot.types.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TypeTest {

    @Test
    public void equals() {

        Type te = new Entier();
        assertTrue(te.equals(new Entier()));

        Type tb = new Booleen();
        assertTrue(tb.equals(new Booleen()));

        assertTrue(!tb.equals(te));
    }
}