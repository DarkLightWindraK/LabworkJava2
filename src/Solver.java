/**
 * Класс Solver предназначен для вычисления арифметического выражения
 * в строковом формате.
 * Выражение должно содержать операнды (числа) и операции: + - * /.
 * Можно использовать скобки и пробелы.
 *
 * Алгоритм реализации основывается на применении двух стеков – один для операций,
 * другой для операндов – для того чтобы сохранить порядок операций.
 *
 * Пример использования:
 * Solver solver = new Solver();
 * Optional<Double> result = solver.solve("2 * (3 + 5) / 8");
 * if (result.isPresent()) {
 *     System.out.println(result.get()); // Результат выражения "2"
 * } else {
 *     System.out.println("Не удалось вычислить выражение");
 * }
 */

import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;

public final class Solver {

    private final Stack<Character> operators = new Stack<>();
    private final Stack<Double> numbers = new Stack<>();

    /**
     * Метод solve осуществляет вычисление арифметического выражения в строковом формате.
     *
     * @param expression - строка, содержащая арифметическое выражение для вычисления.
     * @return Опциональное значение с результатом вычисления, либо пустое значение
     * в случае, если выражение не может быть вычислено.
     */

    public Optional<Double> solve(String expression) {
        Optional<Double> result;
        try {
            result = Optional.of(calculate(expression));
        } catch (EmptyStackException e) {
            return Optional.empty();
        }
        return result;
    }

    private double calculate(String expression) throws EmptyStackException {
        for (int i = 0; i < expression.length(); i++) {
            char sym = expression.charAt(i);
            if (Character.isDigit(sym)) {
                String num = "";
                num += sym;
                while (i < expression.length() - 1 && Character.isDigit(expression.charAt(i + 1))) {
                    num += expression.charAt(++i);
                }
                double number = Double.parseDouble(num);
                numbers.push(number);
            } else if (sym == '(') {
                operators.push(sym);
            } else if (sym == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            } else if (isOperator(sym)) {
                while (!operators.empty() && hasPrecedence(sym, operators.peek())) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(sym);
            }
        }

        while (!operators.empty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+' -> {
                return a + b;
            }
            case '-' -> {
                return a - b;
            }
            case '*' -> {
                return a * b;
            }
            case '/' -> {
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            }
        }
        return 0;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }


}
