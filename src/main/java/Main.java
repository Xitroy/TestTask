import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        put your path here
        String path = "";


        CSVTransformer transformer = new CSVTransformer(path);

        ArrayList<String[]> mock = transformer.getTransformed();
        Solver solver = new Solver();
        mock = solver.solve(mock);

    }
}
