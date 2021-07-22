package calculator.model.operators;

import calculator.model.interfaces.CalculationElement;

/**
 * @author Karin Zoetendal
 * Klasse voor operators, t.b.v. converteren infix naar postfix.
 */

public class OperatorElement implements CalculationElement {

    String c;

    public OperatorElement(String operator) {
        c = operator;
    }

    @Override
    public String toString() {
        return c;
    }
}
