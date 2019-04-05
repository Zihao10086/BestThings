package org.wit.accountbook.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivityForResult
import org.wit.accountbook.R

class AccountBookWelcome:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        toolbarW.title = title//show the title of toolbar

        //handle the event of clicking the button (jump to the activity of AccountBookList)
        welcome.setOnClickListener(){
            startActivityForResult<AccountBookListActivity>(0)
        }
    }
}