import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void classify() {
        Solver solver = new Solver();
        assertEquals("Arithmetic", Solver.classify("3"));
        assertEquals("Arithmetic", Solver.classify("3+9"));
        assertEquals("Excel", Solver.classify("A1"));
        assertEquals("Excel", Solver.classify("A3+6"));
    }

    @Test
    public void arithmetic() {
        assertEquals(12, Solver.arithmetic("3+9"), 0); //12
        assertEquals(180, Solver.arithmetic("12*15"), 0); //180
        assertEquals(-3, Solver.arithmetic("12-15"), 0); //-3
        assertEquals(-267, Solver.arithmetic("12/15/3 - 268"), 1); // -267.73333333333335
    }

    @Test
    public void excel() {
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
        assertEquals(10, Solver.Excel(csv.get(0)[2], csv), 0);
        assertEquals(40, Solver.Excel(csv.get(1)[2], csv), 0);
        assertEquals(70, Solver.Excel(csv.get(2)[2], csv), 1);
    }

    @Test
    public void dereference() {
        ArrayList<Integer> coords = new ArrayList<>();
        coords.add(0, 2);
        coords.add(1,0);

        assertEquals(Solver.dereference("A2"), coords); // A2 = [2,0]
    }

    @Test
    public void solve() {
        //To be solved
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
        ArrayList<String[]> csvArr = new ArrayList<>();
        csvArr.add(s1);
        csvArr.add(s2);
        csvArr.add(s3);

        //Should be equals to
        //======================PLEASE CHECK IT MANUALLY======================//
        //10;20;10
        //20;40;40
        //30;60;20.66666666666667

        csvArr = Solver.solve(csvArr);
        CSVServant.printArray(csvArr);
    }
}