import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVTransformer {

    private ArrayList<String[]> transformed;

    public CSVTransformer(String path) {
        // TODO Считать строки, каждую строку превратить в массив ячеек(строковых), положить все масивы в один большой
        ArrayList<String[]> list = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;
        String text;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((text = reader.readLine()) != null) {
                list.add(text.split(";"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try { if (reader != null) { reader.close(); } }
            catch (IOException e){e.printStackTrace();}
        }
        transformed = list;
    }

    public ArrayList<String[]> getTransformed() {
        return transformed;
    }

    public void printTransformed(ArrayList<String[]> transformed){
        for (int i = 0; i < transformed.size(); i++) {
            String[] row = transformed.get(i);
            StringBuilder out = new StringBuilder();
            for (int j = 0; j <row.length ; j++) {
                out.append(row[j]).append(" ");
            }
            System.out.println(out);
        }
    }
}
