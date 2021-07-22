package calculator.model.operations;

import calculator.model.operands.OperandElement;
import calculator.model.interfaces.BinaryOperator;
import calculator.model.interfaces.Operator;
import calculator.model.operators.OperatorElement;

import java.math.BigDecimal;

/**
 * @author Karin Zoetendal
 * Klasse t.b.v. machtsverheffen
 */

public class Power extends OperatorElement implements Operator, BinaryOperator {

    public Power(String operator) {
        super(operator);
    }

    @Override
    public BigDecimal execute(OperandElement left, OperandElement right) {
        return left.getOperand().pow(right.getOperand().intValue());
    }

    @Override
    public Integer precedence() {
        return 2;
    }

}
