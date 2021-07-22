package calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author Karin Zoetendal
 * Klasse om de applicatie te initialiseren.
 * Ik heb gekozen om infix string om te zetten naar postfix, omdat een postfix expressie sneller geëvalueerd kan worden
 * en haakjes niet nodig zijn in een postfix.
 */

@ServletComponentScan
@SpringBootApplication
public class CalculatorApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CalculatorApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

}