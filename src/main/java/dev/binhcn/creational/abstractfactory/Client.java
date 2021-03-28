package dev.binhcn.creational.abstractfactory;

import dev.binhcn.creational.abstractfactory.chair.Chair;
import dev.binhcn.creational.abstractfactory.factory.FurnitureAbstractFactory;
import dev.binhcn.creational.abstractfactory.factory.FurnitureFactory;
import dev.binhcn.creational.abstractfactory.factory.MaterialType;
import dev.binhcn.creational.abstractfactory.table.Table;

public class Client {
 
    public static void main(String[] args) {
 
        FurnitureAbstractFactory factory = FurnitureFactory.getFactory(MaterialType.FLASTIC);
 
        Chair chair = factory.createChair();
        chair.create(); // Create plastic chair
 
        Table table = factory.createTable();
        table.create(); // Create plastic table
    }
}