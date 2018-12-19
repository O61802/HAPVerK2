package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_kojin.*
import org.jetbrains.anko.startActivity

class Kojin : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kojin)
        modoruK.setOnClickListener { finish() }
        realm = Realm.getDefaultInstance()
        val schedules = realm.where<SK>().findAll()
        KojinlistView.adapter = SAkojin(schedules)
        kozinN.setOnClickListener { onNamaekojin(it) }
        KojinlistView.setOnItemClickListener { parent, view, position, id ->
            val schduleK = parent.getItemAtPosition(position) as SK
            startActivity<SEAkojin>("scheduleK_id" to schduleK.id)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    fun onNamaekojin(view: View?){
        val intent = Intent(this,SEAkojin::class.java)
        startActivity(intent)
    }
}
