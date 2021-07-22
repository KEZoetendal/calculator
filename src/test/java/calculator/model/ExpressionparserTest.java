package calculator.model;

import calculator.model.interfaces.CalculationElement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionparserTest {

    @Test
    void parseTest() {
        Expressionparser expressionparser = new Expressionparser();
        ArrayList<CalculationElement> elementList = new ArrayList<>(expressionparser.parse("(4+5)-8*_9"));
        CalculationElement firstElement = elementList.get(0);
        assertEquals(firstElement.toString(), "(");
        CalculationElement secondLastElement = elementList.get(elementList.size() - 2);
        assertEquals(secondLastElement.toString(), "*");
        CalculationElement lastElement = elementList.get(elementList.size() - 1);
        assertEquals(lastElement.toString(),  "-9");
    }

    @Test
    void validateTest() {
        Expressionparser expressionparser = new Expressionparser();

        // Als haakjes openen en haakjes sluiten niet in balans zijn
        Boolean result = expressionparser.validate("(8+3-(9*7)");
        assertEquals(result, false);

        // Expressie mag niet beginnen met operator, decimaalteken, of haakje sluiten
        Boolean result1 = expressionparser.validate("+8-9");
        assertEquals(result1, false);

        // Expressie mag niks anders bevatten dan operators, operands, decimaaltekens of haakjes
        Boolean result2 = expressionparser.validate("8/4$#");
        assertEquals(result2, false);

        Boolean result2a = expressionparser.validate("(8  *3");
        assertEquals(result2a, false);

        // Expressie mag geen letters bevatten
        Boolean result3 = expressionparser.validate("7+a-1");
        assertEquals(result3, false);

        // Geen 2 operators na elkaar
        Boolean result4 = expressionparser.validate("8.5+/4");
        assertEquals(result4, false);

        // Alleen operand na decimaalteken
        Boolean result5 = expressionparser.validate("8.5.+4");
        assertEquals(result5, false);

        // Alleen operand na negatiefteken
        Boolean result6 = expressionparser.validate("_/8.5/+4");
        assertEquals(result6, false);

        // Na vorige decimaalteken moet minstens 1 operator komen
        Boolean result7 = expressionparser.validate("8.5.96+4");
        assertEquals(result7, false);

        // Geen operator, haakje sluiten of decimaalteken na haakje openen
        Boolean result8 = expressionparser.validate("(*8.5+4");
        assertEquals(result8, false);

        // Alleen operator of haakje sluiten na haakje sluiten
        Boolean result9 = expressionparser.validate("(8.5+4)9");
        assertEquals(result9, false);

        // Geen haakje sluiten of decimaalteken na operator
        Boolean result10 = expressionparser.validate("8+)");
        assertEquals(result10, false);

        // Geen haakje openen na operand
        Boolean result11 = expressionparser.validate("8(");
        assertEquals(result11, false);

        // Niet delen door 0
        Boolean result12 = expressionparser.validate("(8/0)+1");
        assertEquals(result12, false);

        Boolean result12a = expressionparser.validate("8/0");
        assertEquals(result12a, false);

    }
}
