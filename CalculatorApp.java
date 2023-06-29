import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorApp implements ActionListener {
    private JFrame frame;
    private JTextField entryField;

    public static void main(String[] args) {
        CalculatorApp calculator = new CalculatorApp();
        calculator.frame.setVisible(true);
    }

    public CalculatorApp() {
        frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        entryField = new JTextField();
        entryField.setEditable(false);
        frame.add(entryField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "Clear", "=", "+",
                "√", "^", "sin", "cos",
                "log", "!", "tan"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "Clear":
                    entryField.setText("");
                    break;
                case "=":
                    calculate();
                    break;
                case "√":
                    calculateSquareRoot();
                    break;
                case "^":
                    clickOperator("^");
                    break;
                case "sin":
                    calculateSin();
                    break;
                case "cos":
                    calculateCos();
                    break;
                case "log":
                    calculateLog();
                    break;
                case "!":
                    calculateFactorial();
                    break;
                case "tan":
                    calculateTan();
                    break;
                default:
                    clickNumber(command);
                    break;
            }
        } catch (Exception ex) {
            entryField.setText("Error");
        }
    }

    private void clickNumber(String number) {
        String current = entryField.getText();
        entryField.setText(current + number);
    }

    private void clickOperator(String operator) {
        entryField.setText(entryField.getText() + operator);
    }

    private void calculate() {
        String expression = entryField.getText();
        try {
            BigDecimal result = evaluateExpression(expression);
            if (result != null) {
                entryField.setText(result.toString());
            } else {
                entryField.setText("Error");
            }
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private BigDecimal evaluateExpression(String expression) {
        try {
            return new BigDecimal(expression);
        } catch (NumberFormatException e) {
            // Not a simple number, try evaluating more complex expressions
            try {
                if (expression.contains("^")) {
                    String[] parts = expression.split("\\^");
                    BigDecimal base = evaluateExpression(parts[0]);
                    BigDecimal exponent = evaluateExpression(parts[1]);
                    return base.pow(exponent.intValue());
                } else if (expression.contains("+")) {
                    String[] parts = expression.split("\\+");
                    BigDecimal operand1 = evaluateExpression(parts[0]);
                    BigDecimal operand2 = evaluateExpression(parts[1]);
                    return operand1.add(operand2);
                } else if (expression.contains("-")) {
                    String[] parts = expression.split("-");
                    BigDecimal operand1 = evaluateExpression(parts[0]);
                    BigDecimal operand2 = evaluateExpression(parts[1]);
                    return operand1.subtract(operand2);
                } else if (expression.contains("*")) {
                    String[] parts = expression.split("\\*");
                    BigDecimal factor1 = evaluateExpression(parts[0]);
                    BigDecimal factor2 = evaluateExpression(parts[1]);
                    return factor1.multiply(factor2);
                } else if (expression.contains("/")) {
                    String[] parts = expression.split("/");
                    BigDecimal dividend = evaluateExpression(parts[0]);
                    BigDecimal divisor = evaluateExpression(parts[1]);
                    return dividend.divide(divisor, MathContext.DECIMAL128);
                } else if (expression.startsWith("√")) {
                    BigDecimal number = evaluateExpression(expression.substring(1));
                    return BigDecimal.valueOf(Math.sqrt(number.doubleValue()));
                } else if (expression.startsWith("sin")) {
                    BigDecimal angle = evaluateExpression(expression.substring(3));
                    double radians = Math.toRadians(angle.doubleValue());
                    return BigDecimal.valueOf(Math.sin(radians));
                } else if (expression.startsWith("cos")) {
                    BigDecimal angle = evaluateExpression(expression.substring(3));
                    double radians = Math.toRadians(angle.doubleValue());
                    return BigDecimal.valueOf(Math.cos(radians));
                } else if (expression.startsWith("log")) {
                    BigDecimal number = evaluateExpression(expression.substring(3));
                    return BigDecimal.valueOf(Math.log10(number.doubleValue()));
                } else if (expression.endsWith("!")) {
                    int number = evaluateExpression(expression.substring(0, expression.length() - 1)).intValue();
                    return factorial(number);
                } else if (expression.startsWith("tan")) {
                    BigDecimal angle = evaluateExpression(expression.substring(3));
                    double radians = Math.toRadians(angle.doubleValue());
                    return BigDecimal.valueOf(Math.tan(radians));
                } else {
                    return null; // Unable to evaluate expression
                }
            } catch (Exception exception) {
                return null; // Unable to evaluate expression
            }
        }
    }

    private BigDecimal factorial(int number) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }

    private void calculateSquareRoot() {
        try {
            BigDecimal number = evaluateExpression(entryField.getText());
            BigDecimal result = BigDecimal.valueOf(Math.sqrt(number.doubleValue()));
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private void calculateSin() {
        try {
            BigDecimal angle = evaluateExpression(entryField.getText());
            double radians = Math.toRadians(angle.doubleValue());
            BigDecimal result = BigDecimal.valueOf(Math.sin(radians));
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private void calculateCos() {
        try {
            BigDecimal angle = evaluateExpression(entryField.getText());
            double radians = Math.toRadians(angle.doubleValue());
            BigDecimal result = BigDecimal.valueOf(Math.cos(radians));
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private void calculateLog() {
        try {
            BigDecimal number = evaluateExpression(entryField.getText());
            BigDecimal result = BigDecimal.valueOf(Math.log10(number.doubleValue()));
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private void calculateFactorial() {
        try {
            int number = evaluateExpression(entryField.getText()).intValue();
            BigDecimal result = factorial(number);
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }

    private void calculateTan() {
        try {
            BigDecimal angle = evaluateExpression(entryField.getText());
            double radians = Math.toRadians(angle.doubleValue());
            BigDecimal result = BigDecimal.valueOf(Math.tan(radians));
            entryField.setText(result.toString());
        } catch (Exception e) {
            entryField.setText("Error");
        }
    }


}
