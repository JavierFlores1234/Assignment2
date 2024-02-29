package com.hfad.assignmenttictactoe

/*
 * Author: Javier Flores
 * Date: 2/22/2024
 * Description: Backend of the game
 */
class FourInARow : IGame {
    // Game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS) { 0 } }

    //clearboard
    override fun clearBoard() {
        for (rowIndex in 0 until GameConstants.ROWS) {
            for (colIndex in 0 until GameConstants.COLS) {
                board[rowIndex][colIndex] = 0
            }
        }
    }
    //setplayer
    override fun setMove(player: Int, location: Int) {
        if (board[location / 6 ][location % 6] == GameConstants.EMPTY){
            board[location / 6 ][location % 6] = player
        }
    }

    override val computerMove: Int
        get() {
            for (index in 0 until GameConstants.ROWS * GameConstants.COLS) {
                val row = index / GameConstants.COLS
                val col = index % GameConstants.COLS
                if (board[row][col] == 0) {
                    return index
                }
            }
            return -1
        }
    //winner check
    override fun checkForWinner() : Int {
        // Check for 4 consecutive discs in a row horizontally, vertically, or diagonally
        var computerWins = 0;
        var playerWins = 0;

        // Check horizontally
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS - 3) {
                if (board[row][col] != 0 &&
                    board[row][col] == board[row][col + 1] &&
                    board[row][col] == board[row][col + 2] &&
                    board[row][col] == board[row][col + 3]

                ) {
                    if (board[row][col] == GameConstants.BLUE) {
                        playerWins++;

                    } else {
                        computerWins++;
                    }
                }
            }
        }

        // Check vertically
        for (colIndex in 0 until GameConstants.COLS) {
            for (rowIndex in 0 until GameConstants.ROWS - 3) {
                if (board[rowIndex][colIndex] != 0 &&
                    board[rowIndex][colIndex] == board[rowIndex + 1][colIndex] &&
                    board[rowIndex][colIndex] == board[rowIndex + 2][colIndex] &&
                    board[rowIndex][colIndex] == board[rowIndex + 3][colIndex]
                ) {
                    if (board[rowIndex][colIndex] == GameConstants.BLUE) {
                        playerWins++;
                    } else {
                        computerWins++;
                    }
                }
            }
        }

        // Check diagonals
        for (row in 0 until GameConstants.ROWS - 3) {
            for (col in 0 until GameConstants.COLS - 3) {
                // Check diagonals from bottom-left to top-right
                if (board[row][col] != 0 &&
                    board[row][col] == board[row + 1][col + 1] &&
                    board[row][col] == board[row + 2][col + 2] &&
                    board[row][col] == board[row + 3][col + 3]
                ) {
                    if (board[row][col] == GameConstants.BLUE) {
                        playerWins++;
                    } else {
                        computerWins++;
                    }
                }
                // Check diagonals from top-left to bottom-right
                if (board[GameConstants.ROWS - 1 - row][col] != 0 &&
                    board[GameConstants.ROWS - 1 - row][col] == board[GameConstants.ROWS - 1 - row][col + 1] &&
                    board[GameConstants.ROWS - 1 - row][col] == board[GameConstants.ROWS - 2 - row][col + 2] &&
                    board[GameConstants.ROWS - 1 - row][col] == board[GameConstants.ROWS - 3 - row][col + 3]
                ) {
                    if (board[GameConstants.ROWS - 1 - row][col] == GameConstants.BLUE) {
                        playerWins++;
                    } else {
                        computerWins++;
                    }
                }
            }
        }

        return if (playerWins == 0 && computerWins == 0){
            GameConstants.PLAYING;
        } else if (computerWins > playerWins) {
            GameConstants.RED_WON;
        } else if (playerWins > computerWins) {
            GameConstants.BLUE_WON;
        } else {
            GameConstants.TIE;
        }
    }

    fun printBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // Print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // Print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("-----------") // Print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED, or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }
}
