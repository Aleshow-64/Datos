package controller;

import model.Stack;
import java.util.List;

public class NotationConverter {
    private Stack stackModel;
    
    public NotationConverter(int stackSize) {
        this.stackModel = new Stack(stackSize);
    }
    
    public String convertToPostfix(String infixExpression) {
        Stack tempStack = new Stack(stackModel.getCapacity());
        StringBuilder output = new StringBuilder();
        
        // Process each character of the expression
        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);
            
            if (c == ' ') continue; // Ignore spaces
            
            // 1. If it's an operand, add to output
            if (isOperand(c)) {
                // Handle multi-digit operands
                StringBuilder operand = new StringBuilder();
                while (i < infixExpression.length() && 
                      (Character.isDigit(infixExpression.charAt(i)) || 
                       infixExpression.charAt(i) == '.' ||
                       Character.isLetter(infixExpression.charAt(i)))) {
                    operand.append(infixExpression.charAt(i));
                    i++;
                }
                i--; // Step back one position
                output.append(operand.toString()).append(" ");
            }
            // 2. If it's '(', push to stack
            else if (c == '(') {
                tempStack.push(String.valueOf(c));
            }
            // 3. If it's ')', pop until '('
            else if (c == ')') {
                while (!tempStack.isEmpty() && !tempStack.peek().equals("(")) {
                    output.append(tempStack.pop()).append(" ");
                }
                if (!tempStack.isEmpty() && tempStack.peek().equals("(")) {
                    tempStack.pop(); // Remove '('
                } else {
                    throw new RuntimeException("Unbalanced parentheses");
                }
            }
            // 4. If it's an operator
            else if (isOperator(c)) {
                while (!tempStack.isEmpty() && 
                       stackPrecedence(tempStack.peek().charAt(0)) >= infixPrecedence(c) && 
                       !tempStack.peek().equals("(")) {
                    output.append(tempStack.pop()).append(" ");
                }
                tempStack.push(String.valueOf(c));
            }
        }
        
        // Empty remaining stack
        while (!tempStack.isEmpty()) {
            String element = tempStack.pop();
            if (element.equals("(")) {
                throw new RuntimeException("Unbalanced parentheses");
            }
            output.append(element).append(" ");
        }
        
        return output.toString().trim();
    }
    
    /**
     * Delega la evaluación paso a paso al modelo
     */
    public List<String> evaluatePostfixStepByStep(String postfixExpression) {
        return stackModel.evaluateStepByStep(postfixExpression);
    }
    
    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
    
    private boolean isOperand(char c) {
        return Character.isLetterOrDigit(c) || c == '.';
    }
    
    public int infixPrecedence(char operator) {
        switch (operator) {
            case '^': return 4;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 5;
            default: return 0;
        }
    }
    
    public int stackPrecedence(char operator) {
        switch (operator) {
            case '^': return 3;
            case '*': case '/': return 2;
            case '+': case '-': return 1;
            case '(': return 0;
            default: return 0;
        }
    }
    
    // CHALLENGE: Conversion to prefix
    public String convertToPrefix(String infixExpression) {
        // Invert expression
        String invertedExpression = invertExpression(infixExpression);
        
        // Convert inverted expression to postfix
        String invertedPostfix = convertToPostfix(invertedExpression);
        
        // Invert the result
        return invertExpression(invertedPostfix);
    }
    
    private String invertExpression(String expression) {
        StringBuilder inverted = new StringBuilder();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if (c == '(') {
                inverted.append(')');
            } else if (c == ')') {
                inverted.append('(');
            } else {
                inverted.append(c);
            }
        }
        return inverted.toString();
    }
    
    /**
     * Get stack capacity for UI configuration
     */
    public int getStackCapacity() {
        return stackModel.getCapacity();
    }
}