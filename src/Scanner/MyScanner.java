package Scanner;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyScanner implements IScanner {
    private static String RESERVED_WORDS_REGEX;
    private static String OPERATORS_REGEX;
    private static String SEPARATORS_REGEX;
    private static final String IDENTIFIER_REGEX = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String INT_CONSTANT_REGEX = "[+-]?[1-9][0-9]*|0";
    private static final String STRING_CONSTANT_REGEX = "\"[a-zA-Z0-9_\\s]*\"";
    private static final String BOOL_CONSTANT_REGEX = "true|false";
    private static final String FLOAT_CONSTANT_REGEX = "[-+]?\\d+(\\.\\d+)?";

    static {
        try (BufferedReader br = new BufferedReader(new FileReader("token.in"))) {
            String line;
            String currentSection = null;

            while ((line = br.readLine()) != null) {
                if (line.endsWith(":")) {
                    currentSection = line.replace(":", "").trim();
                } else if (currentSection != null) {
                    String trimmedLine = line.trim();
                    switch (currentSection) {
                        case "OPERATORS_REGEX" ->
                                OPERATORS_REGEX = (OPERATORS_REGEX != null) ? OPERATORS_REGEX + "|" + trimmedLine : trimmedLine;
                        case "SEPARATORS_REGEX" ->
                                SEPARATORS_REGEX = (SEPARATORS_REGEX != null) ? SEPARATORS_REGEX + "|" + trimmedLine : trimmedLine;
                        case "RESERVED_WORDS_REGEX" ->
                                RESERVED_WORDS_REGEX = (RESERVED_WORDS_REGEX != null) ? RESERVED_WORDS_REGEX + "|" + trimmedLine : trimmedLine;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Token> scan(String inputText) {
        List<Token> tokens = new ArrayList<>();
        String regexPattern = String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)",
                RESERVED_WORDS_REGEX,
                OPERATORS_REGEX,
                SEPARATORS_REGEX,
                IDENTIFIER_REGEX,
                INT_CONSTANT_REGEX,
                STRING_CONSTANT_REGEX,
                BOOL_CONSTANT_REGEX,
                FLOAT_CONSTANT_REGEX);

        Pattern pattern = Pattern.compile(regexPattern);

        Matcher matcher = pattern.matcher(inputText);

        while (matcher.find()) {
            String token = matcher.group();
            String tokenType = detect(token);
            tokens.add(new Token(tokenType, token));
        }

        return tokens;
    }

    @Override
    public String detect(String token) {
        if (token.matches(RESERVED_WORDS_REGEX)) {
            return "ReservedWord";
        } else if (token.matches(OPERATORS_REGEX)) {
            return "Operator";
        } else if (token.matches(SEPARATORS_REGEX)) {
            return "Separator";
        } else if (token.matches(IDENTIFIER_REGEX)) {
            return "Identifier";
        } else if (token.matches(INT_CONSTANT_REGEX)) {
            return "IntegerConstant";
        } else if (token.matches(STRING_CONSTANT_REGEX)) {
            return "StringConstant";
        } else if (token.matches(BOOL_CONSTANT_REGEX)) {
            return "BooleanConstant";
        } else if (token.matches(FLOAT_CONSTANT_REGEX)) {
            return "FloatConstant";
        } else {
            return "LexicalError";
        }
    }
}
