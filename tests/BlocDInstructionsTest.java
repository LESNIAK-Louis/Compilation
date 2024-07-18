package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import zoot.arbre.BlocDInstructions;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.arbre.instructions.Ecrire;

import java.util.ArrayList;
import java.util.List;


class BlocDInstructionsTest {

    @Test
    public void toMIPS() {

        Ecrire e = new Ecrire(new ConstanteEntiere("1", 1), 2);
        Ecrire e2 = new Ecrire(new ConstanteEntiere("112", 3), 4);


        BlocDInstructions bloc = new BlocDInstructions(0,0);
        bloc.ajouter(e);
        bloc.ajouter(e2);

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        StringBuilder sb = new StringBuilder();
        sb.append("li $v0, 1\n");
        sb.append("move $a0, $v0\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");
        sb.append("li $v0, 112\n");
        sb.append("move $a0, $v0\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");


        assertEquals(sb.toString(), bloc.toMIPS(registres));
    }
}