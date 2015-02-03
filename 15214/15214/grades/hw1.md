hw1 Feedback
============

#### Correct Graph and Algorithm Implementations (49/50)

* -1, the `suggestFriend` method in `Algorithms` failed the  test case on a single node graph on both `AdjacencyList` and `AdjacencyMatrix` graphs.

#### Program Design (10/10)

#### Unit Testing Best Practices and Code Coverage (23/30)

* -4, You only tested on one example graph. It would be better practice to test on different types of graphs, e.g. complete graphs, graphs with cycles, a single vertex graph, etc. to cover edge cases in your code.

* -3, Tests should be split up into the smallest testable parts of a program, not lumped into large test methods. (You may also want to test `isAdjacent`, `getNeighbors` instead of only testing the algorithms implementation.) 

#### Build Automation with Ant and Travis-CI (18/20)

*-2, It is unclear why your `build.xml` refers to a `LinkedIntQueue.jar`, since it was not used for this homework.

#### Documentation and Style (10/10)

---

#### Total (110/120)

Late days used: 0 (5 left)

---

#### Additional Notes

Graded by: Siyu Wei (siyuwei@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw1.md