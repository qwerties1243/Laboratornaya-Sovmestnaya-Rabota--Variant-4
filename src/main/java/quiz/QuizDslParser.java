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
		if (!source.isEmpty() && source.charAt(0) == '﻿') {
            source = source.substring(1);
        }
        String[] lines = source.split("\\R", -1);
        String title = null;
        String h1 = null;
        String style = "default";
        List<QuizSpec.Question> questions = new ArrayList<>();

        int i = 0;
        while (i < lines.length) {
            int lineNumber = i + 1;
            String raw = lines[i].strip();
            i++;
            if (raw.isEmpty()  raw.startsWith("#")) {
                continue;
            }

            if (startsWithDirective(raw, "title")) {
                title = requireValue(raw, "Title", lineNumber);
                continue;
            }
            if (startsWithDirective(raw, "h1")) {
                h1 = requireValue(raw, "H1", lineNumber);
                continue;
            }
            if (startsWithDirective(raw, "style")) {
                style = requireValue(raw, "Style", lineNumber);
                continue;
            }


            String questionHeader = raw.substring(1).strip();
            QuizSpec.AnswerType type = extractType(questionHeader, lineNumber);
            String questionText = stripType(questionHeader, lineNumber).strip();
            if (type == QuizSpec.AnswerType.TEXT) {
                Line answerLine = nextNonBlank(lines, i, lineNumber);
                String answer = answerLine.text.substring(1).strip();
                i = answerLine.nextIndex;
                questions.add(new QuizSpec.Question(
                        questionText, type, List.of(), answer));
                continue;
            }

            List<QuizSpec.Option> options = new ArrayList<>();
            boolean closed = false;
            while (i < lines.length) {
                int optionLineNumber = i + 1;
                String optionRaw = lines[i].strip();
                i++;
                if (optionRaw.isEmpty()  optionRaw.startsWith("#")) {
                    continue;
                }
                if (optionRaw.equals("e")  optionRaw.startsWith("e ")) {
                    String tail = optionRaw.substring(1).strip();
                    if (!tail.isEmpty()) {
                        throw error(optionLineNumber, "Лишние символы после 'e'");
                    }
                    closed = true;
                    break;
                }
                if (optionRaw.charAt(0) != '-') {
                    throw error(optionLineNumber, "Ожидался вариант '- ...' или конец блока 'e'");
                }
                String body = optionRaw.substring(1).strip();

                boolean endsBlock = false;
                if (body.endsWith("e") && (body.length() == 1
                         Character.isWhitespace(body.charAt(body.length() - 2))
                        || body.charAt(body.length() - 2) == '+')) {
                    body = body.substring(0, body.length() - 1).stripTrailing();
                    endsBlock = true;
                }

                boolean correct = body.endsWith("+");
                if (correct) {
                    body = body.substring(0, body.length() - 1).stripTrailing();
                }
                if (body.isEmpty()) {
                    throw error(optionLineNumber, "Пустой вариант ответа");
                }
                options.add(new QuizSpec.Option(body, correct));
                if (endsBlock) {
                    closed = true;
                    break;
                }
            }
            questions.add(new QuizSpec.Question(questionText, type, options, null));
        }
        return new QuizSpec(title, h1, style.strip(), questions, source);
    }
	
}
