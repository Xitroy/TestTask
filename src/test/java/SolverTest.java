import org.junit.Test;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void classify() {
        Solver solver = new Solver();
        System.out.println(solver.classify("3")); //arithmetic
        System.out.println(solver.classify("3+9")); //arithmetic
        System.out.println(solver.classify("A1")); //excel
        System.out.println(solver.classify("A3 +6")); //excel

        System.out.println(solver.arithmetic("3+9")); //12
        System.out.println(solver.arithmetic("12*15")); //180
        System.out.println(solver.arithmetic("12-15")); //-3
        System.out.println(solver.arithmetic("12/15/3 - 268")); //0.8

        System.out.println(solver.dereference("A2"));
    }
}