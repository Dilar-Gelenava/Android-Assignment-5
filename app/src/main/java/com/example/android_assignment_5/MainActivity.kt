package com.example.android_assignment_5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var seedButton: FloatingActionButton
    private lateinit var deleteButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onResume() {
        super.onResume()

        setDataToRecyclerView()
    }

    private fun init() {
        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener() {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivity(intent)
        }

        seedButton = findViewById(R.id.seedButton)
        seedButton.setOnClickListener() {
            CarDBHelper(this).deleteAllCars()
            seedDatabase()
            setDataToRecyclerView()
        }

        deleteButton = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener() {
            CarDBHelper(this).deleteAllCars()
            setDataToRecyclerView()
        }

        setDataToRecyclerView()
    }

    private fun setDataToRecyclerView() {
        val allCars = CarDBHelper(this).getAllCars()
        recyclerView = findViewById(R.id.recyclerView)
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        val adapter = CarRecyclerAdapter(allCars)
        recyclerView.adapter = adapter
    }

    private fun seedDatabase() {
        val carsToInsert = arrayOf(
            Car(0, "BMW", 4.5f, 2018),
            Car(0, "Mercedes", 5.5f, 2017),
            Car(0, "Volvo", 6.5f, 2016),
            Car(0, "Ford", 7.5f, 2015),
            Car(0, "Fiat", 8.5f, 2014),
            Car(0, "Opel", 9.5f, 2013),
            Car(0, "Mazda", 10.5f, 2012),
            Car(0, "Toyota", 11.5f, 2011),
            Car(0, "Honda", 12.5f, 2010),
            Car(0, "Nissan", 13.5f, 2009),
            Car(0, "Mitsubishi", 14.5f, 2008),
            Car(0, "Suzuki", 15.5f, 2007),
            Car(0, "Kia", 16.5f, 2006),
            Car(0, "Hyundai", 17.5f, 2005),
            Car(0, "Chevrolet", 18.5f, 2004),
            Car(0, "Dodge", 19.5f, 2003),
        )

        CarDBHelper(this).apply {
            for (carToInsert in carsToInsert) {
                insertCar(carToInsert)
            }
        }
    }
}