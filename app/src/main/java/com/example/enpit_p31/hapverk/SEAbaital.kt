package com.example.enpit_p31.hapverk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_seabaital.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SEAbaital : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seabaital)
        realm = Realm.getDefaultInstance()

        val schedulBId = intent?.getLongExtra("scheduleasa_id", -1L)
        if (schedulBId != -1L) {
            val scheduleB = realm.where<SB>().equalTo("id", schedulBId).findFirst()
            monceEdit.setText(DateFormat.format("yyyy/MM/dd", scheduleB?.monce))
            kaigosyaEdit.setText(scheduleB?.hikaigosya)
            genkiEdit.setText(scheduleB?.genki)
            suiminEdit.setText(scheduleB?.suiminn)
            syokujiEdit.setText(scheduleB?.syokuzi)
            haisetuEdit.setText(scheduleB?.haisetu)
            deleteB.visibility = View.VISIBLE
        }else{
            deleteB.visibility =View.INVISIBLE
        }

        saveB.setOnClickListener {
            when (schedulBId){
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<SB>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val scheduleB = realm.createObject<SB>(nextId)
                        monceEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            scheduleB.monce = it
                        }
                        scheduleB.hikaigosya = kaigosyaEdit.text.toString()
                        scheduleB.genki = genkiEdit.text.toString()
                        scheduleB.suiminn = suiminEdit.text.toString()
                        scheduleB.syokuzi = syokujiEdit.text.toString()
                        scheduleB.haisetu = haisetuEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val schduleB = realm.where<SB>().equalTo("id", schedulBId).findFirst()
                        monceEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            schduleB?.monce = it
                        }
                        schduleB?.hikaigosya = kaigosyaEdit.text.toString()
                        schduleB?.genki = genkiEdit.text.toString()
                        schduleB?.suiminn = suiminEdit.text.toString()
                        schduleB?.syokuzi = syokujiEdit.text.toString()
                        schduleB?.haisetu = haisetuEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }
        deleteB.setOnClickListener {
            realm.executeTransaction {
                realm.where<ScheduleAsa>().equalTo("id", schedulBId)?.findFirst()?.deleteFromRealm()
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
        } catch (e: IllegalArgumentException) {
            null
        }
        val monce = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return monce
    }
}
