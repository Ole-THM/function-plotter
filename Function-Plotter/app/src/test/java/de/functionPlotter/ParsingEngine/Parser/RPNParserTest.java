package de.functionPlotter.ParsingEngine.Parser;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RPNParserTest {

    private RPNParser parser;

    @BeforeEach
    void setUp() {
        parser = new RPNParser();
    }

    @Test
    void testSimpleAddition() throws ParseException {
        ASTNodeI ast = parser.parse("2 3 +");
        assertEquals(5.0, ast.evaluate(), 1e-9);
        assertEquals("2 3 +", ast.toStringRPN());
    }

    @Test
    void testSimpleSubtraction() throws ParseException {
        ASTNodeI ast = parser.parse("5 2 -");
        assertEquals(3.0, ast.evaluate(), 1e-9);
        assertEquals("5 2 -", ast.toStringRPN());
    }

    @Test
    void testSimpleMultiplication() throws ParseException {
        ASTNodeI ast = parser.parse("4 3 *");
        assertEquals(12.0, ast.evaluate(), 1e-9);
        assertEquals("4 3 *", ast.toStringRPN());
    }

    @Test
    void testSimpleDivision() throws ParseException {
        ASTNodeI ast = parser.parse("8 2 /");
        assertEquals(4.0, ast.evaluate(), 1e-9);
        assertEquals("8 2 /", ast.toStringRPN());
    }

    @Test
    void testOperatorPrecedence() throws ParseException {
        ASTNodeI ast = parser.parse("2 3 * 4 +");
        assertEquals(10.0, ast.evaluate(), 1e-9);
        assertEquals("2 3 * 4 +", ast.toStringRPN());
    }

    @Test
    void testExponentiation() throws ParseException {
        ASTNodeI ast = parser.parse("2 3 ^");
        assertEquals(8.0, ast.evaluate(), 1e-9);
        assertEquals("2 3 ^", ast.toStringRPN());
    }

    @Test
    void testFunctionCall() throws ParseException {
        ASTNodeI ast = parser.parse("0 sin");
        assertEquals(0.0, ast.evaluate(), 1e-9);
        assertEquals("0 sin", ast.toStringRPN());
    }

    @Test
    void testMoreComplexExpression() throws ParseException {
        ASTNodeI ast = parser.parse("3 4.0001 + 2 * 7 /");
        assertEquals((3 + 4.0001) * 2 / 7, ast.evaluate(), 1e-9);
        assertEquals("3 4,0001 + 2 * 7 /", ast.toStringRPN());
    }

}