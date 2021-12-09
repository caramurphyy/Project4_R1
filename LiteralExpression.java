import java.util.ArrayList;

public class LiteralExpression extends ExpressionImpl {
    final String _literal;

    public LiteralExpression(String literal) {
        _literal = literal;
    }

    /**
     * @param indentLevel the indentation level of the literal in the tree
     * @return String representation of the literal
     */
    @Override
    public String convertToString(int indentLevel) {
        return "\t".repeat(indentLevel) + _literal + "\n";
    }
}
