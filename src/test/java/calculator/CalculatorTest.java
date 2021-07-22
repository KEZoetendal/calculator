package calculator;

import calculator.model.Calculator;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void calculateTest() {
        String infixExpression = "4.2+6+9*8-(5*6+9)^2";

        Calculator calculator = new Calculator();
        String result = calculator.calculate(infixExpression);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(24);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        // Resulteert in -1.438,8 of -1,438.8, afhankelijk van locale duizend- en decimaalseparator
        String expected = nf.format(-1438.8);
        assertEquals(expected, result);
    }

    @Test
    void calculateTest1() {
        String infixExpression = "1/3";

        Calculator calculator = new Calculator();
        String result = calculator.calculate(infixExpression);

        // Resulteert in 0, of 0. met 24x 3 erachter, afhankelijk van locale duizend- en decimaalseparator
        String expected = "0,333333333333333333333333";
        assertEquals(expected, result);
    }

    @Test
    void calculateTest2() {
        String infixExpression = "8/0";

        Calculator calculator = new Calculator();
        String result = calculator.calculate(infixExpression);

        String expected = "Error";
        assertEquals(expected, result);
    }

}
