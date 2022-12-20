package com.example.kalendarz

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.alpha
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.math.absoluteValue

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val kalendarz = findViewById<CalendarView>(R.id.calendarView)
        val poczatek = findViewById<Button>(R.id.poczatekWyj)
        val koniec = findViewById<Button>(R.id.koniecWyj)
        val poczOut = findViewById<TextView>(R.id.poczatekOut)
        val konOut = findViewById<TextView>(R.id.koniecOut)
        val dlugosc = findViewById<TextView>(R.id.dlgOut)

        kalendarz.minDate = System.currentTimeMillis()
        kalendarz.maxDate = LocalDate.now().plusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        kalendarz.setFirstDayOfWeek(Calendar.MONDAY);

        val dataPocz = mutableListOf<Int>(0,0,0)
        val dataKon = mutableListOf<Int>(0,0,0)
        val date = arrayListOf<Int>(0,0,0)

        kalendarz.setOnDateChangeListener(){CalendarView, d, m, r ->
            date[0] = d
            date[1] = m +1
            date[2] = r
        }

        poczatek.setOnClickListener {
            dataPocz[0] = date[0]
            dataPocz[1] = date[1]
            dataPocz[2] = date[2]
            poczOut.text = "Początek wyjazdu: " +date[0].toString()+"-"+date[1].toString()+"-"+date[2].toString();
            kalendarz.selectedDateVerticalBar
        }
        koniec.setOnClickListener {
            dataKon[0] = date[0]
            dataKon[1] = date[1]
            dataKon[2] = date[2]
            konOut.text = "Koniec wyjazdu: " +date[0].toString()+"-"+date[1].toString()+"-"+date[2].toString();

            if(dataPocz[0] != 0 && dataKon[0] != 0)
                if(dataPocz[0] > dataKon[0] || dataPocz[2] > dataKon[2] && dataPocz[1] == dataKon[1])
                    dlugosc.text = "Koniec wyjazdu nie może być wcześniej niż początek"
                else{
                    val temp1 = (dataKon[0]*360) + (dataKon[1]*30) + dataKon[2]
                    val temp2 = (dataPocz[0]*360) + (dataPocz[1]*30) + dataPocz[2]
                    val temp = temp1.toChar() - temp2.toChar()
                    dlugosc.text = "Wyjazd trwa "+temp.toString()+" dni"}
        }
    }
}