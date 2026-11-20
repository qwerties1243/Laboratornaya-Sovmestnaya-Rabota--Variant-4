package quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class QuizSpec {
	private final String title;
    private final String h1;
    private final String style;
    private final List<Question> questions;
    private final transient String source;

    public QuizSpec(String title, String h1, String style, List<Question> questions, String source) {
        this.title = title;
        this.h1 = h1;
        this.style = style;
        this.questions = Collections.unmodifiableList(new ArrayList<>(questions));
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getH1() {
        return h1;
    }

    public String getStyle() {
        return style;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public enum AnswerType {
        RADIO("r"), CHECKBOX("c"), TEXT("t");

        private final String code;

        AnswerType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static AnswerType fromCode(String code) {
            for (AnswerType type : values()) {
                if (type.code.equalsIgnoreCase(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Неизвестный тип ответа: " + code);
        }
    }

    public static final class Option {
        private final String text;
        private final boolean correct;

        public Option(String text, boolean correct) {
            this.text = text;
            this.correct = correct;
        }

        public String getText() {
            return text;
        }

        public boolean isCorrect() {
            return correct;
        }
    }

    public static final class Question {
        private final String text;
        private final AnswerType answerType;
        private final List<Option> options;
        private final String expectedText;

        public Question(String text, AnswerType answerType, List<Option> options, String expectedText) {
            this.text = text;
            this.answerType = answerType;
            this.options = Collections.unmodifiableList(new ArrayList<>(options));
            this.expectedText = expectedText;
        }

        public String getText() {
            return text;
        }

        public AnswerType getAnswerType() {
            return answerType;
        }

        public List<Option> getOptions() {
            return options;
        }

        public String getExpectedText() {
            return expectedText;
        }

        public int correctCount() {
            if (answerType == AnswerType.TEXT) {
                return expectedText == null || expectedText.isBlank() ? 0 : 1;
            }
            return (int) options.stream().filter(Option::isCorrect).count();
        }
    }
}
