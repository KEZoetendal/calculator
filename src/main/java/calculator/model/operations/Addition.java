package calculator.model.operations;

import calculator.model.operands.OperandElement;
import calculator.model.interfaces.BinaryOperator;
import calculator.model.interfaces.Operator;
import calculator.model.operators.OperatorElement;

import java.math.BigDecimal;

/**
 * @author Karin Zoetendal
 * Klasse t.b.v. optellen
 */

public class Addition extends OperatorElement implements Operator, BinaryOperator {

    public Addition(String operator) {
        super(operator);
    }

    @Override
    public BigDecimal execute(OperandElement left, OperandElement right) {
        return left.getOperand().add(right.getOperand());
    }

    @Override
    public Integer precedence() {
        return 4;
    }
}
