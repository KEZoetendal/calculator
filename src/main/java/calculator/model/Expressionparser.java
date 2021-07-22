package calculator.model;

import calculator.model.interfaces.CalculationElement;
import calculator.model.operands.OperandElement;
import calculator.model.operators.OperatorElement;

import java.util.*;

/**
 * @author Karin Zoetendal
 * Deze klasse converteert de infix input string naar een lijst met CalculationElementen.
 * TODO: inputvalidatie (in het geval JS omzeild wordt).
 */

public class Expressionparser {

    public final char DECIMALSEPARATOR = '.';
    public final char INFIX_NEGATIVESIGN = '_';
    public final char POSTFIX_NEGATIVESIGN = '-';
    public final char OPEN_PARENTHESIS = '(';
    public final char CLOSE_PARENTHESIS = ')';
    public final Character[] OPERATORS = {'+', '-', '*', '/', '^'};

    List<Character> operators = new ArrayList<>(Arrays.asList(OPERATORS));

    ArrayList<CalculationElement> elementList = new ArrayList<>();

    public Collection<CalculationElement> parse(String infixExpression) throws InputMismatchException {

        if (!validate(infixExpression)) {

            throw new InputMismatchException();

        } else {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < infixExpression.length(); i++) {
                char c = infixExpression.charAt(i);

                // Als character negatiefteken is, voeg '-' toe aan string
                if (c == INFIX_NEGATIVESIGN) {
                    sb.append(POSTFIX_NEGATIVESIGN);

                    // Als character cijfer is of decimaalteken, voeg toe aan string
                } else if (Character.isDigit(c) || c == DECIMALSEPARATOR) {
                    sb.append(c);

                    // Als volgende character geen cijfer of decimaalteken is
                    if (i + 1 < infixExpression.length()) {
                        char d = infixExpression.charAt(i + 1);
                        if (!Character.isDigit(d) && d != DECIMALSEPARATOR && sb.length() > 0) {

                            elementList.add(new OperandElement(sb.toString()));

                            // Leegt stringbuilder
                            sb.delete(0, sb.length());
                        }
                    }
                }


                // Als character een operator is, voeg OperatorElement toe
                if (operators.contains(c) || c == OPEN_PARENTHESIS || c == CLOSE_PARENTHESIS) {
                    elementList.add(new OperatorElement(String.valueOf(c)));
                }
            }

            // Voeg opgebouwde string toe als operandelement
            if (sb.length() > 0) {
                elementList.add(new OperandElement(sb.toString()));
            }
            return elementList;
        }
    }

    public boolean validate(String infix) {

        long countOpen = infix.chars().filter(ch -> ch == OPEN_PARENTHESIS).count();
        long countClose = infix.chars().filter(ch -> ch == CLOSE_PARENTHESIS).count();
        boolean isSeparatorAllowed = true;

        // Als haakjes openen en haakjes sluiten niet in balans zijn
        if (countOpen != countClose) {
            return false;

        } else {
            for (int i = 0; i < infix.length(); i++) {
                char curr = infix.charAt(i);

                // Expressie mag niet beginnen met operator, decimaalteken, of haakje sluiten
                if (i == 0 && (curr == CLOSE_PARENTHESIS || curr == DECIMALSEPARATOR || operators.contains(curr))) {
                    return false;
                }

                // Expressie mag niks anders bevatten dan operators, operands, decimaaltekens of haakjes
                if (!operators.contains(curr) && !Character.isDigit(curr) && curr != DECIMALSEPARATOR &&
                        curr != OPEN_PARENTHESIS && curr != CLOSE_PARENTHESIS) {
                    return false;
                }

                // Expressie mag geen letters bevatten
                if (Character.isLetter(curr)) {
                    return false;
                }

                // Als huidige teken decimaalteken is, mag er pas weer een decimaalteken na een operator
                else if (curr == DECIMALSEPARATOR) {
                    isSeparatorAllowed = false;
                } else if (operators.contains(curr)) {
                    isSeparatorAllowed = true;

                }
                if (i + 1 < infix.length()) {
                    char next = infix.charAt(i + 1);

                    // Geen 2 operators na elkaar       of
                    if (operators.contains(curr) && operators.contains(next) ||

                            // Alleen operand na decimaalteken     of
                            curr == DECIMALSEPARATOR && !Character.isDigit(next) ||

                            // Alleen operand na negatiefteken     of
                            curr == INFIX_NEGATIVESIGN && !Character.isDigit(next) ||

                            // Na vorige decimaalteken moet minstens 1 operator komen       of
                            (curr == DECIMALSEPARATOR && !isSeparatorAllowed) ||

                            // Geen operator, haakje sluiten of decimaalteken na haakje openen      of
                            (curr == OPEN_PARENTHESIS && (operators.contains(next) || next == CLOSE_PARENTHESIS ||
                                    next == DECIMALSEPARATOR)) ||

                            // Alleen operator of haakje sluiten na haakje sluiten      of
                            (curr == CLOSE_PARENTHESIS && next != CLOSE_PARENTHESIS && operators.contains(next)) ||

                            // Geen haakje sluiten of decimaalteken na operator     of
                            (operators.contains(curr) && (next == CLOSE_PARENTHESIS || next == DECIMALSEPARATOR)) ||

                            // Geen haakje openen na operand
                            (Character.isDigit(curr) && next == OPEN_PARENTHESIS)) {
                        return false;
                    }
                }

                // Niet delen door 0
                if (i + 1 < infix.length() && i - 2 >= 0) {
                    char prev = infix.charAt(i - 1);
                    char pre_prev = infix.charAt(i - 2);
                    if ((prev == '0' && pre_prev == '/') && (operators.contains(curr) || curr == CLOSE_PARENTHESIS)) {
                        return false;
                    }
                }
                if (i == infix.length() - 1) {
                    char prev = infix.charAt(i - 1);
                    if (curr == '0' && prev == '/') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}