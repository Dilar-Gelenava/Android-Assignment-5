package com.example.android_assignment_5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class AddCarActivity : AppCompatActivity() {
    lateinit var saveCarButton: Button
    lateinit var carNameInput: EditText
    lateinit var carEngineInput: EditText
    lateinit var carYearOfReleaseInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_add)

        init()
    }

    private fun init() {
        saveCarButton = findViewById(R.id.saveCarButton)
        carNameInput = findViewById(R.id.carNameInput)
        carEngineInput = findViewById(R.id.carEngineInput)
        carYearOfReleaseInput = findViewById(R.id.carYearOfReleaseInput)

        saveCarButton.setOnClickListener {
            addCar()
        }
    }

    private fun addCar() {
        try {
            val carName = carNameInput.text.toString()
            val carEngine = carEngineInput.text.toString().toFloat()
            val carYearOfRelease = carYearOfReleaseInput.text.toString().toInt()

            val carToInsert = Car(
                0,
                carName,
                carEngine,
                carYearOfRelease
            )

            CarDBHelper(this).apply {
                insertCar(carToInsert)
            }

            Snackbar.make(
                findViewById(android.R.id.content),
                "Car added successfully",
                Snackbar.LENGTH_LONG
            ).show()

            finish()
        } catch (e: Exception) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Error: ${e.message}",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}

