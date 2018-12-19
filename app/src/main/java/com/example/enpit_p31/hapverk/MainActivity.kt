package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asa.setOnClickListener { onAsamesi(it) }
        hiru.setOnClickListener{onHirumesi(it)}
        yoru.setOnClickListener { onYorumesi(it) }
        kojin.setOnClickListener { onKojin(it) }
        baital.setOnClickListener { onBaital(it) }
    }
    fun onAsamesi(view: View?){
        val intent=Intent(this, Asamesi::class.java)
        startActivity(intent)
    }
    fun onHirumesi(view: View?){
        val intent=Intent(this,Hirumesi::class.java)
        startActivity(intent)
    }
    fun onYorumesi(view: View?){
        val intent=Intent(this,Yorumesi::class.java)
        startActivity(intent)
    }
    fun onKojin(view: View?){
        val intent=Intent(this,Kojin::class.java)
        startActivity(intent)
    }
    fun onBaital(view: View?){
        val intent=Intent(this,Baital::class.java)
        startActivity(intent)
    }
}
