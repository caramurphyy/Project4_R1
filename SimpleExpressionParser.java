/**
 * Starter code to implement an ExpressionParser. Your parser methods should use
 * the following grammar:
 * S := S+M | M
 * M := M*P | P
 * P := (S) | L
 * L := [a-z] | [0-9]+
 */
public class SimpleExpressionParser implements ExpressionParser {

	/**
	 * Attempts to create an expression tree -- flattened as much as possible --
	 * from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * 
	 * @param str the string to parse into an expression tree
	 * 
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse(String str) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}

	/**
	 * Parses an expressions from the specified string.
	 * 
	 * @param str the string to be parsed
	 * @return Expression
	 */
	protected Expression parseExpression(String str) {
		return parseS(str);
	}

	/**
	 * Parses the S production rule.
	 * 
	 * @param str
	 * @return Expression
	 */
	protected Expression parseS(String str) { // S -> S+M || M
		return parseBinaryOperator(str, '+', x -> parseS(x), x -> parseM(x),
				new AdditionExpression());
	}

	/**
	 * Parses the M production rule.
	 * 
	 * @param str
	 * @return Expression
	 */
	protected Expression parseM(String str) { // M -> M*P | P
		return parseBinaryOperator(str, '*', x -> parseM(x), x -> parseP(x),
				new MultiplicationExpression());
	}

	private static interface StringToExpressionFunction {
		Expression apply(String s);
	}

	/**
	 * Parses a binary operator and its operands and returns a Expression object
	 * 
	 * @param str the string to be parsed
	 * @param op  the operator to be parsed
	 * @param m1  the function to parse the first operand
	 * @param m2  the function to parse the second operand
	 * @param ce  the Expression object to be returned
	 * 
	 * @return Expression returns ce with the parsed children
	 */
	Expression parseBinaryOperator(String str, char op, StringToExpressionFunction m1, StringToExpressionFunction m2,
			CompoundExpression ce) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == op) {
				Expression a = m1.apply(str.substring(0, i));
				Expression b = m2.apply(str.substring(i + 1));

				if (a != null && b != null) {
					ce.addSubexpression(a);
					ce.addSubexpression(b);
					a.setParent(ce);
					b.setParent(ce);
					return ce;
				}
			}
		}
		return m2.apply(str);
	}

	/**
	 * Parses the P production rule.
	 * 
	 * @param str
	 * @return Expression
	 */
	protected Expression parseP(String str) { // P -> (S) | L
		if (str.length() >= 2 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
			ParentheticalExpression pe = new ParentheticalExpression();
			Expression s = parseS(str.substring(1, str.length() - 1));
			if (s != null) {
				pe.addSubexpression(s);
				s.setParent(pe);
				return pe;
			}
		}

		return parseL(str);
	}

	/**
	 * Parses the L production rule.
	 * 
	 * @param str
	 * @return Expression
	 */
	protected Expression parseL(String str) {
		// integer or a character
		if (isNumeric(str) || (str.length() == 1 && Character.isLetter(str.charAt(0)))) {
			return new LiteralExpression(str);
		}

		return null;
	}

	/**
	 * Checks if the specified string is numeric.
	 * 
	 * @param str
	 * @return boolean
	 */
	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}