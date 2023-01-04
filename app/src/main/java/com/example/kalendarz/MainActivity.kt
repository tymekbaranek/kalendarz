package com.example.kalendarz

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.Calendar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

//Tymoteusz Baranowski
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val PrzyciskK = findViewById<Button>(R.id.PrzyciskK)
        val DataRozp = findViewById<TextView>(R.id.DataRozp)
        val DataKonc = findViewById<TextView>(R.id.DataKonc)
        val DlgOut = findViewById<TextView>(R.id.DlgOut)
        val Kalendarz=Calendar.getInstance()
        val DataMin= Kalendarz.timeInMillis
        val DataMax= Kalendarz.timeInMillis + 63113904000
        val Limit= CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .setStart(DataMin)
            .setEnd(DataMax)
            .build()

        PrzyciskK.setOnClickListener {
            var StartDate : Long=1
            var EndDate : Long=1
            val calendar = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(Limit)
                .setTitleText("Wybierz date wyjazdu i przyjazdu")
                .build()
            calendar.show(supportFragmentManager, "Kalendarz")
            calendar.addOnPositiveButtonClickListener { datePicked->
                StartDate = datePicked.first
                EndDate = datePicked.second
                DataRozp.text="Data twojego wyjazdu to: "+ConvertDate(StartDate)
                DataKonc.text="Data twojego przyjazdu to: "+ConvertDate(EndDate)

                val StartDateSub = StartDate/1000/60/60/24
                val EndDateSub = EndDate/1000/60/60/24
                val DatesSub = (EndDateSub-StartDateSub).toInt()
                if(DatesSub==1)
                    DlgOut.text="Twój wyjazd będzie trwał " + DatesSub.toString() + " dzień"
                else
                    DlgOut.text = "Twój wyjazd będzie trwał " + DatesSub.toString() + " dni"
            }
        }
    }
    private fun ConvertDate(date: Long) :String{
        val datetoconvert=Date(date)
        val format=SimpleDateFormat(
            "dd-MM-yyyy"
        )
        return format.format(datetoconvert)
    }
}