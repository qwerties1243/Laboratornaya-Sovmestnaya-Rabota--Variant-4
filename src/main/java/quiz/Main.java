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


    }
}
