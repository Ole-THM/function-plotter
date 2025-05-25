package de.functionPlotter.App;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.ParsingEngine.Parser.InfixParser;
import de.functionPlotter.Utils.GlobalContext;

import java.text.ParseException;

public class App {
    public static void main(String[] args) throws ParseException {

        System.out.println("Hello, World!");
        InfixParser parser = new InfixParser();
        GlobalContext.VARIABLES.add("x", parser.parse("2 + 3"));
        ASTNodeI ast = parser.parse("10^2");
        System.out.println(ast.toStringInfix());
        System.out.println(ast.evaluate());
    }
}