package de.functionPlotter.ParsingEngine.Parser;


import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.ParsingEngine.Lexer.Lexer;
import de.functionPlotter.ParsingEngine.Lexer.Token;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UPNParser implements ParserI {
    private List<Token> tokens;
    private ArrayList<Token> stack;
    private int pos = 0;
    private final Lexer lexer;

    public UPNParser() { this.lexer = new Lexer(); }

    public ASTNodeI parse(String input) throws ParseException {
        this.tokens = this.lexer.tokenize(input);
        this.pos = 0;
        return null;
    }

    @Override
    public boolean isValid(String input) throws ParseException {
        return false;
    }


}