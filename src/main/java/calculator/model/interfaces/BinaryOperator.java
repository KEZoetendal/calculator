package calculator.model.interfaces;

import calculator.model.operands.OperandElement;

import java.math.BigDecimal;

/**
 * @author Karin Zoetendal
 * Interface voor berekeningen met 2 operands.
 */

public interface BinaryOperator {

    BigDecimal execute(OperandElement left, OperandElement right);
}
