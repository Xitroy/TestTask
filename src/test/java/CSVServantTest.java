import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CSVServantTest {
    @Test
    public void CSVTransform() {
        ArrayList<String[]> transformed = CSVServant.CSVToArray("testFile.csv");
        assertEquals("10", transformed.get(0)[0]); // 10
        assertEquals("10+10", transformed.get(0)[1]); // 10+10
        assertEquals("A0", transformed.get(0)[2]); // A0
    }

    @Test
    public void printTransformed() {
        ArrayList<String[]> transformed = CSVServant.CSVToArray("testFile.csv");
        CSVServant.printArray(transformed);
    }
}