package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import zoot.arbre.expressions.ConstanteEntiere;
import zoot.arbre.instructions.Ecrire;

import java.util.ArrayList;
import java.util.List;


class EcrireTest {

    @Test
    public void toMIPS() {

        Ecrire e = new Ecrire(new ConstanteEntiere("1", 1), 2);

        StringBuilder sb = new StringBuilder();
        sb.append("li $v0, 1\n");
        sb.append("move $a0, $v0\n");
        sb.append("li $v0, 1\n");
        sb.append("syscall\n");
        sb.append("addi $a0, $0, 0xA\n");
        sb.append("addi $v0, $0, 0xB\n");
        sb.append("syscall\n");

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(sb.toString(), e.toMIPS(registres));
    }
}