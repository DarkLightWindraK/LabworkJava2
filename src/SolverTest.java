import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class SolverTest {

    // Test 1: проверяем, что, если передано корректное выражение, метод высчитывает его правильно
    @Test
    public void testSolveSuccessful() {
        Solver solver = new Solver();
        var expression = "2 + 2 * 2";

        Double expected = 6.0;

        assertEquals(expected, solver.solve(expression).get());
    }

    // Test 2: проверяем, что результатом пустого выражения будет null
    @Test
    public void testSolveEmpty() {
        Solver solver = new Solver();
        var expression = "";

        Optional<Double> expected = Optional.empty();

        assertEquals(expected, solver.solve(expression));
    }

    // Test 3: проверим, что при попытке вычислить некорректное выражение возвращается null
    @Test
    public void testSolveInvalidExpression() {
        Solver solver = new Solver();
        var expression = "3+(";

        Optional<Double> expected = Optional.empty();

        assertEquals(expected, solver.solve(expression));
    }

    // Test 4: проверим, что выражение со скобками вычисляется корректно
    @Test
    public void testSolveWithBrackets() {
        Solver solver = new Solver();
        var expression = "3 * 2 * (2 + 5)";

        Double expected = 42.0;

        assertEquals(expected, solver.solve(expression).get());
    }
}
