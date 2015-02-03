package edu.cmu.cs.cs214.rec14.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Collector.Characteristics;
import java.util.StringJoiner;

public class CollectExample {
	
	private static class Person {
		final String name;
		final int age;
		
		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		@Override
		public String toString() {
			return name + " - " + age;
		}
	}
	
	public static void main(String[] args) {
		/* A list of 5 people */
		List<Person> people = new ArrayList<Person>();
		people.add(new Person("John", 20));
		people.add(new Person("Mike", 30));
		people.add(new Person("Adam", 40));
		people.add(new Person("Chris", 50));
		people.add(new Person("William", 60));
		System.out.println("Original list: " + people + '\n');
		
		/*
		 * The collect function takes a collector and returns a result given by the collector.
		 * Many common collectors can be produced by factory methods defined in Collectors.
		 */
		
		/* Transforming the people list to a people set */
		Set<Person> peopleSet = people.stream().collect(Collectors.toSet());
		System.out.println("Set of people: " + peopleSet + '\n');
		
		/* Creating a map from name to age */
		Map<String, Integer> nameToAge = people.stream().collect(Collectors.toMap(p -> p.name, p -> p.age));
		System.out.println("Map from name to age: " + nameToAge + '\n');
		
		/* 
		 * You can also perform more complex manipulations
		 * e.g. create a message that includes names of people who are older than 40
		 */
		String message = people.stream()
							 .filter(person -> person.age >= 40)
							 .map(person -> person.name)
							 .collect(Collectors.joining(" and ", "Collector result: ", " are older than 40\n"));
		System.out.println(message);
		
		/*
		 * If the predefined collectors are not suitable for the task, you can also create your own collector
		 * through Collector.of, this method takes in a supplier, a consumer, a combiner, and a finisher.
		 */
		String msg = people.stream().collect(
			Collector.of(
				() -> new StringJoiner(" and "), // supplier
				(joiner, person) -> {if (person.age >= 40) joiner.add(person.name);}, // consumer
				(joiner1, joiner2) -> joiner1.merge(joiner2), // combiner
				joiner -> "Collector result (from customized collector): " + joiner.toString() + " are older than 40\n" // finisher
			)
		);
		System.out.println(msg);
		
		/*
		 * The finisher of a collector can be omitted if its type parameters are the same.
		 * Note that you can also add explicit type references (Type0 arg0, Type1 arg1,...) to arguments of a lambda function
		 */
		Map<String, Integer> m1 = people.stream().collect(
			Collector.of(
				() -> new HashMap<String, Integer>(), 
				(Map<String, Integer> map, Person person) -> {if (person.age >= 40) map.put(person.name, person.age);}, 
				(Map<String, Integer> map1, Map<String, Integer> map2) -> {map1.putAll(map2); return map1;},
				Characteristics.IDENTITY_FINISH
			)
		);
		System.out.println("Map (m1) from name to age: " + m1 + '\n');
		
		/* Another way of writing the above collector */
		Map<String, Integer> m2 = people.stream().collect(
			Collector.of(
				() -> new HashSet<Person>(),
				(Set<Person> set, Person person) -> {if (person.age >= 40) set.add(person);},
				(Set<Person> set1, Set<Person> set2) -> {set1.addAll(set2); return set1;},
				(Set<Person> set) -> {
					/* this finisher converts the intermediate Set<Person> to map */
					Map<String, Integer> result = new HashMap<String, Integer>();
					set.forEach(person -> result.put(person.name, person.age));
					return result;
				}
			)
		);
		System.out.println("Map (m2) from name to age: " + m2 + '\n');
	}
}
