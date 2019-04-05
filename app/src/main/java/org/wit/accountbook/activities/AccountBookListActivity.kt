package org.wit.accountbook.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import kotlinx.android.synthetic.main.activity_account_book.*
import kotlinx.android.synthetic.main.activity_account_book.view.*
import kotlinx.android.synthetic.main.activity_accountbook_list.*
import kotlinx.android.synthetic.main.card_accountbook.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.accountbook.R
import org.wit.accountbook.main.MainApp
import org.wit.accountbook.models.AccountBookModel
import org.wit.accountbook.activities.AccountBookAdapter

class AccountBookListActivity :AppCompatActivity(),AccountBookListener{

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountbook_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager//RecyclerView
        loadRecords()//Persistence (calling the loadRecords())

        toolbarMain.title = title//show the title of toolbar
        setSupportActionBar(toolbarMain)//support the  toolbar
    }

    //pass the selected record to AccountBookActivity
    override fun onAccountBookClick(record: AccountBookModel) {
        startActivityForResult(intentFor<AccountBookActivity>().putExtra("accountbook_edit",record),0)
    }

    //inflate the menu 'add'
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //handle the event
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.record_add->startActivityForResult<AccountBookActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    //another lifecycle event, to be triggered when an activity we started finishes
    //refresh the record that was changed
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadRecords()//Persistence
        super.onActivityResult(requestCode, resultCode, data)
    }

    //Persistence (Storing the record in a JSON file)
    private fun loadRecords(){
        showRecords(app.records.findAll())
    }
    fun showRecords(records:List<AccountBookModel>){
        recyclerView.adapter = AccountBookAdapter(records,this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}


