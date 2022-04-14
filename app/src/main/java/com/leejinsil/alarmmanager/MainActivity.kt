package com.leejinsil.alarmmanager

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.leejinsil.alarmmanager.Const.Companion.NOTIFICATION_ID
import com.leejinsil.alarmmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    companion object{
        val mCalendar = Calendar.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


//      DatePickerDialog
        binding.day.setOnClickListener {

            val dsl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                    mCalendar.set(year, month, dayOfMonth)
                    val sdf = SimpleDateFormat("yy/MM/dd (E)")
                    binding.day.text = sdf.format(mCalendar.time)

                }
            }

            val dpd = DatePickerDialog(
                this,
                dsl,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

//      TimePickDialog
        binding.hour.setOnClickListener {

            val tsl = object : TimePickerDialog.OnTimeSetListener{

                override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

                    mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mCalendar.set(Calendar.MINUTE,minute)
                    mCalendar.set(Calendar.SECOND, 0)
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.hour.text = sdf.format(mCalendar.time)
                }

            }

            val tpd = TimePickerDialog(
                this,
                tsl,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                false
            ).show()

        }


//      AlarmManager 생성
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

//      PendingIntent 생성
        val receiverIntent = Intent(this, AlarmReceiver::class.java)
        val PendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            receiverIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        binding.btnToggle.setOnCheckedChangeListener { compoundButton, check ->

            if (check) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    mCalendar.timeInMillis,
                    PendingIntent
                )

                Log.d("시간", mCalendar.time.toString())

            } else {
                alarmManager.cancel(PendingIntent)
            }

        }


    }


}