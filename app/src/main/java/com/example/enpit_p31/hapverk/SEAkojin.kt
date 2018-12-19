package com.example.enpit_p31.hapverk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_seakojin.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SEAkojin : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seakojin)
        realm = Realm.getDefaultInstance()

        val scheduleKId = intent?.getLongExtra("schedulehiru_id", -1L)
        if (scheduleKId != -1L) {
            val scheduleK = realm.where<SK>().equalTo("id", scheduleKId).findFirst()
            yearEdit.setText(DateFormat.format("yyyy/MM/dd", scheduleK?.year))
            tanzyouEdit.setText(DateFormat.format("yyyy/MM/dd", scheduleK?.tannzyou))
            keiyakusyaEdit.setText(scheduleK?.keiyakusya)
            syussinEdit.setText(scheduleK?.syusin)
            kazokuEdit.setText(scheduleK?.kazoku)
            bikouEdit.setText(scheduleK?.tuiki)
            deleteK.visibility = View.VISIBLE
        }else{
            deleteK.visibility = View.INVISIBLE
        }

        saveK.setOnClickListener {
            when (scheduleKId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<SK>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val scheduleK = realm.createObject<SK>(nextId)
                        yearEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            scheduleK.year
                        }
                        tanzyouEdit.text.toString().toTanzyo("yyyy/MM/dd")?.let {
                            scheduleK.tannzyou
                        }
                        scheduleK.keiyakusya = keiyakusyaEdit.text.toString()
                        scheduleK.syusin = syussinEdit.text.toString()
                        scheduleK.kazoku = kazokuEdit.text.toString()
                        scheduleK.tuiki = bikouEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val scheduleK = realm.where<SK>()
                                .equalTo("Id", scheduleKId) .findFirst()
                        yearEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            scheduleK?.year = it
                        }
                        tanzyouEdit.text.toString().toTanzyo("yyyy/MM/dd")?.let {
                            scheduleK?.tannzyou = it
                        }
                        scheduleK?.keiyakusya = keiyakusyaEdit.text.toString()
                        scheduleK?.syusin = syussinEdit.text.toString()
                        scheduleK?.kazoku = kazokuEdit.text.toString()
                        scheduleK?.tuiki = bikouEdit.text.toString()
                    }
                    alert("修正しました"){
                        yesButton { finish() }
                    }.show()
                }
            }
            deleteK.setOnClickListener {
                realm.executeTransaction {
                    realm.where<SK>().equalTo("id", scheduleKId)?.findFirst()?.deleteFromRealm()
                }
                alert("削除しました") {
                    yesButton { finish() }
                }.show()
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
        val day = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return day
    }
    fun String.toTanzyo(pattern: String = "yyyy/MM/dd HH:mm") : Date? {
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
