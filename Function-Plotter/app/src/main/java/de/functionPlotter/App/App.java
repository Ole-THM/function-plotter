package de.functionPlotter.App;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.ParsingEngine.Parser.Parser;
import de.functionPlotter.Utils.GlobalContext;

import java.text.ParseException;

public class App {

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello, World!");
        Setup.setUp();
        GlobalContext.VARIABLES.add("x", Parser.parseInfix("2 + 3"));
        ASTNodeI ast = Parser.parseInfix("sin(10)");
        System.out.println(ast.toStringInfix());
        System.out.println(ast.evaluate());
        ASTNodeI ast2 = Parser.parseRPN("10 sin");
        System.out.println(ast2.evaluate());
    }

}

