package quiz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class HtmlQuizGenerator {
  
    private static String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static final String TEMPLATE = """
<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>{{TITLE}}</title>
<style>
  body { font-family: Georgia, 'Times New Roman', serif; margin: 24px; color: #111; }
  h1 { font-size: 34px; margin: 0 0 28px; }
  fieldset { border: 0; margin: 0 0 30px; padding: 0; }
  legend { font-size: 22px; font-weight: bold; margin-bottom: 10px; }
  .option { display: block; margin: 5px 0; font-size: 19px; cursor: pointer; }
  .option input { width: 18px; height: 18px; margin: 0 8px 0 0; vertical-align: -2px; }
  input[type=text] { width: 215px; padding: 5px; font-size: 17px; font-family: inherit; }
  button { margin-top: 12px; padding: 9px 17px; font: 16px inherit; color: #fff;
           background: #5cb85c; border: 0; border-radius: 4px; cursor: pointer; }
  button:hover { background: #4cae4c; }
  #result { margin-top: 18px; font-size: 20px; font-weight: bold; }
  .wrong { color: #c00; }
  .right { color: #2d8a2d; }
  .answer-line { font-size: 16px; font-weight: normal; margin: 3px 0 0 26px; color: #555; }

  body.dark { background: #1e1e1e; color: #eee; }
  body.dark button { background: #3f8f3f; }
  body.compact h1 { font-size: 26px; margin-bottom: 16px; }
  body.compact fieldset { margin-bottom: 16px; }
  body.compact legend { font-size: 18px; }
  body.compact .option { font-size: 16px; margin: 2px 0; }
</style>
</head>
<body class="{{STYLE_CLASS}}">
<h1>{{H1}}</h1>
<form id="quiz-form"></form>
<div id="result" aria-live="polite"></div>

<script id="quiz-spec" type="application/json">
{{QUIZ_JSON}}
</script>
<script>
</script>
</body>
</html>
""";
}