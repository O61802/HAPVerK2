package com.example.enpit_p31.hapverk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SB :RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var monce: Date = Date()
    var hikaigosya: String = ""
    var genki: String = ""
    var suiminn: String = ""
    var syokuzi: String =""
    var haisetu: String = ""
}