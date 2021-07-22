package calculator.model;

import calculator.model.interfaces.CalculationElement;
import calculator.model.interfaces.Operator;
import calculator.model.operations.*;
import calculator.model.operators.CloseParenthesis;
import calculator.model.operators.OpenParenthesis;
import calculator.model.operators.OperatorElement;

import java.util.*;

/**
 * @author Karin Zoetendal
 * Deze klasse maakt van de collectie met de infix CalculatieElementen een collectie in postfix volgorde.
 */

public class InfixToPostfixConverter {

    private static final Map<String, Operator> OPERATORS = Map.of(
            "/", new Division("/"),
            "*", new Multiplication("*"),
            "+", new Addition("+"),
            "-", new Subtraction("-"),
            "^", new Power("^"),
            "(", new OpenParenthesis("("),
            ")", new CloseParenthesis(")")
    );

    ArrayList<CalculationElement> output = new ArrayList<>();

    public void ProcessOperators(Stack<CalculationElement> st, CalculationElement calculationElement, CalculationElement top) {

        // Zolang stack groter dan 0 is, en precedence huidige element is groter of gelijk aan bovenste in de stack,
        // pop van de stack.
        while (!st.isEmpty() && OPERATORS.get(calculationElement.toString()).precedence() >= OPERATORS.get(top.toString()).precedence()) {
            CalculationElement p = st.pop();

            // Indien haakje openen stoppen, anders element toevoegen aan output
            if (OPERATORS.get(p.toString()) instanceof OpenParenthesis) {
                break;
            }
            output.add(p);

            // Waarde van element "top" opnieuw vaststellen (bovenste van de stack, zolang stack niet leeg is)
            if (!st.isEmpty()) {
                top = st.lastElement();
            }
        }
    }

    public Collection<CalculationElement> convertFromInfixToPostFix(Collection<CalculationElement> e) {

        ArrayList<CalculationElement> calculationElements = new ArrayList<>(e);
        Stack<CalculationElement> stack = new Stack<>();
        // var stack = new LinkedList<Operator>();

        for (CalculationElement calculationElement : calculationElements) {
            // Als huidige token een operator is
            if (calculationElement instanceof OperatorElement) {

                // Als stack leeg is, of als huidige element een haakje openen is, push in de stack
                if (stack.isEmpty() || OPERATORS.get(calculationElement.toString()) instanceof OpenParenthesis) {
                    stack.push(calculationElement);

                } else {
                    CalculationElement top = stack.lastElement();

                    // Als huidige element haakje sluiten is
                    if (OPERATORS.get(calculationElement.toString()) instanceof CloseParenthesis) {
                        ProcessOperators(stack, calculationElement, top);

                        // Als precedence huidige element lager is dan dat van de bovenste van de stack
                    } else if (OPERATORS.get(calculationElement.toString()).precedence() < OPERATORS.get(top.toString()).precedence()) {
                        stack.push(calculationElement);
                    } else {
                        ProcessOperators(stack, calculationElement, top);
                        stack.push(calculationElement);
                    }
                }

                // Indien geen operator, dan is het een operand: toevoegen aan output
            } else {
                output.add(calculationElement);
            }
        }

        // Voeg overgebleven operators in de stack toe aan output
        while (stack.size() > 0) {
            output.add(stack.pop());
        }
        return output;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (CalculationElement calculationElement : output) {
            s.append(calculationElement).append(" ");
        }
        return s.toString();
    }
}
