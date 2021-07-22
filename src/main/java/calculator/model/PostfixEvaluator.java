package calculator.model;

import calculator.model.interfaces.CalculationElement;
import calculator.model.operands.OperandElement;
import calculator.model.operations.*;
import calculator.model.operators.OperatorElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author Karin Zoetendal
 * Klasse om de output van de InfixToPostFixConverter te calculeren.
 * Display scherm kan maar maximaal 26 characters weergeven, dus daar zou nog een afronding moeten plaatsvinden. Omdat
 * ik ook decimaaltekens wil tonen in de output, is dit heel lastig te bereiken en heb ik dat voor nu niet ingevoerd.
 */

public class PostfixEvaluator {

    // Display kan maximaal 26 characters tonen
    public final int MAXIMUM_CHARACTERS_TO_DISPLAY = 26;

    // Indien geen decimalen, niet tonen als x,0 of x.0, maar als x
    public final int MINIMUM_FRACTION_DIGITS = 0;

    // Getal zal altijd bestaan uit minimaal 1 character voor het decimaalteken (x,yyy), dus maximaal aantal achter
    // de komma is max to display - 2 (1 character + decimaalteken)
    public final int MAXIMUM_FRACTION_DIGITS = MAXIMUM_CHARACTERS_TO_DISPLAY - 2;

    // Bij scientific notation zullen er minimaal 3 characters aan het einde staan (E+1), en 2 aan het begin (x,yyyE+1)
    // dus maximaal aantal characters achter het decimaalteken is max to display - 5
    public final int MAXIMUM_SCALE_SCIENTIFIC = MAXIMUM_CHARACTERS_TO_DISPLAY - 5;

    Stack<CalculationElement> stack = new Stack<>();

    public String evaluate(Collection<CalculationElement> converted) {

        var binaryOperations = Map.of(
                "/", new Division("/"),
                "*", new Multiplication("*"),
                "+", new Addition("+"),
                "-", new Subtraction("-"),
                "^", new Power("^")
        );

        ArrayList<CalculationElement> a = new ArrayList<>(converted);

        for (CalculationElement calculationElement : a) {

            // Als token niet een operator is, push operand in de stack
            if (calculationElement instanceof OperandElement) {
                stack.push(calculationElement);
            }

            // Als token operator is, pop bovenste 2 tokens uit de stack als operands rechts en links. Voer de
            // bijbehorende bewerking uit en push het resultaat terug in de stack
            if (calculationElement instanceof OperatorElement) {
                OperandElement right = (OperandElement) stack.pop();
                OperandElement left = (OperandElement) stack.pop();

                OperandElement result = new OperandElement(binaryOperations.get(calculationElement.toString()).execute(left, right).toString());
                stack.push(result);
            }
        }

        // Laatste operand in de stack is de uitkomst van de calculatie
        BigDecimal finalResult = new BigDecimal(stack.pop().toString());

        // Om leesbaarheid te verhogen, converteren naar locale nummerweergave, zodat locale duizend- en
        // decimaalseparator getoond wordt.
        // Als getal langer is dan display maximum (26 karakters), scientific notatie gebruiken
        if (localFormat(finalResult, MINIMUM_FRACTION_DIGITS, MAXIMUM_FRACTION_DIGITS).length() > MAXIMUM_CHARACTERS_TO_DISPLAY) {

            // Als lengte van scientific notation hoger is dan maximaal aantal characters die op het display passen
            int scientificLength = scientificFormat(finalResult, MAXIMUM_SCALE_SCIENTIFIC).length();
            if (scientificLength > MAXIMUM_CHARACTERS_TO_DISPLAY) {

                // scale aanpassen naar beneden, met het aantal characters dat het nu te lang is
                int newScale = MAXIMUM_SCALE_SCIENTIFIC -
                        (scientificFormat(finalResult, MAXIMUM_SCALE_SCIENTIFIC).length() - MAXIMUM_CHARACTERS_TO_DISPLAY);

                return scientificFormat(finalResult, newScale);
            } else {
                return scientificFormat(finalResult, MAXIMUM_SCALE_SCIENTIFIC);
            }
        } else {
            return localFormat(finalResult, MINIMUM_FRACTION_DIGITS, MAXIMUM_FRACTION_DIGITS);
        }
    }

    private static String scientificFormat(BigDecimal x, int scale) {
        NumberFormat formatter = new DecimalFormat("0.0E0");
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        formatter.setMinimumFractionDigits(scale);
        return formatter.format(x);
    }

    private static String localFormat(BigDecimal x, int minimumFractionDigits, int maximumFractionDigits) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(minimumFractionDigits);
        nf.setMaximumFractionDigits(maximumFractionDigits);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(x);
    }

}
