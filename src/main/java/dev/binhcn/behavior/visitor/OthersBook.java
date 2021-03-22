package dev.binhcn.behavior.visitor;

public class OthersBook implements ProgramingBook {
 
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
 
    @Override
    public String getResource() {
        return "Undefined";
    }
}