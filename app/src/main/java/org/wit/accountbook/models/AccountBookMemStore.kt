package org.wit.accountbook.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId():Long{
    return lastId++
}

//A simple encapsulation of our records list
class AccountBookMemStore: AccountBookStore,AnkoLogger {
    val records = ArrayList<AccountBookModel>()

    //all the functions
    override fun findAll(): List<AccountBookModel> {
        return records
    }

    override fun create(record: AccountBookModel) {
        record.id = getId()
        records.add(record)
        logAll()
    }

    //refresh the values of the record that i changed
    override fun update(record: AccountBookModel) {
        var foundAccountBook:AccountBookModel? = records.find { p->p.id == record.id }
        if(foundAccountBook != null){
            foundAccountBook.type = record.type
            foundAccountBook.description = record.description
            foundAccountBook.date = record.date
            foundAccountBook.rating = record.rating
            foundAccountBook.total = record.total
            foundAccountBook.image = record.image
            foundAccountBook.lat = record.lat
            foundAccountBook.lng = record.lng
            foundAccountBook.zoom = record.zoom
            logAll()
        }
    }

    override fun delete(record: AccountBookModel) {
        records.remove(record)
    }

    fun logAll(){
        records.forEach{info("${it}")}
    }
}