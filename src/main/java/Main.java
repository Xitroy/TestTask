import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        put your path here
        String path = "";

        // Превратим csv файл в массив из массивов строк
        CSVTransformer transformer = new CSVTransformer(path);
        ArrayList<String[]> csvArr = transformer.getTransformed();

        // Решим каждую ячейку
        Solver solver = new Solver();
        csvArr = solver.solve(csvArr);

    }
}