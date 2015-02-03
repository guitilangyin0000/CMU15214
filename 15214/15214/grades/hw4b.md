hw4b Feedback
============

#### Implementation of Scrabble game (57/60)

-3, The player has no place to store its special tiles. When special tiles are bought, this forces the caller of `buySpecialTile()` to maintain the special tiles available rather than the player classes.

#### Testing and Build Automation (25/25)

#### Program Design (13/15)

-2, It's a bad design to have a boolean flag for each `SpecialTile` in your `Game` - what if there are 100 different special tiles?

#### Design Response (10/10)

Regarding the last answer: 

"Boom: All tiles in a 3-tile radius on the board are removed from the board. Only surviving tiles are scored for this round." -- handout

#### Documentation and Style (8/10)

-1, Presence of dead code. Your submission should be finalized. (Game.java)

-1, Variable names should start with lowercase letters and hould be `camelCase`. (Game#makeAction())

---

#### Total (113/120)

Late days used: 0 (4 left)

---

#### Additional Notes

If the caller of `Game#getScore` already has a reference to `Player`, why does it need to call `Game#getScore` at all?

Graded by: Harry Zeng (zeyuzeng@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw4b.md