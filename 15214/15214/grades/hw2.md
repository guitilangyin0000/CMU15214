hw2 Feedback
============

#### Design documents demonstrating an understanding of the design process (13/15)

*-2, According to your object model, there is no way to keep track of the player's money (note this is different than a player's hand value).

#### A domain model, object model, and interaction diagrams that accurately model Blackjack and follow design principles (32/40)

*-2, In each of your sequence diagrams, you should not have one of the classes initiating the sequence of events. This is something that should happened from an outside actor (a GUI for example). 

*-2, In your first sequence diagram, it's not entirely clear how the player is determining her score. You should have added your Card class to the diagram and shown that it was looping over this collection of cards calling getValue().

*-2, From your second sequence diagram, it is not clear that these are being given to the player and the dealer. In your object model, the player and the dealer have a collection of cards, so in your sequence diagram, the GameDriver class should be be giving the cards to the player and the dealer with a giveCard(Card) method or something similar.

*-2, In your second sequence diagram, you are not updating the player's money amount (note different than the value of the hand).

#### Clear design documents using correct notation (5/5)

#### Accurate behavioral contracts for the third use-case scenario (5/5)

#### Faithful implementation of the coffee shop software (19/25)

*-2, You could have achieved more code reuse by implementing setSizeFactor() in Beverage.java instead of in each of the classes that extend Coffee and Tea.

*-2, You could have achieved more code reuse by implementing setRecipe() in Beverage.java instead of in each of the classes that extend Coffee and Tea.

*-2, You could have achieved more code reuse by implementing prepare() in Beverage.java instead of in each of the classes that extend Coffee and Tea.

*-0.1, Instead of using instanceof and casting to determine how to set the isCoffee variable, you could have added an isCoffee abstract method to Beverage.java and then had Coffee and Tea implement this. This way you could have avoided using instanceof (reflection) which is highly inefficient and frowned upon in most cases. 

#### Documentation and Style (10/10)

---

#### Total (84/100)

Late days used: 0 (5 left)

---

#### Additional Notes

Graded by: Matt Gode (mgode@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw2.md
