package edu.cmu.cs.cs214.hw0;

public class Main {
	public static void main(String[] args) {
		// Un-comment the following code (CTRL + /).

		FriendGraph graph = new FriendGraph();
		
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        
        
		graph.addPerson(rachel);
		graph.addPerson(ross);
		graph.addPerson(ben);
		graph.addPerson(kramer);
		
		graph.addFriendship(rachel, ross);
		graph.addFriendship(ross, ben);
		
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, kramer));
		
		FriendGraph circle = new FriendGraph();
		
		Person a = new Person("a");
        Person b = new Person("b");
        Person c = new Person("c");
        Person d = new Person("d");
        Person e = new Person("e");
        Person f = new Person("f");
        Person g = new Person("g");
        Person h = new Person("h");
        
        circle.addPerson(a);
		circle.addPerson(b);
		circle.addPerson(c);
		circle.addPerson(d);
		circle.addPerson(e);
		circle.addPerson(f);
		circle.addPerson(g);
		circle.addPerson(h);
		
		circle.addFriendship(a, b);
		circle.addFriendship(a, c);
		circle.addFriendship(a, d);
		circle.addFriendship(b, c);
		circle.addFriendship(e, c);
		circle.addFriendship(g, c);
		circle.addFriendship(d, e);
		circle.addFriendship(e, f);
		circle.addFriendship(g, f);

		
		System.out.println(circle.getDistance(a, g));
		System.out.println(circle.getDistance(a, f));
     	System.out.println(circle.getDistance(a, b));
    	System.out.println(circle.getDistance(a, d));
		System.out.println(circle.getDistance(a, e));
		System.out.println(circle.getDistance(a, h));
		
		// You may write more tests if you'd like. 
	}
}
