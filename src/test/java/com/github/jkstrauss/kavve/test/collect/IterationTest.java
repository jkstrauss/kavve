package com.github.jkstrauss.kavve.test.collect;

import java.util.ArrayList;
import java.util.List;

import com.github.jkstrauss.kavve.collect.Iteration;

public class IterationTest {
  public static void main(String[] args) {
    
    String[] array = new String[] {"Dina", "Yoheved", "Samuel", "Dog", "Joseph", "Aaron", "Jonah"};
    List<String> people = new ArrayList<>();
    
    for(String person: array) {
      people.add(person);
    }
    
    System.out.println(people);
    for(Iteration<String> iteration: Iteration.iterations(people)) {
      if(iteration.value.equals("Dog")) {
        iteration.remove();
      }
      else {
        System.out.printf("%s: %s\n", iteration.index + 1, iteration.value);
        if(!iteration.hasNext()){
          System.out.println("Last");
        }
      }
    }
    System.out.println(people);
  }
}
