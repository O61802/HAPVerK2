package com.example.enpit_p31.hapverk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_hirumesi.*
import org.jetbrains.anko.startActivity

class Hirumesi : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hirumesi)
        realm = Realm.getDefaultInstance()
        val scheduleshiru = realm.where<SH>().findAll()
        HirulistView.adapter = SAHiru(scheduleshiru)
        Namaehiru.setOnClickListener { onNamaeHiru(it) }
        modoruHiru.setOnClickListener { finish() }
        HirulistView.setOnItemClickListener { parent, view, position, id ->
            val scheduleshiru = parent.getItemAtPosition(position) as SH
            startActivity<SEAhiru>(
                    "schedulehiru_id" to scheduleshiru.id
            )
        }

    }

    fun onNamaeHiru(view: View?){
        val intent = Intent(this,SEAhiru::class.java)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
