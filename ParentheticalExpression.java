import java.util.ArrayList;

public class ParentheticalExpression implements CompoundExpression {
    CompoundExpression _parent;
    ArrayList<Expression> _children;

    public ParentheticalExpression() {
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
        // TODO Auto-generated method stub

    }

    @Override
    public String convertToString(int indentLevel) {
        ArrayList<String> childrenStrings = new ArrayList<String>();
        for (Expression child : _children) {
            childrenStrings.add(child.convertToString(indentLevel + 1));
        }

        return "\t".repeat(indentLevel) + "()\n" + String.join("\n", childrenStrings) + "\n";
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        _children.add(subexpression);
    }

}
