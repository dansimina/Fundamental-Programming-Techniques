package view;

import logic.Controller;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JPanel contentPane;
    private JTextField firstPolynomialTextField;
    private JTextField secondPolynomialTextField;
    private JLabel firstPolynomialLabel;
    private JLabel secondPolynomialLabel;
    private JLabel resultLabel;
    private JLabel titleLabel;
    private JButton addButton;
    private JButton subtractButton;
    private JButton derivativeButton;
    private JButton integralButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JLabel resultedPolynomialLabel;
    private JLabel aditionalInfo;

    Controller controller;

    public View(String name) {
        super(name);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new Controller(this);

        contentPane.setBackground(Color.white);
        this.add(contentPane);
        this.setSize(350, 400);

        this.prepareLabels();
        this.prepareButtons();
    }

    private void prepareButtons() {
        addButton.setText("ADD");
        addButton.setBackground(Color.white);
        addButton.setActionCommand("add");
        addButton.addActionListener(controller);

        subtractButton.setText("SUBTRACT");
        subtractButton.setBackground(Color.white);
        subtractButton.setActionCommand("subtract");
        subtractButton.addActionListener(controller);

        multiplyButton.setText("MULTIPLY");
        multiplyButton.setBackground(Color.white);
        multiplyButton.setActionCommand("multiply");
        multiplyButton.addActionListener(controller);

        divideButton.setText("DIVIDE");
        divideButton.setBackground(Color.white);
        divideButton.setActionCommand("divide");
        divideButton.addActionListener(controller);

        derivativeButton.setText("DERIVATIVE");
        derivativeButton.setBackground(Color.white);
        derivativeButton.setActionCommand("derivative");
        derivativeButton.addActionListener(controller);

        integralButton.setText("INTEGRAL");
        integralButton.setBackground(Color.white);
        integralButton.setActionCommand("integral");
        integralButton.addActionListener(controller);
    }

    private void prepareLabels() {
        firstPolynomialLabel.setText("FIRST POLYNOMIAL");
        secondPolynomialLabel.setText("SECOND POLYNOMIAL");
        resultLabel.setText("RESULT");
        titleLabel.setText("CALCULATOR");
        resultedPolynomialLabel.setText(" ");
        aditionalInfo.setText(" ");
    }

    public String getFirstPolynomial() {
        return firstPolynomialTextField.getText();
    }

    public String getSecondPolynomial() {
        return secondPolynomialTextField.getText();
    }

    public void setResultedPolynomialLabel(String text) {
        resultedPolynomialLabel.setText(text);
    }

    public void setAditionalInfo(String text) {
        aditionalInfo.setText(text);
    }

    public void invalidInput() {
        JOptionPane.showMessageDialog(new JFrame(), "ERROR: Invalid input!");
    }
}
