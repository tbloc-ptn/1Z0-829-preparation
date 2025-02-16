package org.enricogiurin.ocp17.book.ch10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsGroupingBy {

  public static void main(String[] args) {
    new CollectorsGroupingBy().groupingByRecord();
  }


  //3 args
  void groupingByRecord() {
    record Car(String name, int year) {

      @Override
      public String toString() {
        return name;
      }
    }
    var cars = new ArrayList<Car>();
    cars.add(new Car("Mustang", 1967));
    cars.add(new Car("Escort", 1975));
    cars.add(new Car("Thunderbird", 1967));

    //{1967=[Mustang, Thunderbird], 1975=[Escort]}
    TreeMap<Integer, List<Car>> map = cars.stream()
        .collect(Collectors.groupingBy(Car::year, //function
            TreeMap::new,  //supplier
            Collectors.toList()));  //collector
    System.out.print(map);
  }

  //2 args
  void groupingBySet() {
    Stream<String> fruitStream = fruitStream();
    Map<Integer, Set<String>> map = fruitStream.collect(
        Collectors.groupingBy(String::length,
            Collectors.toSet()));
    //{4=[Lime, Kiwi, Pear], 5=[Apple, Grape, Peach, Mango, Lemon], 6=[Orange, Banana]}
    //as you can see, Banana is present only once!
    System.out.println(map);
  }

  //one-arg
  void groupingByBoolean() {
    Stream<String> fruitStream = fruitStream();
    Map<Boolean, List<String>> map = fruitStream.collect(
        Collectors.groupingBy(s -> s.startsWith("K")));
    //{false=[Orange, Apple, Banana, Grape, Pear, Peach, Mango, Lemon, Lime, Banana], true=[Kiwi]}
    System.out.println(map);
  }

  //one-arg
  void groupingBy() {
    Stream<String> fruitStream = fruitStream();
    Map<Integer, List<String>> map = fruitStream.collect(Collectors.groupingBy(String::length));
    //{4=[Kiwi, Pear, Lime], 5=[Apple, Grape, Peach, Mango, Lemon], 6=[Orange, Banana, Banana]}
    //as you can see, Banana is present twice
    System.out.println(map);
  }

  //note that Banana is present twice
  private Stream<String> fruitStream() {
    return Stream.of("Orange", "Apple", "Banana", "Grape", "Kiwi",
        "Pear", "Peach", "Mango", "Lemon", "Lime", "Banana");
  }

}
