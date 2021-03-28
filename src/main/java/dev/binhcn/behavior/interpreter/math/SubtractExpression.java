package dev.binhcn.behavior.interpreter.math;

public class SubtractExpression implements Expression {
 
    private String expression;
 
    public SubtractExpression(String expression) {
        this.expression = expression;
    }
 
    @Override
    public int interpret(InterpreterEngineContext context) {
        return context.subtract(expression);
    }
}