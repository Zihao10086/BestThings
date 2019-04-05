package org.wit.accountbook.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.accountbook.models.AccountBookJSONStore
import org.wit.accountbook.models.AccountBookMemStore
import org.wit.accountbook.models.AccountBookStore

class MainApp:Application(),AnkoLogger {

    lateinit var records:AccountBookStore

    override fun onCreate() {
        super.onCreate()
        //switch between the database and memory stores
        records = AccountBookJSONStore(applicationContext)//use JSON file
        //records = AccountBookMemStore()  use memory stores
        info("AccountBook started.......")
    }
}