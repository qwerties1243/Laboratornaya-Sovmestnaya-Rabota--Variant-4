package quiz;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        Path input = args.length > 0 ? Path.of(args[0]) : Path.of("examples", "quiz.txt");
        Path output = args.length > 1 ? Path.of(args[1]) : Path.of("quiz.html");

        try {
            QuizSpec spec = new QuizDslParser().parseFile(input);
            new HtmlQuizGenerator().generateToFile(spec, output);
            System.out.println("Тест сгенерирован: " + output.toAbsolutePath());
            System.out.println("Вопросов: " + spec.getQuestions().size()
                    + ", стиль: " + spec.getStyle());
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.exit(2);
        }
    }
}
