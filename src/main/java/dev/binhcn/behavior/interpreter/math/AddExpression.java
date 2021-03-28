package dev.binhcn.behavior.interpreter.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddExpression implements Expression {

    private String expression;

    @Override
    public int interpret(InterpreterEngineContext context) {
        return context.add(expression);
    }
}