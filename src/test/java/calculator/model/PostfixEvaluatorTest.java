package calculator.model;

import calculator.model.interfaces.CalculationElement;
import calculator.model.operands.OperandElement;
import calculator.model.operators.OperatorElement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PostfixEvaluatorTest {

    @Test
    void evaluateTest() {

        PostfixEvaluator postfixEvaluator = new PostfixEvaluator();

        ArrayList<CalculationElement> testCollection = new ArrayList<>();

        OperandElement a = new OperandElement("488500000000000000000");
        OperandElement b = new OperandElement("5.589");
        OperandElement c = new OperandElement("-9");
        OperatorElement d = new OperatorElement("+");
        OperatorElement e = new OperatorElement("*");

        testCollection.add(a);
        testCollection.add(b);
        testCollection.add(c);
        testCollection.add(d);
        testCollection.add(e);

        String result = postfixEvaluator.evaluate(testCollection);

        assertEquals(result, "-1,66627350000000000000E21");
    }

    @Test
    void evaluateTest1() {

        PostfixEvaluator postfixEvaluator = new PostfixEvaluator();

        ArrayList<CalculationElement> testCollection = new ArrayList<>();

        OperandElement a = new OperandElement("4885");
        OperandElement b = new OperandElement("5.589");
        OperandElement c = new OperandElement("-9");
        OperatorElement d = new OperatorElement("+");
        OperatorElement e = new OperatorElement("*");

        testCollection.add(a);
        testCollection.add(b);
        testCollection.add(c);
        testCollection.add(d);
        testCollection.add(e);

        String result = postfixEvaluator.evaluate(testCollection);

        assertEquals(result, "-16.662,735");
    }
}