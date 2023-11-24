package Scanner;


import FA.FiniteAutomaton;

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
    private static final String IDENTIFIER_REGEX = "(?<![\\d])\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String INT_CONSTANT_REGEX = "([+-]?([1-9][0-9]*)|0)";
    private static final String STRING_CONSTANT_REGEX = "\"[a-zA-Z0-9_\\s]*\"";
    private static final String BOOL_CONSTANT_REGEX = "true|false";

    private static final String FLOAT_CONSTANT_REGEX = "[-+]?\\d+(\\.\\d+)?(?!\\w)";


    static {
        try (BufferedReader br = new BufferedReader(new FileReader("token.in"))) {
            String line;
            String currentSection = null;

            while ((line = br.readLine()) != null) {
                if (line.endsWith(":")) {
                    currentSection = line.replace(":", "").trim();
                } else if (currentSection != null) {
                    switch (currentSection) {
                        case "OPERATORS_REGEX" ->
                                OPERATORS_REGEX = (OPERATORS_REGEX != null) ? OPERATORS_REGEX + "|" + line : line;
                        case "SEPARATORS_REGEX" ->
                                SEPARATORS_REGEX = (SEPARATORS_REGEX != null) ? SEPARATORS_REGEX + "|" + line : line;
                        case "RESERVED_WORDS_REGEX" ->
                                RESERVED_WORDS_REGEX = (RESERVED_WORDS_REGEX != null) ? RESERVED_WORDS_REGEX + "|" + line : line;
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
                STRING_CONSTANT_REGEX,
                IDENTIFIER_REGEX,
                INT_CONSTANT_REGEX,
                BOOL_CONSTANT_REGEX,
                FLOAT_CONSTANT_REGEX);

        Pattern pattern = Pattern.compile(regexPattern);

        Matcher matcher = pattern.matcher(inputText);

        int lastMatchEnd = 0;

        while (matcher.find()) {
            String token = matcher.group();

            int firstMatch = matcher.start();
            if (firstMatch > lastMatchEnd) {
                // Lexical error for the portion between last match and the current match
                String errorToken = inputText.substring(lastMatchEnd, matcher.start());
                tokens.add(new Token("LEXICAL_ERROR", errorToken));
            }

            String tokenType = detect(token);
            tokens.add(new Token(tokenType, token));

            lastMatchEnd = matcher.end();
        }

        return tokens;
    }

    @Override
    public String detect(String token) {
        FiniteAutomaton integerConstantFA = new FiniteAutomaton();
        integerConstantFA.readFAFromFile("integerConstantFA.in");
        FiniteAutomaton identifierFA = new FiniteAutomaton();
        identifierFA.readFAFromFile("identifierFA.in");

        if (token.matches(RESERVED_WORDS_REGEX)) {
            return "ReservedWord";
        } else if (token.matches(OPERATORS_REGEX)) {
            return "Operator";
        } else if (token.matches(SEPARATORS_REGEX)) {
            return "Separator";
        } else if (identifierFA.isAccepted(token)) {
            return "Identifier";
        } else if (integerConstantFA.isAccepted(token)) {
            return "IntegerConstant";
        } else if (token.matches(STRING_CONSTANT_REGEX)) {
            return "StringConstant";
        } else if (token.matches(BOOL_CONSTANT_REGEX)) {
            return "BooleanConstant";
        } else if (token.matches(FLOAT_CONSTANT_REGEX)) {
            return "FloatConstant";
        }
        else {
            return "LexicalError";
        }
    }
}
