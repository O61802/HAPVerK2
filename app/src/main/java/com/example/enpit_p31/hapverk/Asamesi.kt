package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_asamesi.*
import org.jetbrains.anko.startActivity

class Asamesi : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asamesi)
        realm = Realm.getDefaultInstance()
        val schedules = realm.where<ScheduleAsa>().findAll()
        AsalistView.adapter = ScheduleAdapterAsa(schedules)
        modoruAsa.setOnClickListener { finish() }
        Name.setOnClickListener { onNamaeAsa(it) }
        AsalistView.setOnItemClickListener { parent, view, position, id ->
            val scheduleasa = parent.getItemAtPosition(position) as ScheduleAsa
            startActivity<ScheduleEditAsaActivity>(
                    "scheduleasa_id" to scheduleasa.id
            )
        }
    }
    fun onNamaeAsa(view:View?){
        val intent = Intent(this,ScheduleEditAsaActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
