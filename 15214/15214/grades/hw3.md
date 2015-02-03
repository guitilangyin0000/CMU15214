hw3 Feedback
============

#### A complete implementation matching our specification, including all nine additional item classes and working RabbitAI and FoxAI implementations (42/44)

* -1, Your AI should not rely on `getName()` determine the type of an `Item` because that is relying on a specific implementation of that `Item`.

#### Program design that demonstrates understanding of design principles and the use of inheritance and delegation to achieve design flexibility and code reuse (28/30)

* -2, You should not modify the state of objects directly in `getNextAction()`. Instead, create a new `Command`. ([link](https://github.com/CMU-15-214/yangwan2/blob/master/homework/3/src/edu/cmu/cs/cs15214/hw3/vehicles/Car.java#L76))

#### A solution that demonstrates understanding of the use of the Template Method pattern to achieve code reuse (12/12)

####Behavioral specification and testing of your vehicles classes (6/24)

* -12, You did not specify any behavioral contracts for your vehicles.

* -6, Your tests do not fully test the behavior of your vehicle. You should verify that they crash, speedup, can't turn at high speeds, etc. 

####Java coding and testing practices and style (9/10)

* -1, Some of your abstract classes have a significant number of uncommented methods.

---

#### Total (97/120)

Late days used: 1 (4 left)

---

#### Additional Notes

Graded by: Ken Li (kyli@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw3.md