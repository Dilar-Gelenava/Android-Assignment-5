package com.example.android_assignment_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar


class EditCarActivity : AppCompatActivity() {
    lateinit var updateCarButton: Button
    lateinit var carNameInput: EditText
    lateinit var carEngineInput: EditText
    lateinit var carYearOfReleaseInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_edit)

        init()
    }

    private fun init() {
        updateCarButton = findViewById(R.id.updateCarButton)
        carNameInput = findViewById(R.id.carNameInputEdit)
        carEngineInput = findViewById(R.id.carEngineInputEdit)
        carYearOfReleaseInput = findViewById(R.id.carYearOfReleaseInputEdit)

        val carId = intent.getSerializableExtra("carId")
        CarDBHelper(this).apply {
            val car = getCarById(carId as Int)

            carNameInput.setText(car.name)
            carEngineInput.setText(car.engine.toString())
            carYearOfReleaseInput.setText(car.yearOfRelease.toString())
        }

        updateCarButton.setOnClickListener {
            updateCar(carId as Int)
        }
    }

    private fun updateCar(carId: Int) {
        try {
            val carName = carNameInput.text.toString()
            val carEngine = carEngineInput.text.toString().toFloat()
            val carYearOfRelease = carYearOfReleaseInput.text.toString().toInt()

            val carToUpdate = Car(
                carId,
                carName,
                carEngine,
                carYearOfRelease
            )

            CarDBHelper(this).apply {
                updateCar(carToUpdate)
            }

            Snackbar.make(
                findViewById(android.R.id.content),
                "Car updated successfully",
                Snackbar.LENGTH_LONG
            ).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Error: ${e.message}",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
