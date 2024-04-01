package com.genpact.onlineShoppingApp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Examples {
	
	public void streamExample() {
		List<Integer> li = Arrays.asList(1,3,5,6,2,4,8,7,7,7);
		
		List<Integer> newStreamList = li.stream()
				.map(x -> x*x)
				.filter(x -> x<30)
				.sorted((o1, o2) -> (o1>o2)?-1:((o2>o1)?+1:0))
				.collect(Collectors.toList());
		
		Integer newStreamValue = li.stream()
				.filter(x -> x%2==0)
				.reduce(0, (value, x) -> value+x);
		
		System.out.println("Sum of all even number of:" + li + " is: " + newStreamValue);
		System.out.println(newStreamList);
		newStreamList.forEach(x -> System.out.println(x));
		
//		----------------------------------------------------------------
		
//		List<Integer> li = Arrays.asList(13,12,13,24,24,33,15,26,15);
//		
//		li.stream()
//				.sorted((o1, o2) -> (o1>o2)? 1:(o2>o1)? -1:0)
//				.distinct()
//				.forEach(x -> System.out.println(x));
//		
//		List<String> colour = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
//		
//		colour.stream()
//				.sorted((o1, o2) -> o1.compareTo(o2))
//				.forEach(x -> System.out.println(x));
//		
//		colour.stream()
//				.sorted((o1, o2) -> o2.compareTo(o1))
//				.forEach(x -> System.out.println(x));
		
	}
}
