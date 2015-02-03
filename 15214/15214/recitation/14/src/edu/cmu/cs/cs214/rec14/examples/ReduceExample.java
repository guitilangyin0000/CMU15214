package edu.cmu.cs.cs214.rec14.examples;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReduceExample {
	
	public static void main(String[] args) {
		/* A list of integers from 1 to 100 */
		List<Integer> range = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
		
		/*
		 * Traditional way of summation - sequential loop
		 */
		int sumNonLambda = 0;
		for (Integer i : range) {
			sumNonLambda += i;
		}
		System.out.println("Sum from 1 to 100 (without using lambda) is: " + sumNonLambda);
		
		/*
		 * Summation with reduction:
		 * The reduce function takes an identity and an associative binary operator,
		 * in this example, the identity is 0 and the operator is "+" 
		 * (alternatively, you can provide a simple lambda function for addition, i.e. (a, b) -> a + b.
		 * The function will go through all elements in the stream and perform the 
		 * binary operator on them until only one value (the result) is left.
		 */
		int sumLambda = range.stream().reduce(0, Integer::sum);
		System.out.println("Sum from 1 to 100 (using lambda) is: " + sumLambda);
	}
}
