package com.example.enpit_p31.hapverk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import android.widget.RadioButton
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit_asa.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditAsaActivity : AppCompatActivity() {
    private  lateinit var  realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit_asa)
        radioGroupHiru.setOnCheckedChangeListener { group, checkedId -> yorugohan.text = findViewById<RadioButton>(checkedId).text  }
        realm = Realm.getDefaultInstance()

        val scheduleasaId = intent?.getLongExtra("scheduleasa_id", -1L)
        if (scheduleasaId != -1L) {
            val scheduleasa = realm.where<ScheduleAsa>()
                    .equalTo("id", scheduleasaId).findFirst()
            dateEdit.setText(
                    DateFormat.format("yyyy/MM/dd", scheduleasa?.date))
            titleEditAsa.setText(scheduleasa?.title)
            detailEditAsa.setText(scheduleasa?.detail)
            deleteAsa.visibility = View.VISIBLE
        }else{
            deleteAsa.visibility = View.INVISIBLE
        }

        saveAsa.setOnClickListener {
            when (scheduleasaId) {
                -1L ->{
                    realm.executeTransaction {
                        val maxIdasa = realm.where<ScheduleAsa>().max("id")
                        val nextIdasa = (maxIdasa?.toLong() ?: 0L) + 1
                        val scheduleasa = realm.createObject<ScheduleAsa>(nextIdasa)
                        dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            scheduleasa.date = it
                        }
                        scheduleasa.title = titleEditAsa.text.toString()
                        scheduleasa.detail = detailEditAsa.text.toString()
                    }
                alert("追加しました") {
                    yesButton { finish() }
                }.show()
            }
            else -> {
            realm.executeTransaction {
                val scheduleasa = realm.where<ScheduleAsa>()
                        .equalTo("id", scheduleasaId).findFirst()
                dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                    scheduleasa?.date = it
                }
                scheduleasa?.title = titleEditAsa.text.toString()
                scheduleasa?.detail = detailEditAsa.text.toString()
            }
                alert("修正しました"){
                    yesButton { finish() }
                }.show()
            }
            }
        }
        deleteAsa.setOnClickListener {
            realm.executeTransaction {
                realm.where<ScheduleAsa>() .equalTo("id", scheduleasaId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
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
        val date = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return date
    }
}
