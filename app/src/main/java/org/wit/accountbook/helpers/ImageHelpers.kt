package org.wit.accountbook.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewParent
import org.wit.accountbook.R
import java.io.IOException

//encapsulating the Content Provider in this helper class

//the function of choose the image of what you bought
fun showImagePicker(parent: Activity, id:Int){
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_image.toString())
    parent.startActivityForResult(chooser,id)
}

//bitmap is a type for storing the content of image
fun readImage(activity:Activity,resultCode:Int,data:Intent?): Bitmap?{
    var bitmap:Bitmap?=null
    if(resultCode == Activity.RESULT_OK && data != null &&data.data!=null){
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver,data.data)
        }catch(e:IOException){
            e.printStackTrace()
        }
    }
    return bitmap
}


//pass the path of the image to the accountBook activity
//and we can see the image if we selected the record
fun readImageFromPath(context: Context, path:String):Bitmap?{
    var bitmap:Bitmap?=null
    val uri = Uri.parse(path)
    if(uri != null){
        try {
            val parcelFileDescriptior = context.getContentResolver().openFileDescriptor(uri,"r")
            val fileDescriptor = parcelFileDescriptior.getFileDescriptor()
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptior.close()
        }catch (e:Exception){
        }
    }
    return bitmap
}