package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_baital.*
import org.jetbrains.anko.startActivity

class Baital : AppCompatActivity() {
    private  lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baital)
        realm = Realm.getDefaultInstance()
        val scheduleB =realm.where<SB>().findAll()
        BaitallistView.adapter = SAbaitarub(scheduleB)
        nyuuryokuB.setOnClickListener { onNamaeB(it) }
        modoruB.setOnClickListener { finish() }
        BaitallistView.setOnItemClickListener { parent, view, position, id ->
            val scheduleB = parent.getItemIdAtPosition(position) as SB
            startActivity<SEAbaital>("scheduleB_id" to scheduleB.id)

        }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        realm.close()
    }
    fun onNamaeB(view: View){
        val intent = Intent(this,SEAbaital::class.java)
        startActivity(intent)
    }

}
