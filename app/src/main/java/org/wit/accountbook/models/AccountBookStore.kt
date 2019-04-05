package org.wit.accountbook.models

interface AccountBookStore {
    fun findAll():List<AccountBookModel>
    fun create(record:AccountBookModel)
    fun update(record: AccountBookModel)
    fun delete(record: AccountBookModel)
}