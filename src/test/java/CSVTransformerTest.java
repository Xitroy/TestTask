import org.junit.Test;

import static org.junit.Assert.*;

public class CSVTransformerTest {
    @Test
    public void init(){
        CSVTransformer transformer = new CSVTransformer("C:\\Users\\GID\\IdeaProjects\\test-task\\src\\test\\java\\testFile.csv");
        System.out.println(transformer.getTransformed().get(0)[0]);
        System.out.println(transformer.getTransformed().get(0)[1]);
        System.out.println(transformer.getTransformed().get(0)[2]);

        System.out.println(transformer.getTransformed().get(1)[0]);
        System.out.println(transformer.getTransformed().get(1)[1]);
        System.out.println(transformer.getTransformed().get(1)[2]);

        System.out.println(transformer.getTransformed().get(2)[0]);
        System.out.println(transformer.getTransformed().get(2)[1]);
        System.out.println(transformer.getTransformed().get(2)[2]);
    }

}