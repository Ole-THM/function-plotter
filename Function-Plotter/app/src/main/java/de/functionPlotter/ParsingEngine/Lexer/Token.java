package de.functionPlotter.ParsingEngine.Lexer;

public record Token(TokenType type, String text) {

    @Override
    public String toString() {
        return type + "('" + text + "')";
    }

}
