package de.functionPlotter.ParsingEngine.Lexer;

public enum TokenType {
    NUMBER,                                             // Numeric literals (integers, floats)
    IDENTIFIER,                                         // Identifiers (variable names)
    FUNCTION,                                           // Native functions like sin, cos, log, sqrt etc.
    PLUS, MINUS, MULTIPLY, DIVIDE, EXPONENT,            // Arithmetic operators
    OPENPARENTHESIS, CLOSEPARENTHESIS,                  // Parentheses
    EOF                                                 // End of file token
}
