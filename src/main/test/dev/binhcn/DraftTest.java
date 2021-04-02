package dev.binhcn;

import dev.binhcn.controller.Controller;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class DraftTest {

  @Test
  public void gen() {
    Class<?>[] definitions = {
        Controller.class
    };

    for (Class<?> klass : definitions) {
      inspect(klass);
      System.out.println("");
    }
  }

  <T> void inspect(Class<T> klass) {
    Field[] fields = klass.getDeclaredFields();
    System.out.println("  " + klass.getSimpleName() + ":");
    System.out.println("    type: \"object\"");
    System.out.println("    properties:");


    List<Field> fieldList = new ArrayList<>(Arrays.asList(fields));

    Class<?> parent = klass;
    while (!parent.getSuperclass().equals(Object.class)) {
      parent = parent.getSuperclass();
      Field[] parentField = parent.getDeclaredFields();
      fieldList.addAll(new ArrayList<>(Arrays.asList(parentField)));
    }

    for (Field field : fieldList) {
      String type = field.getType().getName();
      String name = field.getName();
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      System.out.println("      " + name + ":");
      System.out.println("        " + getType(type));
    }
  }

  private String getType(String name) {
    String typeFormat = "type: \"%s\"";
    String refFormat = "$ref: \"#/definitions/%s\"";
    switch (name) {
      case "int":
      case "long":
        return String.format(typeFormat, "integer");
      case "boolean":
        return String.format(typeFormat, "boolean");
      case "String":
        return String.format(typeFormat, "string");
      default:
        return String.format(refFormat, name);
    }
  }
}