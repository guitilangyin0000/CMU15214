package edu.cmu.cs.cs214.hw2;

import java.text.DecimalFormat;
import java.util.Scanner;

import edu.cmu.cs.cs214.hw2.beverage.Beverage;
import edu.cmu.cs.cs214.hw2.beverage.Coffee;
import edu.cmu.cs.cs214.hw2.beverage.CoffeeDrink;
import edu.cmu.cs.cs214.hw2.beverage.CoffeeSizeFactor;
import edu.cmu.cs.cs214.hw2.beverage.Decaf;
import edu.cmu.cs.cs214.hw2.beverage.Espresso;
import edu.cmu.cs.cs214.hw2.beverage.GreenTea;
import edu.cmu.cs.cs214.hw2.beverage.HouseBlend;
import edu.cmu.cs.cs214.hw2.beverage.MilkFreeCoffeeDrink;
import edu.cmu.cs.cs214.hw2.beverage.MilkFreeTeaDrink;
import edu.cmu.cs.cs214.hw2.beverage.RedTea;
import edu.cmu.cs.cs214.hw2.beverage.Tea;
import edu.cmu.cs.cs214.hw2.beverage.TeaDrink;
import edu.cmu.cs.cs214.hw2.beverage.TeaSizeFactor;
import edu.cmu.cs.cs214.hw2.beverage.WhiteTea;
import edu.cmu.cs.cs214.hw2.ingredients.Chocolate;
import edu.cmu.cs.cs214.hw2.ingredients.FlavoredBeverage;
import edu.cmu.cs.cs214.hw2.ingredients.Ginger;
import edu.cmu.cs.cs214.hw2.ingredients.Jasmine;
import edu.cmu.cs.cs214.hw2.ingredients.Milk;
import edu.cmu.cs.cs214.hw2.ingredients.WhipCream;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to the Coffee Shop!");
		System.out.println("Type \\h for help. Type \\q to quit.");

		Scanner scanner = new Scanner(System.in);
		DecimalFormat decimalFormatter = new DecimalFormat("#.00");

		while (true) {
			System.out.print("\n> ");

			String line = scanner.nextLine();
			if (line.equals("\\q")) {
				break;
			}
			if (line.equals("\\h")) {
				System.out
						.println("Usage: <beverage name> <size> [<ingredient> <ingredient> ...]");
				System.out.println("Example: greentea large milk ginger");
				System.out
						.println("Note: beverage names and ingredients must not contain spaces.");
				continue;
			}

			String[] words = line.split(" ");
			if (words.length < 2) {
				System.out.println("Invalid input.");
				continue;
			}

			// words[0] = beverage name
			String name = words[0].toLowerCase();

			// words[1] = size
			String size = words[1].toLowerCase();

			// words[2+] (optional) = ingredients
			int numIngredients = words.length - 2;
			String[] ingredients = new String[numIngredients];
			System.arraycopy(words, 2, ingredients, 0, numIngredients);

			Beverage beverage = makeBeverage(name, size, ingredients);
			if (beverage == null) {
				System.out.println("Invalid input.");
				continue;
			}

			int totalCostCents = beverage.getCost();
			String cost = decimalFormatter.format(totalCostCents / 100.0);
			System.out.printf("The total cost of your order is: $%s\n", cost);
			System.out.println("The beverage is prepared as follows: ");
			beverage.prepare();
		}
		scanner.close();
	}

	private static Beverage makeBeverage(String name, String size,
			String[] ingredients) {
		Beverage beverage = null;

		TeaSizeFactor teasize = new TeaSizeFactor(size);
		CoffeeSizeFactor coffeesize = new CoffeeSizeFactor(size);
		MilkFreeCoffeeDrink milkfreecoffeedrink = new MilkFreeCoffeeDrink();
		MilkFreeTeaDrink milkfreeteadrink = new MilkFreeTeaDrink();

		if (name.equals("espresso")) {
			beverage = new Espresso(name);
			beverage.setSizeFactor(coffeesize);
			beverage.setRecipe(milkfreecoffeedrink);
		} else if (name.equals("decaf")) {
			beverage = new Decaf(name);
			beverage.setSizeFactor(coffeesize);
			beverage.setRecipe(milkfreecoffeedrink);
		} else if (name.equals("houseblend")) {
			beverage = new HouseBlend(name);
			beverage.setSizeFactor(coffeesize);
			beverage.setRecipe(milkfreecoffeedrink);
		} else if (name.equals("greentea")) {
			beverage = new GreenTea(name);
			beverage.setSizeFactor(teasize);
			beverage.setRecipe(milkfreeteadrink);
		} else if (name.equals("redtea")) {
			beverage = new RedTea(name);
			beverage.setSizeFactor(teasize);
			beverage.setRecipe(milkfreeteadrink);
		} else if (name.equals("whitetea")) {
			beverage = new WhiteTea(name);
			beverage.setSizeFactor(teasize);
			beverage.setRecipe(milkfreeteadrink);
		} else {
			// Invalid beverage type.
			return null;
		}

		for (String ingredient : ingredients) {
			beverage = addIngredient(beverage, ingredient.toLowerCase());
			if (beverage == null) {
				// Invalid ingredient.
				return null;
			}
		}

		return beverage;
	}

	private static FlavoredBeverage addIngredient(Beverage beverage,
			String ingredient) {
		FlavoredBeverage flavoredBeverage = null;

		CoffeeDrink coffeedrink = new CoffeeDrink();
		TeaDrink teadrink = new TeaDrink();

		if (ingredient.equals("milk")) {
			flavoredBeverage = new Milk(beverage.getDescription(), beverage);
			if (flavoredBeverage.isCoffee) {
				flavoredBeverage.setRecipe(coffeedrink);
			}else if (!flavoredBeverage.isCoffee){
				flavoredBeverage.setRecipe(teadrink);
			}else{
				return null;
			}
		} else if (ingredient.equals("whippedcream")) {
			flavoredBeverage = new WhipCream(beverage.getDescription(),
					beverage);
		} else if (ingredient.equals("jasmine")) {
			flavoredBeverage = new Jasmine(beverage.getDescription(), beverage);
		} else if (ingredient.equals("ginger")) {
			flavoredBeverage = new Ginger(beverage.getDescription(), beverage);
		} else if (ingredient.equals("chocolate")) {
			flavoredBeverage = new Chocolate(beverage.getDescription(),
					beverage);
		} else {
			return null;
		}

		return flavoredBeverage;
	}

}
