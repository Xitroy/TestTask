import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CSVServant {

    /**
     * Description:
     * Translates CSV file to ArrayList<String[]>
     *
     * @param path to the file to be transformed to Arraylist of strings
     * @return csv file transformed to the ArrayList<String[]>. Helps to work with data
     */
    public static ArrayList<String[]> CSVToArray(String path) {
        ArrayList<String[]> csvArray = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;
        String text;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((text = reader.readLine()) != null) {
                csvArray.add(text.split(";"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try { if (reader != null) { reader.close(); } }
            catch (IOException e){e.printStackTrace();}
        }
        return (csvArray);
    }

    /**
     * Description:
     * Print ArrayList<String[]> into console
     *
     * Advice:
     * Use it for print your solved result
     *
     * @param csvArray array to be printed
     */
    public static void printArray(ArrayList<String[]> csvArray){
        for (String[] row : csvArray) {
            System.out.println(String.join("\t", row));
        }
    }

    /**
     * Description:
     * Translates ArrayList<String[]> to CSV file
     *
     * @param csvArray to be wrote into file
     * @param path of file to be created
     */
    public static void ArrayToCSV(ArrayList<String[]> csvArray, String path) {
        ArrayList<String> lines = new ArrayList<>();
        for (String[] aCsvArray : csvArray) {
            lines.add(String.join(";", aCsvArray));
        }

        try {
            Files.write(Paths.get(path), lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
