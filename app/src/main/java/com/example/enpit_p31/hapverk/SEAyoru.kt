package com.example.enpit_p31.hapverk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.widget.RadioButton
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_seayoru.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SEAyoru : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seayoru)
        realm = Realm.getDefaultInstance()
        radioGroupyoru.setOnCheckedChangeListener { group, checkedId ->
            yorugohan.text = findViewById<RadioButton>(checkedId).text }
        val scheduleIdY = intent?.getLongExtra("scheduleY_id", -1L)
        if (scheduleIdY != -1L) {
            val schduleY =realm.where<SY>()
                    .equalTo("id",scheduleIdY).findFirst()
            day2Edit.setText(DateFormat.format("yyyy/MM/dd",schduleY?.day2))
            mei2Edit.setText(schduleY?.mei2)
            detail3Edit.setText(schduleY?.detailyorugohan)
        }
        saveyoru.setOnClickListener {
            when(scheduleIdY){
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<SY>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val scheduleAsa = realm.createObject<SY>(nextId)
                        day2Edit.text.toString().toDate("yyyy/MM/dd")?.let {
                            scheduleAsa.day2 = it
                        }
                        scheduleAsa.mei2 = mei2Edit.text.toString()
                        scheduleAsa.detailyorugohan = detail3Edit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                realm.executeTransaction {
                    val schduleY =realm.where<SY>()
                            .equalTo("id",scheduleIdY).findFirst()
                    day2Edit.setText(DateFormat.format("yyyy/MM/dd",schduleY?.day2))
                    mei2Edit.setText(schduleY?.mei2)
                    detail3Edit.setText(schduleY?.detailyorugohan)
                }
                    alert ("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm") : Date? {
        val sdFormat = try {
            SimpleDateFormat(pattern)
        } catch (e: IllegalAccessException){
            null
        }
        val day2 = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return day2
    }
}
