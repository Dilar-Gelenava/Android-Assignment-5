package com.example.android_assignment_5

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarRecyclerAdapter(private var cars: ArrayList<Car>) :
    RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCars(cars: ArrayList<Car>) {
        this.cars = cars
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(car: Car) {
            itemView.findViewById<TextView>(R.id.carName).text = car.name
            itemView.findViewById<TextView>(R.id.carEngine).text = car.engine.toString()
            itemView.findViewById<TextView>(R.id.carYearOfRelease).text = car.yearOfRelease.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CarDetailedActivity::class.java)
                intent.putExtra("carId", car.id);
                itemView.context.startActivity(intent)
            }
        }

    }
}