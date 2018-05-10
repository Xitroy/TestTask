import java.util.ArrayList;

public class Solver {

    private String classify(String cell){
        //TODO Решить вопрос вычислимости и некорректных значений ячейки
        //Пока считаем, что всё вычислимо, если нет проблем с файлом
        String type = "Arithmetic";
        for (int i = 0; i < cell.length(); i++) {
            if (Character.isLetter(cell.charAt(0))){
                type = "Excel";
                break;
            }
        }
        return type;
    }

    private Double arithmetic(String cell){
        //TODO вычисление строки с математическим выражением (читай ниже)
        //http://www.cyberforum.ru/java-j2se/thread1088770.html
        return (6.0);
    }

    private Double Excel(String cell){
        //TODO вычисление строки с экселевским выражением
        return (6.0);
    }

    public ArrayList<String[]> solve(ArrayList<String[]> rows){
        //TODO Пробежаться по каждой ячейке, классифицировать тип вычисления, вычислить, положить результат на место
        ArrayList<String[]> solved = rows;
        return solved;
    }
}
