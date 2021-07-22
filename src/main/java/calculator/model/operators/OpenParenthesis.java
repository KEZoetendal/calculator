package calculator.model.operators;

import calculator.model.interfaces.CalculationElement;
import calculator.model.interfaces.Operator;

/**
 * @author Karin Zoetendal
 * Klasse voor haakje openen. Precendence op max gezet, zodat ook wanneer calculatie-opties worden toegevoegd,
 * haakjes altijd eerst worden uitgevoerd.
 */

public class OpenParenthesis extends OperatorElement implements CalculationElement, Operator {

    public OpenParenthesis(String operator) {
        super(operator);
    }

    @Override
    public Integer precedence() {
        return Integer.MAX_VALUE;
    }
}