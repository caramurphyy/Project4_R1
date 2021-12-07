import java.util.ArrayList;

/**
 * AdditionExpression
 */
public class AdditionExpression implements CompoundExpression {
    CompoundExpression _parent;
    ArrayList<Expression> _children;

    public AdditionExpression() {
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

        for (int i = 0; i < _children.size(); i++) {
            final Expression parent = _children.get(i);
            if (parent instanceof AdditionExpression) {
                final ArrayList<Expression> children = parent.getChildren();

                for (int j = 0; j < children.size(); j++) {
                    this.addSubexpression(children.get(j));
                }

                _children.remove(parent);

            }
            parent.flatten();
        }

    }

    @Override
    public String convertToString(int indentLevel) {
        ArrayList<String> childrenStrings = new ArrayList<String>();
        for (Expression child : _children) {
            childrenStrings.add(child.convertToString(indentLevel + 1));
        }

        return "\t".repeat(indentLevel) + "+\n" + String.join("\n", childrenStrings) + "\n";
    }

    @Override
    public void addSubexpression(Expression subexpression) {
        _children.add(subexpression);
    }

    @Override
    public ArrayList<Expression> getChildren() {
        return _children;
    }

}