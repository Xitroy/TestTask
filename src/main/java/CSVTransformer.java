import java.io.*;
import java.util.ArrayList;

public class CSVTransformer {
    public CSVTransformer(String path) {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File("file.txt");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
            }
        }
        System.out.println(list);
    }
}
