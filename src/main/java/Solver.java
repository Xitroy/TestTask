import java.util.ArrayList;

public class Solver {

    /**
     * Example:
     * classify("(6+10)/2") = "Arithmetic"
     * classify("A0") = "Excel"
     *
     * Assumption:
     * There are only 2 possible types of cells
     * Arithmetic and Excel-like.
     * The difference between them is that Excel-like cells use characters inside
     *
     * @param cell to be classified
     * @return is this cell excel-like or arithmetic-like expression
     */
    public static String classify(String cell) {
        //Считаем, что всё вычислимо, если нет проблем с файлом
        String type = "Arithmetic";
        for (int i = 0; i < cell.length(); i++) {
            if (Character.isLetter(cell.charAt(0))) {
                type = "Excel";
                break;
            }
        }
        return type;
    }

    /**
     * Example:
     * arithmetic("(6+10)/2") = 8
     *
     * @param cell to evaluate
     * @return evaluated arithmetic expression as a double
     */
    public static Double arithmetic(String cell) {
        //Позаимствовано со stackoverflow
        //https://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
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

    /**
     * Example:
     * rows = [
     * [1;2;3]
     * [4;5;A0*B0*C0]
     * [7;8;9]
     * ]
     * Excel("A0*B0*C0", rows) = 6
     *
     * Assumption:
     * Never the case when reference to the cell with other references
     *
     * @param cell to evaluate
     * @param rows array generated from CSV in order to find references
     * @return evaluated Excel-like expression as a double
     */
    public static Double Excel(String cell, ArrayList<String[]> rows) {
        //Вычленить ссылки
        //Разыменовать все ссылки
        //Подставить в исходную формулу
        //Посчитать как обычное математическое выражение
        //Предположим, что случай упрощённый и ссылка никогда не ссылается на клетку в которой есть другая ссылка
        //s.split("[ \\+-/\\*\\(\\)]+"); - разбиватель по операциям
        String[] operands = cell.split("[ \\+-/\\*\\(\\)]+");
        ArrayList<Integer> coords;
        String dereferenced;
        for (String operand : operands) {
            // Если тут референс
            if (Solver.classify(operand).equals("Excel")) {
                coords = Solver.dereference(operand);
                dereferenced = rows.get(coords.get(0))[coords.get(1)];
                cell = cell.replaceAll(operand, dereferenced);
            }
        }
        return (Solver.arithmetic(cell));
    }

    /**
     * Example:
     * dereference(A0) = [0,0]
     * dereference(C1) = [0,0]
     *
     * Assumption:
     * The table has only A-Z columns
     *
     * @param reference in excel-like format
     * @return coordinates in array
     */
    public static ArrayList<Integer> dereference(String reference) {
        //coord[0] -  номер строки, coord[1] -  номер столбца
        ArrayList<Integer> coord = new ArrayList<>();
        coord.add(0, Integer.parseInt(reference.substring(1)));
        coord.add(1, reference.charAt(0) - 'A');
        return coord;
    }

    /**
     * Example:
     * rows = [
     * [1;2;3]
     * [4;5;A0*B0*C0]
     * [7;8;9]
     * ]
     * solve(rows) = [
     * [1;2;3]
     * [4;5;6]
     * [7;8;9]
     * ]
     *
     *
     * @param rows array generated from CSV in order to find references
     * @return array with solved cells
     */
    public static ArrayList<String[]> solve(ArrayList<String[]> rows) {
        ArrayList<String[]> solved;
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
