import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 5; you should definitely add more tests!
 */
public class ExpressionParserPartialTester {
	private ExpressionParser _parser;

	@BeforeEach
	/**
	 * Instantiates the actors and movies graphs
	 */
	public void setUp() throws IOException {
		_parser = new SimpleExpressionParser();
	}

	@Test
	/**
	 * Just verifies that the SimpleExpressionParser could be instantiated without
	 * crashing.
	 */
	public void finishedLoading() {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression1() throws ExpressionParseException {
		final String expressionStr = "a+b";
		final String parseTreeStr = "+\n\ta\n\tb\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression2() throws ExpressionParseException {
		final String expressionStr = "13*x";
		final String parseTreeStr = "*\n\t13\n\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression3() throws ExpressionParseException {
		final String expressionStr = "4*(z+5*x)";
		final String parseTreeStr = "*\n\t4\n\t()\n\t\t+\n\t\t\tz\n\t\t\t*\n\t\t\t\t5\n\t\t\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));

	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten1() throws ExpressionParseException {
		final String expressionStr = "1+2+3";
		final String parseTreeStr = "+\n\t1\n\t2\n\t3\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten2() throws ExpressionParseException {
		final String expressionStr = "(x+(x)+(x+x)+x)";
		final String parseTreeStr = "()\n\t+\n\t\tx\n\t\t()\n\t\t\tx\n\t\t()\n\t\t\t+\n\t\t\t\tx\n\t\t\t\tx\n\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpressionAndFlatten3() throws ExpressionParseException {
		final String expressionStr = "1+2+3+4+5+6";
		final String parseTreeStr = "+\n\t1\n\t2\n\t3\n\t4\n\t5\n\t6\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException1() throws ExpressionParseException {
		final String expressionStr = "1+2+";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException2() throws ExpressionParseException {
		final String expressionStr = "((()))";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException3() throws ExpressionParseException {
		final String expressionStr = "()()";
		Assertions.assertThrows(ExpressionParseException.class, () -> _parser.parse(expressionStr));
	}
}
