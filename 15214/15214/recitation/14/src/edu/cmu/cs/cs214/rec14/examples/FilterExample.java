package edu.cmu.cs.cs214.rec14.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterExample {
    public static void main(String[] args) {
        List<Integer> originalList = Arrays.asList(-2, -1, 0, 1, 2, 3, 4, 5);
        System.out.println("Original list: " + originalList);
        
        /*
         * Suppose we want to create a new list containing only the even numbers in the original list.
         * The traditional way is to create a new list and populate the new list with
         * even numbers from the original list.
         */
        List<Integer> newList = new ArrayList<Integer>();
        for (Integer i : originalList) {
        	if (i % 2 == 0) {
        		newList.add(i);
        	}
        }
        System.out.println("Result list (without using filter): " + newList);

        /*
         * Alternatively, you can use streams, and specifically the filter() method.
         *
         * The filter() method takes a special function (called a predicate), which takes in a
         * single value and returns a boolean. The function is used to determines if the
         * element of the stream should also be an element of the output stream. The following code
         * removes all odd numbers from the stream for "values" by providing a function which only
         * returns true for even numbers.
         */
        List<Integer> filteredList = originalList.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println("Result list (using filter): " + filteredList);
    }
}
