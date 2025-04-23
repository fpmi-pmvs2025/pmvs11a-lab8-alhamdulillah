package com.example.chess

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPlay).setOnClickListener {
            startGame()
        }

        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            openSettings()
        }
    }

    private fun startGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, GameFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openSettings() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SettingsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            showExitConfirmationDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Выйти в меню")
            .setMessage("Вы уверены, что хотите выйти в меню?")
            .setPositiveButton("Да") { _: DialogInterface, _: Int ->
                supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Нет", null)
            .show()
    }
}
