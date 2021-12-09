import java.util.stream.Collectors;

public abstract class CompoundExpressionImpl extends ExpressionImpl implements CompoundExpression {

    /**
     * An abstract method to be implemented by the subclasses to define the
     * representation of their operation in convertToString
     * 
     * @return String
     */
    abstract protected String getOperator();

    /**
     * 
     * @param indentLevel the indent level of the expression in the tree
     * @return String representation of the expression
     */
    @Override
    public String convertToString(int indentLevel) {
        String childrenString = _children.stream().map(child -> child.convertToString(indentLevel + 1))
                .collect(Collectors.joining(""));

        return "\t".repeat(indentLevel) + getOperator() + "\n" + childrenString;
    }

    /**
     * @param subexpression the subexpression to be added as a child to the compound
     *                      expression
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        _children.add(subexpression);
    }
}
