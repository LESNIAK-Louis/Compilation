package tests;


import org.junit.Test;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.tds.*;
import zoot.types.Entier;

import static org.junit.Assert.*;


public class TDSTest {

    @Test
    public void ajouter() {
        SymboleVariable s1 = new SymboleVariable(new Entier(), "a");
        TDS.getInstance().ajouter(new EntreeVariable("a",0 ), s1);

        Symbole r = TDS.getInstance().identifier(new EntreeVariable("a", 0));
        assertEquals(r, s1);

        SymboleVariable s2 = new SymboleVariable(new Entier(), "b");
        TDS.getInstance().ajouter(new EntreeVariable("b", 0), s2);

        assertEquals(TDS.getInstance().identifier(new EntreeVariable("b", 0)), s2);

        assertNotEquals(r, s2);
    }

    @Test
    public void identifier() {
        SymboleVariable s1 = new SymboleVariable(new Entier(), "a");
        TDS.getInstance().ajouter(new EntreeVariable("a", 0), s1);

        assertEquals(TDS.getInstance().identifier(new  EntreeVariable("a", 0)), s1);

        SymboleVariable s2 = new SymboleVariable(new Entier(), "b");
        TDS.getInstance().ajouter(new EntreeVariable("b", 0), s2);

        assertEquals(TDS.getInstance().identifier( new EntreeVariable("b", 0)), s2);
    }

    @Test
    public void getTailleZoneVariables() {
        SymboleVariable s1 = new SymboleVariable(new Entier(), "a");
        TDS.getInstance().ajouter(new EntreeVariable("a", 0), s1);

        assertEquals(TDS.getInstance().getTailleZoneVariables(), 4);

        SymboleVariable s2 = new SymboleVariable(new Entier(), "b");
        TDS.getInstance().ajouter(new EntreeVariable("b", 0), s2);

        assertEquals(TDS.getInstance().getTailleZoneVariables(), 8);
    }
}