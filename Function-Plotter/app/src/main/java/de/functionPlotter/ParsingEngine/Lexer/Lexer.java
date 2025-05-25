package de.functionPlotter.ParsingEngine.Lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private int pos = 0;

    public Lexer() {}

    public List<Token> tokenize(String input) {
        this.pos = 0;
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char currentChar = input.charAt(pos);
            if (Character.isWhitespace(currentChar)) {
                pos++;
            } else if (Character.isDigit(currentChar) || currentChar == '.') {
                tokens.add(readNumber(input));
            } else if (Character.isLetter(currentChar)) {
                tokens.add(readIdentifier(input));
            } else {
                switch (currentChar) {
                    case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                    case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                    case '¯': tokens.add(new Token(TokenType.UNARYMINUS, "¯")); break;
                    case '*': tokens.add(new Token(TokenType.MULTIPLY, "*")); break;
                    case '/': tokens.add(new Token(TokenType.DIVIDE, "/")); break;
                    case '(': tokens.add(new Token(TokenType.OPENPARENTHESIS, "(")); break;
                    case ')': tokens.add(new Token(TokenType.CLOSEPARENTHESIS, ")")); break;
                    case '^': tokens.add(new Token(TokenType.EXPONENT, "^")); break;
                    default: throw new RuntimeException("Unbekanntes Zeichen: " + currentChar);
                }
                pos++;
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token readNumber(String input) {
        int start = pos;
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) pos++;
        return new Token(TokenType.NUMBER, input.substring(start, pos));
    }

    private Token readIdentifier(String input) {
        int start = pos;
        while (pos < input.length() && (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos) == '_')) pos++;
        String text = input.substring(start, pos);

        if (text.matches("sin|cos|tan|log|sqrt")) {
            return new Token(TokenType.FUNCTION, text);
        } else {
            return new Token(TokenType.IDENTIFIER, text);
        }
    }

}
