package org.enricogiurin.ocp17.book.ch8.functionalinterface.primitive;

import java.util.function.ObjDoubleConsumer;

public class UseOfObjDoubleConsumer {

  public static void main(String[] args) {
    new UseOfObjDoubleConsumer().use();
  }

  void use() {
    ObjDoubleConsumer<String> fi = (d, s) -> System.out.println("double: " + d + " obj: " + s);
    fi.accept("hello", 4.5D); //hello obj: 4.5
  }

}
