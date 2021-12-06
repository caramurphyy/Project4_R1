/**
 * Main
 */
public class Main {

    public static void main(String[] args) throws ExpressionParseException {
        SimpleExpressionParser se = new SimpleExpressionParser();

        System.out.println(se.parse("1+2"));
    }
}