package calculator.controller;

import calculator.model.Calculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Karin Zoetendal
 * Controllerklasse om de requests vanuit de calculator af te handelen.
 */

@Controller
public class CalculatorController {

    @GetMapping("/")
    public String getCalculator() {
        return "calculator";
    }

    @PostMapping("/")
    public String executeCalculation(@RequestParam("infixExpression") String infix, Model model) {
        Calculator calculation = new Calculator();
        String result = calculation.calculate(infix);
        model.addAttribute("result", result);
        return "calculator";
    }
}
