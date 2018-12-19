package com.example.enpit_p31.hapverk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SK : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var year: Date = Date()
    var keiyakusya: String = ""
    var syusin: String = ""
    var tannzyou: Date = Date()
    var kazoku: String = ""
    var tuiki: String = ""
}