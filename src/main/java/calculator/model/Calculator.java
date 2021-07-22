package calculator.model;

import calculator.model.interfaces.CalculationElement;

import java.util.Collection;
import java.util.InputMismatchException;

/**
 * @author Karin Zoetendal
 * Klasse om infix string te converteren naar postfix en resultaat calculatie terug te geven.
 */

public class Calculator {

    public String calculate(String infixExpression) {
        Expressionparser expressionparser = new Expressionparser();

        try {
            Collection<CalculationElement> elementList = expressionparser.parse(infixExpression);
            InfixToPostfixConverter infixToPostfixConverter = new InfixToPostfixConverter();
            elementList = infixToPostfixConverter.convertFromInfixToPostFix(elementList);

            PostfixEvaluator postfixEvaluator = new PostfixEvaluator();

            return postfixEvaluator.evaluate(elementList);
        } catch (InputMismatchException e) {
            return "Error";
        }
    }
}
