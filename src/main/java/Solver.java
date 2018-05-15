import java.util.ArrayList;

public class Solver {

    public static String classify(String cell) {
        //TODO Решить вопрос вычислимости и некорректных значений ячейки
        //TODO переназвать метод
        //Пока считаем, что всё вычислимо, если нет проблем с файлом
        String type = "Arithmetic";
        for (int i = 0; i < cell.length(); i++) {
            if (Character.isLetter(cell.charAt(0))) {
                type = "Excel";
                break;
            }
        }
        return type;
    }

    public static Double arithmetic(String cell) {
        //Позаимствовано со stackoverflow
        //https://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        //Проверено тестами
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < cell.length()) ? cell.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < cell.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(cell.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = cell.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public static Double Excel(String cell, ArrayList<String[]> rows) {
        //Вычленить ссылки
        //Разыменовать все ссылки
        //Подставить в исходную формулу
        //Посчитать как обычное математическое выражение
        //Предположим, что случай упрощённый и ссылка никогда не ссылается на клетку в которой есть другая ссылка
        //s.split("[ \\+-/\\*\\(\\)]+"); - разбиватель по операциям
        String[] operands = cell.split("[ \\+-/\\*\\(\\)]+");
        for (int i = 0; i < operands.length; i++) {
            // Если тут референс
            if (Solver.classify(operands[i]).equals("Excel")) {
                ArrayList<Integer> coords = Solver.dereference(operands[i]);
                String dereferenced = rows.get(coords.get(0))[coords.get(1)];
                cell = cell.replaceAll(operands[i], dereferenced);
            }
        }
        return (Solver.arithmetic(cell));
    }

    public static ArrayList<Integer> dereference(String reference) {
        //Упрощенная версия excel выражения. Будем считать, что таблица конечная в длину
        //И последняя колонка - это Z
        //coord[0] -  номер строки, coord[1] -  номер столбца
        ArrayList<Integer> coord = new ArrayList<>();
        coord.add(0, Integer.parseInt(reference.substring(1)));
        coord.add(1, reference.charAt(0) - 'A');
        return coord;
    }

    public ArrayList<String[]> solve(ArrayList<String[]> rows) {
        //TODO Пробежаться по каждой ячейке, классифицировать тип вычисления, вычислить, положить результат на место
        ArrayList<String[]> solved = new ArrayList<>();
        solved = rows;
        String[] row;
        for (int i = 0; i < rows.size(); i++) {
            row = rows.get(i);
            for (int j = 0; j < row.length; j++) {
                if (Solver.classify(row[j]).equals("Excel")) {
                    row[j] = String.valueOf(Solver.Excel(row[j], rows));
                }
                else {
                    row[j] = String.valueOf(Solver.arithmetic(row[j]));
                }
            }
            solved.set(i, row);
        }
        return solved;
    }
}
