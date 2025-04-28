package com.example.chess

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val themeSpinner: Spinner = view.findViewById(R.id.themeSpinner)
        val sharedPreferences = requireContext().getSharedPreferences("ChessGamePrefs", Context.MODE_PRIVATE)
        val selectedTheme = sharedPreferences.getString("theme", "classic") ?: "classic"

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.themes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            themeSpinner.adapter = adapter
        }

        themeSpinner.setSelection(getIndex(themeSpinner, selectedTheme))

        themeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val theme = parent.getItemAtPosition(position).toString().toLowerCase()
                sharedPreferences.edit().putString("theme", theme).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return view
    }

    private fun getIndex(spinner: Spinner, theme: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().toLowerCase() == theme) {
                return i
            }
        }
        return 0
    }
}
