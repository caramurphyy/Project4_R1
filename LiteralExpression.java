import java.util.ArrayList;

public class LiteralExpression implements Expression {

    String _literal;

    LiteralExpression(String literal) {
        _literal = literal;
    }

    @Override
    public CompoundExpression getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        // TODO Auto-generated method stub

    }

    @Override
    public Expression deepCopy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flatten() {
        // TODO Auto-generated method stub

    }

    @Override
    public String convertToString(int indentLevel) {
        return "\t".repeat(indentLevel) + _literal;
    }

    @Override
    public ArrayList<Expression> getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

}
