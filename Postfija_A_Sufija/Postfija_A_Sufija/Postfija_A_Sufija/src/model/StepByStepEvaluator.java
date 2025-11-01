package model;

import java.util.List;

public interface StepByStepEvaluator {
    /**
     * Evalúa una expresión postfija paso a paso y retorna los pasos del proceso
     */
    List<String> evaluateStepByStep(String postfixExpression);
}