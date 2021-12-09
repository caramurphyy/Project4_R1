import java.util.ArrayList;

public abstract class ExpressionImpl implements Expression {
    CompoundExpression _parent;
    ArrayList<Expression> _children;

    public ExpressionImpl() {
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
        for (Expression child : _children) {
            child.flatten();
        }

        ArrayList<Expression> newChildren = new ArrayList<Expression>();

        for (Expression child : _children) {
            if (child.getClass() == this.getClass()) {
                newChildren.addAll(child.getChildren());
            } else {
                newChildren.add(child);
            }
        }

        _children = newChildren;
    }

    // We won't implment convertToString for ExpressionImpl and leave that to the
    // subclasses

    @Override
    public ArrayList<Expression> getChildren() {
        return _children;
    }

}
