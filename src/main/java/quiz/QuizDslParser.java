package quiz;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class QuizDslParser {

    public QuizSpec parseFile(Path file) throws IOException {
        String source = Files.readString(file, StandardCharsets.UTF_8);
        return parse(source);
    }

    public QuizSpec parse(String source) {
		
    }
	
}
