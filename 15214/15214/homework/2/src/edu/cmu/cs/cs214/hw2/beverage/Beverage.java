package edu.cmu.cs.cs214.hw2.beverage;

public abstract class Beverage {

	protected String description;
	
	public Beverage(String description){
		this.description = description;
	}
    /**
     * Calculates and returns the cost of the {@link Beverage}.
     *
     * @return The cost of this {@link Beverage} in cents.
     */
    abstract public int getCost();
    
    public String getDescription(){
    	return description;
    }
    
    abstract public void prepare();
    
    abstract public void setSizeFactor(SizeFactor sizefactor);
    
    abstract public void setRecipe(Recipe recipe);
    

}
