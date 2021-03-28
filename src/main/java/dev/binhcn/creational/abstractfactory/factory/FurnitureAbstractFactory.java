package dev.binhcn.creational.abstractfactory.factory;

import dev.binhcn.creational.abstractfactory.chair.Chair;
import dev.binhcn.creational.abstractfactory.table.Table;

public abstract class FurnitureAbstractFactory {
 
    public abstract Chair createChair();
 
    public abstract Table createTable();
     
}