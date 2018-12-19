package com.example.enpit_p31.hapverk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SH : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var day: Date = Date()
    var mei: String = ""
    var detailHil: String = ""
}