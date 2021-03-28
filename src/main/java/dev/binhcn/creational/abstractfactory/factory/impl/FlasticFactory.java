package dev.binhcn.creational.abstractfactory.factory.impl;

import dev.binhcn.creational.abstractfactory.chair.Chair;
import dev.binhcn.creational.abstractfactory.chair.PlasticChair;
import dev.binhcn.creational.abstractfactory.factory.FurnitureAbstractFactory;
import dev.binhcn.creational.abstractfactory.table.PlasticTable;
import dev.binhcn.creational.abstractfactory.table.Table;

public class FlasticFactory extends FurnitureAbstractFactory {
 
    @Override
    public Chair createChair() {
        return new PlasticChair();
    }
 
    @Override
    public Table createTable() {
        return new PlasticTable();
    }
 
}