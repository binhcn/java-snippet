package dev.binhcn.behavior.interpreter.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubtractExpression implements Expression {
 
    private String expression;
 
    @Override
    public int interpret(InterpreterEngineContext context) {
        return context.subtract(expression);
    }
}