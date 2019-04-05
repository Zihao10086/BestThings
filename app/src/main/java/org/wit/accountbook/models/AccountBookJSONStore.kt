package org.wit.accountbook.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.accountbook.helpers.exists
import org.wit.accountbook.helpers.read
import org.wit.accountbook.helpers.write
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

//converting a JSON string to a java collection
val JSON_FILE = "accountbook.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<AccountBookModel>>(){}.type

fun generateRandomId():Long{
    return Random().nextLong()
}

class AccountBookJSONStore:AccountBookStore,AnkoLogger {

    val context:Context
    var records = mutableListOf<AccountBookModel>()

    constructor(context: Context){
        this.context = context
        if(exists(context, JSON_FILE)){
            deserialize()
        }
    }

    override fun findAll(): MutableList<AccountBookModel> {
        return records
    }

    override fun create(record: AccountBookModel) {
        record.id = generateRandomId()
        records.add(record)
        serialize()
    }

    override fun update(record: AccountBookModel) {
        val recordsList = findAll() as ArrayList<AccountBookModel>
        var foundRecord:AccountBookModel? = recordsList.find { p->p.id == record.id }
        if(foundRecord!=null){
            foundRecord.type = record.type
            foundRecord.description = record.description
            foundRecord.total = record.total
            foundRecord.date = record.date
            foundRecord.rating = record.rating
            foundRecord.image = record.image
            foundRecord.lat = record.lat
            foundRecord.lng = record.lng
            foundRecord.zoom = record.zoom
        }
        serialize()
    }

    override fun delete(record: AccountBookModel) {
        records.remove(record)
        serialize()
    }

    private fun serialize(){
        val jsonString = gsonBuilder.toJson(records,listType)
        write(context, JSON_FILE,jsonString)
    }

    private fun deserialize(){
        val jsonString = read(context, JSON_FILE)
        records = Gson().fromJson(jsonString,listType)
    }
}