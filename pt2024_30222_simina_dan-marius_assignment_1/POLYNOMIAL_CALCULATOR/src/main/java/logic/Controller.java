package logic;

import model.Operations;
import model.Polynomial;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Controller implements ActionListener {
    private final Operations operations;
    private final View view;

    public Controller(View view) {
        this.operations = new Operations();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String operation = e.getActionCommand();
        Polynomial firstPolynomial = new Polynomial(view.getFirstPolynomial());
        Polynomial secondPolynomial = new Polynomial(view.getSecondPolynomial());
        Polynomial result = new Polynomial();
        Polynomial aditionalPolynomial = new Polynomial("0");

        if (!firstPolynomial.toString().equals(view.getFirstPolynomial().replace(" ", "")) || (!secondPolynomial.toString().equals(view.getSecondPolynomial().replace(" ", "")) && !operation.equals("derivative") && !operation.equals("integral"))) {
            view.setResultedPolynomialLabel(" ");
            view.setAditionalInfo(" ");
            view.invalidInput();
            return;
        }

        switch (operation) {
            case "add" -> result = operations.add(firstPolynomial, secondPolynomial);
            case "subtract" -> result = operations.subtract(firstPolynomial, secondPolynomial);
            case "multiply" -> result = operations.multiply(firstPolynomial, secondPolynomial);
            case "divide" -> {
                Map.Entry<Polynomial, Polynomial> divideResult = operations.divide(firstPolynomial, secondPolynomial);
                if (divideResult.getKey().getDegree() == -1 && divideResult.getValue().getDegree() == -1 && firstPolynomial.getDegree() != -1) {
                    view.invalidInput();
                    return;
                }
                result = divideResult.getKey();
                aditionalPolynomial = divideResult.getValue();
            }
            case "derivative" -> result = operations.derivative(firstPolynomial);
            case "integral" -> result = operations.integral(firstPolynomial);
        }

        view.setResultedPolynomialLabel(result.toString());
        view.setAditionalInfo(aditionalPolynomial.toString());
    }
}
