package edu.cmu.cs.cs214.hw1.staff;

/**
 * This class models a vertex of the graph. DO NOT MODIFY THIS FILE.
 */
public class Vertex {

	private String label;

	public Vertex(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
		if (!(obj instanceof Vertex)) {
			return false;
		}
		Vertex other = (Vertex) obj;
		return label.equals(other.label);
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

	@Override
	public String toString() {
		return label;
	}

}
