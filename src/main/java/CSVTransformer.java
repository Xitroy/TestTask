import java.io.*;
import java.util.ArrayList;

public class CSVTransformer {

    private ArrayList<String[]> transformed;

    public CSVTransformer(String path) {
        // TODO Считать строки, каждую строку превратить в массив ячеек(строковых), положить все масивы в один большой
        ArrayList<String[]> list = new ArrayList<>();
        File file = new File("file.txt");
        BufferedReader reader = null;
        String text;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((text = reader.readLine()) != null) {
                list.add(text.split(";"));
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            try { if (reader != null) { reader.close(); } }
            catch (IOException e){}
        }

        transformed = list;
    }

    public ArrayList<String[]> getTransformed() {
        return transformed;
    }
}
