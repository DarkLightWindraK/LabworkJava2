import java.util.EmptyStackException;
import java.util.Optional;
import java.util.Stack;

public final class Solver {

    private final Stack<Character> operators = new Stack<>();
    private final Stack<Double> numbers = new Stack<>();

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
