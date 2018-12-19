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
import kotlinx.android.synthetic.main.activity_seahiru.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SEAhiru : AppCompatActivity() {
private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seahiru)
        radioGroup.setOnCheckedChangeListener { group, checkedId -> hirugohan.text = findViewById<RadioButton>(checkedId).text }
        realm = Realm.getDefaultInstance()

        val schedulehiruId = intent?.getLongExtra("schedulehiru_id", -1L)
        if (schedulehiruId != -1L) {
            val schedulehiru = realm.where<SH>()
                    .equalTo("id", schedulehiruId).findFirst()
            dateEdit.setText(
                    DateFormat.format("yyyy/MM/dd", schedulehiru?.day))
            meiEdit.setText(schedulehiru?.mei)
            detail2Edit.setText(schedulehiru?.detailHil)
            deletehiru.visibility = View.VISIBLE
        }else{
            deletehiru.visibility = View.INVISIBLE
        }

        savehiru.setOnClickListener {
            when (schedulehiruId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<SH>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val schedulehiru = realm.createObject<SH>(nextId)
                        dayEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            schedulehiru.day
                        }
                        schedulehiru.mei = meiEdit.text.toString()
                        schedulehiru.detailHil = detail2Edit.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                realm.executeTransaction {
                    val scheduleAsa = realm.where<SH>()
                            .equalTo("Id", schedulehiruId) .findFirst()
                    dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                        scheduleAsa?.day = it
                    }
                    scheduleAsa?.mei = meiEdit.text.toString()
                    scheduleAsa?.detailHil = detail2Edit.text.toString()
                }
                    alert("修正しました"){
                        yesButton { finish() }
                    }.show()
                }
            }
        }
        deletehiru.setOnClickListener {
            realm.executeTransaction {
                realm.where<ScheduleAsa>() .equalTo("id", schedulehiruId)?.findFirst()?.deleteFromRealm()
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
        val day = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return day
    }
}
