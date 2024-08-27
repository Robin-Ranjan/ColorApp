package com.task.janitritask

import android.app.Application
import android.graphics.Color
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.task.janitritask.locaData.ColorDataDao
import com.task.janitritask.locaData.ColorDataEntity
import com.task.janitritask.locaData.ColorDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ColorViewModal(application: Application) : AndroidViewModel(application) {
    private val colorDao: ColorDataDao = Room.databaseBuilder(
        application,
        ColorDatabase::class.java, "colors_db"
    ).build().colorDao()

//    val colors = colorDao.getAllColors().asLiveData()
    val colors: LiveData<List<ColorDataEntity>> = colorDao.getAllColors()

    val unsyncedColors: LiveData<List<ColorDataEntity>> = colorDao.getUnsyncedColors()
    val unsyncedColorsCount: LiveData<Int> = colorDao.getUnsyncedColorsCount().asLiveData()
    fun addRandomColor() {
        viewModelScope.launch(Dispatchers.IO) {
            val color = generateRandomColor()
            val colorData = ColorDataEntity(
                colorCode = color,
                date = getCurrentDate()
            )
            colorDao.insertColor(colorData)
        }
    }

    private fun generateRandomColor(): String {
        val red = Random.nextInt(128, 256)
        val green = Random.nextInt(128, 256)
        val blue = Random.nextInt(128, 256)

        return String.format("#%02X%02X%02X", red, green, blue)
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun syncColors() {
        viewModelScope.launch(Dispatchers.IO) {
            val unsyncedColorsList = unsyncedColors.value ?: return@launch
            val firestore = FirebaseFirestore.getInstance()

            unsyncedColorsList.forEach { colorData ->
                val colorMap = hashMapOf(
                    "colorCode" to colorData.colorCode,
                    "data" to colorData.date
                )

                firestore.collection("colors")
                    .add(colorMap)
                    .addOnSuccessListener {
//                        colorData.synced = true
                        viewModelScope.launch(Dispatchers.IO) {
                            colorData.synced = true
                            colorDao.updateColor(colorData)
                        }
                        viewModelScope.launch(Dispatchers.Main) {
                            Toast.makeText(
                                getApplication<Application>().applicationContext,
                                "Color synced successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .addOnFailureListener{ exception ->
                        viewModelScope.launch(Dispatchers.Main) {
                            Toast.makeText(getApplication<Application>().applicationContext, "fail $exception", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}