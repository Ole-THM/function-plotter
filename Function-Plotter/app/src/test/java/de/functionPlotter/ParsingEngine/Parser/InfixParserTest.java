package de.functionPlotter.ParsingEngine.Parser;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfixParserTest {

    @Test
    void testSimpleAddition() throws ParseException {
        InfixParser parser = new InfixParser();
        ASTNodeI ast = parser.parse("2+3");
        assertEquals("2 + 3", ast.toStringInfix());
        assertEquals(5.0, ast.evaluate());
    }

    @Test
    void testOperatorPrecedence() throws ParseException {
        InfixParser parser = new InfixParser();
        ASTNodeI ast = parser.parse("2+3*4");
        assertEquals("2 + 3 * 4", ast.toStringInfix());
        assertEquals(14.0, ast.evaluate());
    }

    @Test
    void testParentheses() throws ParseException {
        InfixParser parser = new InfixParser();
        ASTNodeI ast = parser.parse("(2.0123+3)*4");
        System.out.println(ast.toStringInfix());
        assertEquals("(2,0123 + 3) * 4", ast.toStringInfix());
        assertEquals(20.0492, ast.evaluate());
    }

    @Test
    void testFunctionCall() throws ParseException {
        InfixParser parser = new InfixParser();
        ASTNodeI ast = parser.parse("sin(0)");
        assertEquals("sin(0)", ast.toStringInfix());
        assertEquals(0.0, ast.evaluate(), 1e-9);
    }

    @Test
    void testUnaryMinus() throws ParseException {
        InfixParser parser = new InfixParser();
        ASTNodeI ast = parser.parse("-5");
        assertEquals("-5", ast.toStringInfix());
        assertEquals(-5.0, ast.evaluate());
    }
}