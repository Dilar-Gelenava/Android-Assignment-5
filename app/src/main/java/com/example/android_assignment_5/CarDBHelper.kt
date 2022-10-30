package com.example.android_assignment_5

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class CarDBHelper(context: Context) :
    SQLiteOpenHelper(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    companion object {
        private const val CREATE_TABLE = "CREATE TABLE " + Config.TABLE_NAME + "(" +
                CarContract.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CarContract.NAME + " TEXT," +
                CarContract.ENGINE + " DOUBLE," +
                CarContract.YEAR_OF_RELEASE + " INTEGER" + ")"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + Config.TABLE_NAME
    }

    fun insertCar(car: Car): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CarContract.NAME, car.name)
        values.put(CarContract.ENGINE, car.engine)
        values.put(CarContract.YEAR_OF_RELEASE, car.yearOfRelease)
        val id = db.insert(Config.TABLE_NAME, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllCars(): ArrayList<Car> {
        val cars = ArrayList<Car>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM " + Config.TABLE_NAME +
                    " ORDER BY " + CarContract.ID + " DESC", null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(CarContract.ID))
                val name = cursor.getString(cursor.getColumnIndex(CarContract.NAME))
                val engine = cursor.getFloat(cursor.getColumnIndex(CarContract.ENGINE))
                val yearOfRelease =
                    cursor.getInt(cursor.getColumnIndex(CarContract.YEAR_OF_RELEASE))
                val car = Car(id, name, engine, yearOfRelease)
                cars.add(car)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cars
    }

    @SuppressLint("Range")
    fun getCarById(id: Int): Car {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM " + Config.TABLE_NAME + " WHERE " + CarContract.ID + " = " + id,
            null
        )
        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndex(CarContract.NAME))
        val engine = cursor.getFloat(cursor.getColumnIndex(CarContract.ENGINE))
        val yearOfRelease =
            cursor.getInt(cursor.getColumnIndex(CarContract.YEAR_OF_RELEASE))
        val car = Car(id, name, engine, yearOfRelease)
        cursor.close()
        db.close()
        return car
    }

    fun updateCar(car: Car): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CarContract.NAME, car.name)
        values.put(CarContract.ENGINE, car.engine)
        values.put(CarContract.YEAR_OF_RELEASE, car.yearOfRelease)
        return db.update(
            Config.TABLE_NAME, values, CarContract.ID + " = ?",
            arrayOf(car.id.toString())
        )
    }

    fun deleteCar(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(
            Config.TABLE_NAME, CarContract.ID + " = ?",
            arrayOf(id.toString())
        )
    }

    fun deleteAllCars(): Int {
        val db = this.writableDatabase
        return db.delete(
            Config.TABLE_NAME, null, null
        )
    }
}
