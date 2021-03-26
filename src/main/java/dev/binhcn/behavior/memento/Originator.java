package dev.binhcn.behavior.memento;

import lombok.Data;

@Data
public class Originator {
 
    private double x;
    private double y;
 
    public Originator(double x, double y) {
        this.x = x;
        this.y = y;
    }
 
    public Memento save() {
        return new Memento(this.x, this.y);
    }
 
    public void undo(Memento mem) {
        this.x = mem.getX();
        this.y = mem.getY();
    }
}