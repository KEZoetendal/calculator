package calculator.model.operands;

import calculator.model.interfaces.CalculationElement;

import java.math.BigDecimal;

/**
 * @author Karin Zoetendal
 * Klasse voor operands, t.b.v. omzetten infix naar postfix. Gekozen voor BigDecimal voor nauwkeurigheid berekeningen.
 */

public class OperandElement implements CalculationElement {

    BigDecimal operand;

    public OperandElement(String operand) {
        this.operand = new BigDecimal(operand);
    }

    public BigDecimal getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return operand.toString();
    }

}
