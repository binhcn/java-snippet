package dev.binhcn.behavior.iterator;

public interface ItemIterator<T> {
     
    boolean hasNext();
     
    T next();
}