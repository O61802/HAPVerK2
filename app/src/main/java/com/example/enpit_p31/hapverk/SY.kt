package com.example.enpit_p31.hapverk

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SY : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var day2: Date = Date()
    var mei2: String = ""
    var detailyorugohan: String = ""
}