package model;

import java.util.ArrayList;
import java.util.List;

public class Stack implements StepByStepEvaluator {
    private String[] array; 
    private int top; 
    private int capacity;
    
    public Stack(int size) { 
        capacity = size;
        array = new String[capacity];
        top = -1;
    }
    
    public void push(String element) { 
        if (isFull()) { 
            System.out.println("Error: Stack full");
            return;
        }
        top++;
        array[top] = element;
    }
    
    public String pop() { 
        if (isEmpty()) { 
            System.out.println("Error: Stack empty");
            return null;
        }
        String element = array[top]; 
        top--;
        return element;
    }
    
    public String peek() { 
        if (isEmpty()) {
            return null;
        }
        return array[top];
    }
    
    public boolean isEmpty() { 
        return top == -1;
    }
    
    public boolean isFull() { 
        return top == capacity - 1;
    }
    
    public void clearStack() { 
        top = -1;
    }
    
    public int size() { 
        return top + 1;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Implementación de la interfaz StepByStepEvaluator
     * Evalúa una expresión postfija paso a paso
     */
    @Override
    public List<String> evaluateStepByStep(String postfixExpression) {
        List<String> steps = new ArrayList<>();
        Stack tempStack = new Stack(this.capacity); // Usar stack temporal para evaluación
        
        steps.add("Initial expression: " + postfixExpression);
        steps.add("");
        
        String[] tokens = postfixExpression.split(" ");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isOperand(token)) {
                tempStack.push(token);
                steps.add("Push operand: " + token);
            } else if (isOperator(token)) {
                if (tempStack.size() < 2) {
                    throw new RuntimeException("Insufficient operands for operator: " + token);
                }
                
                double operand2 = Double.parseDouble(tempStack.pop());
                double operand1 = Double.parseDouble(tempStack.pop());
                double result = performOperation(operand1, token, operand2);
                
                tempStack.push(String.valueOf(result));
                steps.add("Operation: " + operand1 + " " + token + " " + operand2 + " = " + result);
                steps.add("Push result: " + result);
            }
            
            // Mostrar estado actual del stack
            steps.add("Stack state: " + getStackState(tempStack));
            steps.add("");
        }
        
        if (tempStack.size() == 1) {
            String finalResult = tempStack.pop();
            steps.add("Final result: " + finalResult);
        } else {
            throw new RuntimeException("Malformed expression");
        }
        
        return steps;
    }
    
    /**
     * Obtiene el estado actual del stack como string
     */
    private String getStackState(Stack stack) {
        StringBuilder state = new StringBuilder("[");
        
        // Crear array temporal para mostrar el estado
        String[] tempArray = new String[stack.size()];
        Stack temp = new Stack(stack.size());
        
        // Copiar elementos
        int index = 0;
        while (!stack.isEmpty()) {
            String element = stack.pop();
            tempArray[index++] = element;
            temp.push(element);
        }
        
        // Reconstruir stack original y crear string de estado
        for (int i = tempArray.length - 1; i >= 0; i--) {
            state.append(tempArray[i]);
            if (i > 0) state.append(" ");
            stack.push(tempArray[i]); // Restaurar stack original
        }
        
        state.append("]");
        return state.toString();
    }
    /**
     * Verifica si un token es un operando (número)
     */
    private boolean isOperand(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }
    /**
     * Verifica si un token es un operador
     */
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
               token.equals("*") || token.equals("/") ||
               token.equals("^");
    }
    /**
     * Realiza una operación matemática
     */
    private double performOperation(double a, String operator, double b) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": 
                if (b == 0) throw new RuntimeException("Division by zero");
                return a / b;
            case "^": return Math.pow(a, b);
            default: throw new RuntimeException("Unsupported operator: " + operator);
        }
    }
}