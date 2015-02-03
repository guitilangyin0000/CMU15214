Hi TA, I will introduce my version of scribble with stuff 1.0, the GUI part

First, after you run my main function which is in the Main.class of GUI package, you will see a window popping up and tell you to input the players’ names into the text fields, the maximum number of players for this game is 4 and you cannot input empty player name, by the way, the players’ names could not be the same.

Then after you click the “launch” button, you will see the game panel of the scribble game. There are several parts in this panel, left is the board panel which records the tiles condition of the board, there are small separated panel for each cells, when you are playing the game, you will find the small panel has two parts, the left part represents the letter and the value of the tiles and right part represents if there’s special tile on that location, the sign of the special tile will be represented by the first letter of the special tile like “B” or “O”, player only can see his/her own special tiles when playing. Then for the right part of the game panel, you will see the player information panel, player command panel and message panel. The player information panel will tell you the names of the players and the scores of them, and you will only see your inventory when playing, the command panel will tell you who the current player is and there are two main kinds of commands you can use, first is the buying special tile command, their prices are shown after them, the second command is to exchange, to place tiles, to pass the turn and to quit the game. Then I will tell what you should do when it’s your turn.

1) If you want to buy a special tile, please buy it immediately after you know it’s your turn, or after you do the exchange or place action, the turn will directly jump to another player, after you click the special tile button, your score will be deleted some and you need to define the coordinate of the special tile, you can type in the coordinate like:
x: 1
y: 1
(1, 1) is the real input, the coordinate is shown on the board panel, if you decide to cancel the purchase, your score will be refunded. You can buy as many special tiles as you want, but the price for the special tile is not that cheap.

2) Then, if you want to pass, please click the “pass” button. And if you want to quit the game, the message panel will tell you who the winner is.

3) If you press the “exchange” button, you will see the exchange window, please input the tiles you want to exchange in the tile package, like “ABCD”, please type in the letters in sequence and use upper case. The message panel will tell you error if you have input the wrong text. After submitting, you will get the tile exchanged.

4) If you press the “place” button, which means you want to place tiles on the board, then  a small panel will shown at the bottom of the panel, please input the start location of the word you want to put and the direction of your word, then after submitting, you will see the placement window jump out, now you need to type in the words, please fill one text field with one letter and the letter should be upper case, after you press the “place” button, the message panel will tell you whether you are making a valid placement, you can also cancel the placement and try again.

So thank you and enjoy the game!
