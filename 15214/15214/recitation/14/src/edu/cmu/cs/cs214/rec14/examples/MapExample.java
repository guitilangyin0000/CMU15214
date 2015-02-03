package edu.cmu.cs.cs214.rec14.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapExample {
	public static void main(String[] args) {
        List<Integer> originalList = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Original List: " + originalList);
        
        /*
         * Suppose we want to create a new list where each elements is twice
         * the value of the original list. The traditional way of doing this 
         * is to create a new list and loop through the original list to add
         * elements to the new list. 
         */
        List<Integer> newList = new ArrayList<>();
        for (Integer i : originalList) {
        	newList.add(i * 2);
        }
        System.out.println("Double list without mapping: " + newList);
        System.out.println();
        
        /* 
         * The map() function takes each element of a stream and applies the function supplied to it,
         * creating a new stream of the results.
         */
        List<Integer> mappedList = originalList.stream().map(x -> 2 * x).collect(Collectors.toList());
        System.out.println("Double list with mapping: " + mappedList);
        System.out.println();

        /* 
         * 
         * The function supplied to to map() will create a stream whose type is the type of the return
         * value of the map function: 
         * in this example, the function takes an integer and returns a string. 
         */
        List<String> mappedStringList = originalList.stream().map(x -> "Number " + x).collect(Collectors.toList());
        System.out.println("Mapped String list: " + mappedStringList);
    }
}
