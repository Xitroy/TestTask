import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Путь до файла, который нужно решить
        String path = "C:\\Users\\GID\\IdeaProjects\\test-task\\src\\test\\java\\testFile.csv";

        // Превратим csv файл в массив из массивов строк
        ArrayList<String[]> csvArr = CSVTransformer.CSVTransform(path);

        // Решим каждую ячейку
        csvArr = Solver.solve(csvArr);

        //TODO write to the file
        CSVTransformer.printTransformed(csvArr);
    }
}
