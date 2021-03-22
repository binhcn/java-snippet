package dev.binhcn.behavior.visitor;

public interface Book {
    void accept(Visitor v);
}