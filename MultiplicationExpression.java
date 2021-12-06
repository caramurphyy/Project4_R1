import java.util.ArrayList;

public class MultiplicationExpression implements CompoundExpression {
    CompoundExpression _parent;
    ArrayList<Expression> _children;

    public MultiplicationExpression() {
        _parent = null;
        _children = new ArrayList<Expression>();
    }

    @Override
    public CompoundExpression getParent() {
        return _parent;
    }

    @Override
    public void setParent(CompoundExpression parent) {
        _parent = parent;

    }

    @Override
    public Expression deepCopy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flatten() {
        // whenever a child c has a type (AdditiveExpression, MultiplicativeExpression,
        // etc.)
        // that is the same as the type of its parent p, you should (recursively)
        // replace c with
        // its own children, which thereby become children of p.

        for (Expression child : _children) {
            CompoundExpression p = child.getParent();

        }

    }

    @Override
    public String convertToString(int indentLevel) {
        ArrayList<String> childrenStrings = new ArrayList<String>();
        for (Expression child : _children) {
            childrenStrings.add(child.convertToString(indentLevel + 1));
        }

        return "\t".repeat(indentLevel) + "*\n" + String.join("\n", childrenStrings) + "\n";
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        _children.add(subexpression);

    }

}
