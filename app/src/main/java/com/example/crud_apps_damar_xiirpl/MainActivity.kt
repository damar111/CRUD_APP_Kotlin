package com.example.crud_apps_damar_xiirpl

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.crud_apps_damar_xiirpl.helper.DBHelper
import com.google.android.material.textfield.TextInputEditText
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var inputName : TextInputEditText
    lateinit var inputAge : TextInputEditText
    lateinit var btnAdd : Button
    lateinit var btnPrint : Button
    lateinit var textName: TextView
    lateinit var textAge : TextView
    lateinit var textId : TextView
    var progressDialog : ProgressDialog? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputName = findViewById(R.id.input_name)
        inputAge = findViewById(R.id.input_age)
        btnAdd = findViewById(R.id.btnadd)
        btnPrint = findViewById(R.id.btnprint)
        textName = findViewById(R.id.name)
        textAge = findViewById(R.id.age)
        textId = findViewById(R.id.id)

        btnAdd.setOnClickListener{
            val db = DBHelper(this, null)
            val name = inputName.text.toString()
            val age = inputAge.text.toString()
            val id = textId.text.toString()

            db.addProfile(name,age)

            inputAge.text!!.clear()
            inputName.text!!.clear()
        }

        btnPrint.setOnClickListener{
            loadData()
        }
        loadData()
    }

    @SuppressLint("Range")
    fun loadData(){
        val db = DBHelper(this, null)
        val cursor = db.getProfile()

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setMessage("Tunggu sebentar... Data akan muncul")
        progressDialog!!.max = 100
        progressDialog!!.progress = 1
        progressDialog!!.show()

        Thread(Runnable {
            try {
                Thread.sleep(1000)
            }catch (e:Exception){
                e.printStackTrace()
            }
            progressDialog!!.dismiss()
        }).start()

        if (cursor!!.moveToFirst()) {
            textName.text = "Name\n"
            textAge.text = "Age\n"
            textId.append(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)) + "\n")
            textName.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
            textAge.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
        }

        if (cursor.moveToNext()) {
            while (cursor.moveToNext()) {
                textId.append(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL)) + "\n")
                textName.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
                textAge.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
            }
        }
        cursor.close()
    }
}