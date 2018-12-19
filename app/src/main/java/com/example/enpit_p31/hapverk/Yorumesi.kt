package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_hirumesi.*
import kotlinx.android.synthetic.main.activity_yorumesi.*
import org.jetbrains.anko.startActivity

class Yorumesi : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yorumesi)
        realm = Realm.getDefaultInstance()
        val schedules = realm.where<SY>().findAll()
        yorulistView.adapter = SAyoru(schedules)
        Nameyoru.setOnClickListener { onNamaeyoru(it) }
        HirulistView.setOnItemClickListener { parent, view, position, id ->
            val schedules = parent.getItemAtPosition(position) as SY
            startActivity<SEAyoru>(
                    "schedule_id" to schedules.id
            )
        }
    }
    fun onNamaeyoru(view: View?){
        val intent = Intent(this,SEAyoru::class.java)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
