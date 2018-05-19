import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVTransformer {

    /**
     * @param path to the file to be transformed
     * @return csv file transformed to the ArrayList<String[]>. Helps to work with data
     */
    public static ArrayList<String[]> CSVTransform(String path) {
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
        return (list);
    }

    /**
     * Advice:
     * Use it for print your solved result
     *
     * @param transformed array to be printed
     */
    public static void printTransformed(ArrayList<String[]> transformed){
        for (String[] row : transformed) {
            StringBuilder out = new StringBuilder();
            for (String aRow : row) {
                out.append(aRow).append("\t");
            }
            System.out.println(out);
        }
    }
}
