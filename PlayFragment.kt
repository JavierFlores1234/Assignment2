/*
 * Author: Javier Flores
 * Date: 2/22/2024
 * Description: This class represents the PlayFragment, where the Tic Tac Toe game is played.
 */
package com.hfad.assignmenttictactoe

import android.app.GameState
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.NavController
import android.widget.EditText


class PlayFragment : Fragment() {

    private lateinit var firBoard: FourInARow

    private lateinit var ticTacGrid: androidx.gridlayout.widget.GridLayout

    private var currentTurn: Int = GameConstants.RED

    lateinit var navController: NavController

    private var gameState: Int = GameConstants.PLAYING

    lateinit var restart: Button

    private var playerName: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerName = arguments?.getString("playerName")

        ticTacGrid = view.findViewById<androidx.gridlayout.widget.GridLayout>(R.id.ticTacGrid)

        firBoard = FourInARow()

        restart = view.findViewById(R.id.restart)
        restart.setOnClickListener {
            firBoard.clearBoard()
            for(index in 0 until ticTacGrid.childCount) {
                var currentView = ticTacGrid.getChildAt(index)
                currentView.setBackgroundResource(R.drawable.grey_color)
            }
            gameState = GameConstants.PLAYING
            restart.isVisible = false
            view?.findViewById<TextView>(R.id.EndMessage)?.text = ""
        }

        var redDrawable = this.resources.getDrawable(
            R.drawable.red_color,
            context?.theme
        ).constantState.hashCode()
        var blueDrawable = this.resources.getDrawable(
            R.drawable.blue_color,
            context?.theme
        ).constantState.hashCode()

        for (index in 0 until ticTacGrid.childCount) {
            var currentView = ticTacGrid.getChildAt(index)
            currentView.setBackgroundResource(R.drawable.grey_color)

            currentView.setOnClickListener {
                var currDrawable = currentView.background.constantState.hashCode()

                if (currentTurn == GameConstants.RED && (currDrawable != redDrawable && currDrawable != blueDrawable) && gameState == GameConstants.PLAYING) {
                    currentView.setBackgroundResource(R.drawable.red_color)
                    firBoard.setMove(GameConstants.RED, index)
                    currentTurn = GameConstants.BLUE

                    getComputerMove()

                    checkWinner()


                }
            }
        }
    }

    private fun getComputerMove() {
        var computerMove = firBoard.computerMove
        firBoard.setMove(GameConstants.BLUE, computerMove)
        ticTacGrid.getChildAt(computerMove).setBackgroundResource(R.drawable.blue_color)
        currentTurn = GameConstants.RED
    }

    private fun checkWinner() {
        gameState = firBoard.checkForWinner();

        when (gameState) {
            GameConstants.TIE -> {
                view?.findViewById<TextView>(R.id.EndMessage)?.text = "IT'S A TIE!"
            }

            GameConstants.RED_WON -> {
                view?.findViewById<TextView>(R.id.EndMessage)?.text = "$playerName WINS!!!"
            }

            GameConstants.BLUE_WON -> {
                view?.findViewById<TextView>(R.id.EndMessage)?.text = "COMPUTER WINS!"
            }
        }
        if (gameState != GameConstants.PLAYING){
            restart.isVisible = true
    }
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

}