package Simple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);

        JTextField textField = new JTextField();
        textField.setBounds(10, 10, 300, 40);
        textField.setBackground(Color.WHITE);
        frame.add(textField);

        String[] buttonLabels = {"AC", "mod", "!", "√", "x²", "x³", "%", "/", "7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "=","DEL"};
        JButton[] buttons = new JButton[buttonLabels.length];
        int numRows = 6;
        int numCols = (buttonLabels.length + numRows - 1) / numRows;
        int width = 70, height = 40;
        int x = 10, y = 60;
        int currentRow = 0;
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBounds(x, y, width, height);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 14));
            if (buttonLabels[i].equals("=")) {
                buttons[i].setBackground(new Color(0, 100, 0));
            } else if (buttonLabels[i].equals("AC") || buttonLabels[i].equals("DEL")) {
                buttons[i].setBackground(Color.RED);
            } else if (buttonLabels[i].matches("[0-9]")) {
                buttons[i].setBackground(Color.gray);
            } else {
                buttons[i].setBackground(Color.BLUE);
            }
            buttons[i].setForeground(Color.WHITE);
            buttons[i].addActionListener(new ButtonClickListener(textField, buttons[i]));
            frame.add(buttons[i]);
            if ((i + 1) % numCols == 0) {
                x = 10;
                y += height + 5;
                currentRow++;
            } else {
                x += width + 5;
            }
        }

        frame.setLayout(null);
        frame.setVisible(true);
    }
}

class ButtonClickListener implements ActionListener {
    private final JTextField textField;
    private final JButton button;

    public ButtonClickListener(JTextField textField, JButton button) {
        this.textField = textField;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = button.getText();
        if (command.equals("=")) {
            String expression = textField.getText();
            try {
                double result = evaluateExpression(expression);
                textField.setText(Double.toString(result));
            } catch (Exception ex) {
                textField.setText("Error");
            }
        } else if (command.equals("AC")) {
            textField.setText("");
        } else if (command.equals("DEL")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                textField.setText(text.substring(0, text.length() - 1));
            }
        } else if (command.equals("√")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                double number = Double.parseDouble(text);
                textField.setText(Double.toString(Math.sqrt(number)));
            }
        } else if (command.equals("x²")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                double number = Double.parseDouble(text);
                textField.setText(Double.toString(number * number));
            }
        } else if (command.equals("x³")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                double number = Double.parseDouble(text);
                textField.setText(Double.toString(number * number * number));
            }
        } else if (command.equals("%")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                double number = Double.parseDouble(text);
                textField.setText(Double.toString(number / 100));
            }
        } else if (command.equals("mod")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                double number = Double.parseDouble(text);
                textField.setText(Double.toString(number % 2));
            }
        } else if (command.equals("!")) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                int number = Integer.parseInt(text);
                textField.setText(Integer.toString(factorial(number)));
            }
        } else {
            textField.setText(textField.getText() + command);
        }
    }

    private double evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s", "");

        
        String[] parts = expression.split("(?<=[-+*/])|(?=[-+*/])");
        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[2]);
        char operator = parts[1].charAt(0);

       
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    private int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
