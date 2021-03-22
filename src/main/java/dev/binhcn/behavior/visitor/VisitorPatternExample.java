package dev.binhcn.behavior.visitor;

import java.lang.reflect.Method;

public class VisitorPatternExample {
 
    public static void main(String[] args) throws Exception {
        Book book1 = new BusinessBook();
        Book book2 = new JavaCoreBook();
        Book book3 = new DesignPatternBook();
        Book book4 = new OthersBook();

        Visitor v = new VisitorImpl();
        book1.accept(v);
        book2.accept(v);
        book3.accept(v);
        book4.accept(v);

    }
}