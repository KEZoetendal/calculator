package calculator.model.operations;

import calculator.model.operands.OperandElement;
import calculator.model.interfaces.BinaryOperator;
import calculator.model.interfaces.Operator;
import calculator.model.operators.OperatorElement;

import java.math.BigDecimal;

/**
 * @author Karin Zoetendal
 * Klasse t.b.v. vermenigvuldigen
 */

public class Multiplication extends OperatorElement implements Operator, BinaryOperator {

    public Multiplication(String operator) {
        super(operator);
    }

    @Override
    public BigDecimal execute(OperandElement left, OperandElement right) {
        return left.getOperand().multiply(right.getOperand());
    }

    @Override
    public Integer precedence() {
        return 3;
    }
}
