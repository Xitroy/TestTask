import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void classify() {
        Solver solver = new Solver();
        System.out.println(Solver.classify("3")); //arithmetic
        System.out.println(Solver.classify("3+9")); //arithmetic
        System.out.println(Solver.classify("A1")); //excel
        System.out.println(Solver.classify("A3 +6")); //excel

        System.out.println(Solver.arithmetic("3+9")); //12
        System.out.println(Solver.arithmetic("12*15")); //180
        System.out.println(Solver.arithmetic("12-15")); //-3
        System.out.println(Solver.arithmetic("12/15/3 - 268")); //0.8

        System.out.println(Solver.dereference("A2"));

        //10;10+10;A0
        //20;20+20;B1
        //30;30+30;B0+(B1/B2)
        String[] s1 = new String[3];
        s1[0] = "10";
        s1[1] = "10 + 10";
        s1[2] = "A0";
        String[] s2 = new String[3];
        s2[0] = "20";
        s2[1] = "20 + 20";
        s2[2] = "B1";
        String[] s3 = new String[3];
        s3[0] = "30";
        s3[1] = "30 + 30";
        s3[2] = "B0+(B1/B2)";
        ArrayList<String[]> csv = new ArrayList<>();
        csv.add(s1);
        csv.add(s2);
        csv.add(s3);
        System.out.println(Solver.Excel(csv.get(0)[2], csv));
        System.out.println(Solver.Excel(csv.get(1)[2], csv));
        System.out.println(Solver.Excel(csv.get(2)[2], csv));
    }
}