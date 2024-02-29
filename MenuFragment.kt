/*
 * Author: Javier Flores
 * Date: 2/22/2024
 * Description: This class represents the Menu where Name is Inputed and the Game Starts
 */
package com.hfad.assignmenttictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MenuFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val messageView = view.findViewById<EditText>(R.id.playerName)
        val startButton = view.findViewById<Button>(R.id.start)

        startButton.setOnClickListener {
            val playerName = messageView.text.toString()
            val bundle = Bundle().apply {
                putString("playerName", playerName)
            }
            navController.navigate(R.id.action_menuFragment_to_playFragment, bundle)

        }
    }
}
