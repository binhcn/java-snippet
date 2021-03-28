package dev.binhcn.creational.abstractfactory.factory.impl;

import dev.binhcn.creational.abstractfactory.chair.Chair;
import dev.binhcn.creational.abstractfactory.chair.WoodChair;
import dev.binhcn.creational.abstractfactory.factory.FurnitureAbstractFactory;
import dev.binhcn.creational.abstractfactory.table.Table;
import dev.binhcn.creational.abstractfactory.table.WoodTable;

public class WoodFactory extends FurnitureAbstractFactory {
 
    @Override
    public Chair createChair() {
        return new WoodChair();
    }
 
    @Override
    public Table createTable() {
        return new WoodTable();
    }
}