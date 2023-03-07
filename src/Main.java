import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите выражение");
        var data = new Scanner(System.in).nextLine();
        var result = new Solver().solve(data);
        if (result.isPresent()) {
            System.out.println("Ответ: " + result.get());
        } else {
            System.out.println("Выражение некорректно");
        }
    }
}