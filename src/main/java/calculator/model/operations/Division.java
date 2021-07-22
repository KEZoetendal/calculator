package calculator.model.operations;

import calculator.model.operands.OperandElement;
import calculator.model.interfaces.BinaryOperator;
import calculator.model.interfaces.Operator;
import calculator.model.operators.OperatorElement;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Karin Zoetendal
 * Klasse t.b.v. delen
 */

public class Division extends OperatorElement implements Operator, BinaryOperator {

    // Scale i.v.m. afronden infinitieve uitkomsten
    public final int SCALE = 50;

    public Division(String operator) {
        super(operator);
    }

    @Override
    public BigDecimal execute(OperandElement left, OperandElement right) {
        return left.getOperand().divide(right.getOperand(), SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public Integer precedence() {
        return 3;
    }
}
