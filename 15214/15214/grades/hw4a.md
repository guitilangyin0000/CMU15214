hw4a Feedback
============

**IMPORTANT:** Please respond to questions about your design in a file called `{your repo}/homework/4/response.pdf`. This will count as part of your Milestone B grade!

#### Demonstrate a comprehensive design process including object-oriented analysis, object-oriented design (25/30)

-1, `Dictionary` is in `Game` in your domain model, but it is in `Board` in your object model. There should be some consistency between the domain and object model.

-1, `Game.haveAction` takes no argument in your object model, but it takes `Action` as an argument in your interaction_move diagram.

-3, * Your `Board` is passing a `Game` as a parameter in `makeSpecialEffect`, but it is suggested by the object diagram that there isn’t a reference.

#### Demonstrate the use of design goals to influence your design choices, assigning responsibilities carefully, discussing trade-offs among alternative designs, using design patterns where appropriate, and choosing an appropriate solution. (69/70)

-1, In your interaction_move diagram, tiles should be placed before special effects are applied. What if some tiles are removed by `Boom`?

#### Communicate design ideas clearly, including design documents that demonstrate fluency with the basic notation of UML class diagrams and interaction diagrams, the correct use of design vocabulary, and an appropriate level of formality in the specification of system behavior. (25/25)


---

#### Total (114/120)

Late days used: 0 (4 left)

---

#### Additional Notes

Graded by: Harry Zeng (zeyuzeng@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw4a.md