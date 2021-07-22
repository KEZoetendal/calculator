package calculator.model;

import calculator.model.interfaces.CalculationElement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InfixToPostfixConverterTest {

    @Test
    void convertFromInfixToPostFix() {
        Expressionparser expressionparser = new Expressionparser();
        ArrayList<CalculationElement> elementList = new ArrayList<>(expressionparser.parse("(4+5*9)-8*_9"));

        InfixToPostfixConverter infixToPostfixConverter = new InfixToPostfixConverter();

        ArrayList<CalculationElement> postFix = new ArrayList<>(infixToPostfixConverter.convertFromInfixToPostFix(elementList));

        // result: [4, 5, 9, *, +, 8, -9, *, -]

        CalculationElement firstElement = postFix.get(0);
        assertEquals(firstElement.toString(), "4");
        CalculationElement secondLastElement = postFix.get(postFix.size() - 2);
        assertEquals(secondLastElement.toString(), "*");
        CalculationElement thirdLastElement = postFix.get(postFix.size() -3);
        assertEquals(thirdLastElement.toString(), "-9");
    }
}