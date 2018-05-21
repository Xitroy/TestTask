import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Путь до файла, который нужно решить
        String path = "testFile.csv";

        // Превратим csv файл в массив из массивов строк
        ArrayList<String[]> csvArr = CSVServant.CSVToArray(path);

        // Решим все ячейки
        csvArr = Solver.solve(csvArr);

        // Вывод в консольку
        CSVServant.printArray(csvArr);

        // Запись в новый CSV
        CSVServant.ArrayToCSV(csvArr, "output.csv");
    }
}
