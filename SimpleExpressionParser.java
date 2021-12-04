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
		Expression expression;

		for (int i = 0; i < str.length(); i++) {

			if (str.length() <= 1) { // end must be L
				Expression end = parseL(str);
			}

			if (str.charAt(i) == '+') {

				parseS(str);
			}
		}
		// TODO implement me
		return null;
	}

	// try all occurances of +, try if there is S recursively before that point and
	// an M after that point
	protected Expression parseS(String str) { // S -> S+M || M
		for (int i = 0; i < str.length(); i++) {
			if ((str.substring(0, i) != null) && ((str.substring(i + 1)) != null)) { // necessary?

				if (str.charAt(i) == '+') {
					Expression s = parseS(str.substring(0, i));
					Expression m = parseM(str.substring(i + 1));
					if (s != null && m != null) {
						return new AdditionExpression(s, m);
					}
				}
			}
		}
		Expression m = parseM(str);
		if (m != null) {
			return m;
		}
		return null;

	}

	protected Expression parseM(String str) { // M -> M*P | P
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '*') {
				Expression m = parseM(str.substring(0, i));
				Expression p = parseP(str.substring(i + 1));

				if (m != null && p != null) {
					return new MultiplicationExpression(m, p);
				}
			}
		}
		Expression p = parseP(str);
		if (p != null) {
			return p;
		}

		return null;

	}

	protected Expression parseP(String str) { // P -> (S) | L
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				for (int j = i; j < str.length(); j++) {
					if (str.charAt(j) == ')') {
						Expression s = parseS(str.substring(i, j));

						if (s != null) {
							return new AdditionExpression(s);
						}
					}
				}
			}
		}
		Expression l = parseL(str);
		if (l != null) {
			return l;
		}
		return null;

	}

	protected Expression parseL(String str) { // L -> [a-z] | [0-9]+
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) || !Character.isLetter(str.charAt(i))) {
				Expression l = parseL(str);
				return l;
			}
		}
		return null;

	}

	protected Expression AdditionExpression(Expression s, Expression m, CompoundExpression parent) {

		parent.addSubexpression(s);
		parent.addSubexpression(m);

		return parent;

	}

	protected Expression MultiplicationExpression(Expression m, Expression p, CompoundExpression parent) {
		parent.addSubexpression(m);
		parent.addSubexpression(p);

		return parent;

	}

	protected Expression LiteralExpression(Expression l, CompoundExpression parent) {

		parent.addSubexpression(l);

		return parent;

	}

	// data parent expression
}