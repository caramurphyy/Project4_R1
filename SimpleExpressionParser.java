/**
 * Starter code to implement an ExpressionParser. Your parser methods should use
 * the following grammar:
 * S := S+M | M
 * M := M*P | P
 * P := (S) | L
 * L := [a-z] | [0-9]+
 */
public class SimpleExpressionParser implements ExpressionParser {
	/*
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

	protected Expression parseExpression(String str) {
		return parseS(str);
	}

	// try all occurances of +, try if there is S recursively before that point and
	// an M after that point
	protected Expression parseS(String str) { // S -> S+M || M
		return parseHelper(str, '+', x -> parseS(x), x -> parseM(x),
				new AdditionExpression());
	}

	protected Expression parseM(String str) { // M -> M*P | P
		return parseHelper(str, '*', x -> parseM(x), x -> parseP(x),
				new MultiplicationExpression());
	}

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

	protected Expression parseL(String str) {
		// integer or a character
		if (isNumeric(str) || (str.length() == 1 && Character.isLetter(str.charAt(0)))) {
			return new LiteralExpression(str);
		}

		return null;
	}

	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	Expression parseHelper(String str, char op, StringToExpressionFunction m1, StringToExpressionFunction m2,
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
					ce.flatten();
					return ce;
				}
			}
		}
		return m2.apply(str);
	}

}