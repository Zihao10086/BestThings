package org.wit.accountbook.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.abc_activity_chooser_view.*
import kotlinx.android.synthetic.main.activity_account_book.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*
import org.wit.accountbook.R
import org.wit.accountbook.helpers.readImage
import org.wit.accountbook.helpers.readImageFromPath
import org.wit.accountbook.helpers.showImagePicker
import org.wit.accountbook.main.MainApp
import org.wit.accountbook.models.AccountBookModel
import org.wit.accountbook.models.Location
import java.util.*

class AccountBookActivity : AppCompatActivity(),AnkoLogger {

    //halo
    var record = AccountBookModel()
    lateinit var app:MainApp //Null Safety in action, we don't need to initialise it at first because 'lateinit'
    var edit = false//the flag of updating
    val IMAGE_REQUEST = 1//choose the image
    val LOCATION_REQUEST = 2//the flag of location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_book)//type the details of New record in this activity
        app = application as MainApp

        toolbarAdd.title = title //in order to show the title of the toolbar
        setSupportActionBar(toolbarAdd)//support the toolbar

        //pass the selected record to AccountBookActivity
        if (intent.hasExtra("accountbook_edit")){
            edit = true
            record = intent.extras.getParcelable<AccountBookModel>("accountbook_edit")
            accountType.setText(record.type)
            description.setText(record.description)
            total.setText(record.total)
            date.setText(record.date)
            rating_bar.rating = record.rating
            //change the content of the add button to Save Record
            btnAdd.setText(R.string.save_record)
            //add the image to the record
            Image.setImageBitmap(readImageFromPath(this,record.image))
            if(record!=null){
                //change the content of the add image button when we change the image record
                chooseImage.setText(R.string.change_image)
            }
        }

        btnAdd.setOnClickListener(){
            record.type = accountType.text.toString()
            record.description = description.text.toString()
            record.total = total.text.toString()
            record.date = date.text.toString()
            record.rating = rating_bar.rating
            if(record.type.isEmpty()){
                toast(R.string.enter_accountbook_title)
            }else {
                if (edit){
                    app.records.update(record.copy())
                }
                else{
                    app.records.create(record.copy())
                }
            }
            info("Add Button Pressed:$record")
            //I run it by the function of logAll() in AccountBookMemStore
            //app.records.findAll().forEach{info("add Button Pressed:${it}")}
            setResult(AppCompatActivity.RESULT_OK)
            finish()//return to the last activity
        }

        //the function of choosing image
        chooseImage.setOnClickListener{
            showImagePicker(this,IMAGE_REQUEST)
        }

        //the function of setting location
        accountbookLocation.setOnClickListener{
            var location = Location(52.245696,-7.139102,15f)//default location
            if (record.zoom!=0f){
                location.lat = record.lat
                location.lng = record.lng
                location .zoom = record.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location",location),LOCATION_REQUEST)
        }


        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        date.setRawInputType(InputType.TYPE_NULL)
        //button click to show DatePickerDialog
        date.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                //set to edit text
                date.setText(""+mYear+"-"+(mMonth+1)+"-"+mDay)
            },year,month,day)
            //show dialog
            dpd.show()
        }
    }

    //inflate the menu 'cancel'
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cancel,menu)
        if (edit&&menu!=null)menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    //handle the event of Cancel
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_delete->{
                app.records.delete(record)
                finish()
            }
            R.id.item_cancel->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //put the image that you choosed into the accountbook activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            IMAGE_REQUEST->{
                if(data!=null){
                    record.image = data.getData().toString()
                    Image.setImageBitmap(readImage(this,resultCode,data))
                    chooseImage.setText(R.string.change_image)
                }
            }
            LOCATION_REQUEST->{
                if (data != null){
                    val location = data.extras.getParcelable<Location>("location")
                    record.lat = location.lat
                    record.lng = location.lng
                    record.zoom = location.zoom
                }
            }
        }
    }
}
