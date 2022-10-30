package com.example.android_assignment_5

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CarDetailedActivity: AppCompatActivity() {
    lateinit var carNameView: TextView
    private lateinit var carEngineView: TextView
    private lateinit var carYearOfReleaseView: TextView
    lateinit var deleteCarButton: FloatingActionButton
    lateinit var editCarButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_detailed)

        init()
    }

    private fun init() {
        carNameView = findViewById(R.id.carNameTextView)
        carEngineView = findViewById(R.id.carEngineTextView)
        carYearOfReleaseView = findViewById(R.id.carYearOfReleaseTextView)

        val carId = intent.getSerializableExtra("carId")
        println(carId)

        CarDBHelper(this).apply {
            val car = getCarById(carId as Int)

            carNameView.text = car.name
            carEngineView.text = car.engine.toString()
            carYearOfReleaseView.text = car.yearOfRelease.toString()
        }

        deleteCarButton = findViewById(R.id.deleteButton)
        deleteCarButton.setOnClickListener {
            CarDBHelper(this).apply {
                deleteCar(carId as Int)
            }
            Snackbar.make(
                findViewById(android.R.id.content),
                "Car deleted successfully",
                Snackbar.LENGTH_LONG
            ).show()

            finish()
        }

        editCarButton = findViewById(R.id.editButton)
        editCarButton.setOnClickListener {
            val intent = Intent(this, EditCarActivity::class.java)
            intent.putExtra("carId", carId)
            startActivity(intent)
        }
    }
}

