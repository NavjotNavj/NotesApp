package com.example.navjotsingh.notesapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import java.lang.Exception

class AddNotes : AppCompatActivity() {
val dbTable = "Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


      try {
          var bundle : Bundle= intent.extras
          id = bundle.getInt("ID", 0)

          if (id != 0) {
              etTitle.setText(bundle.getString("name").toString())
              etDesc.setText(bundle.getString("des").toString())
          }
      } catch (ex: Exception) {

      }



    }

    fun buAdd(view: View) {
        var dbManager = DbManager(this)
        var values = ContentValues()
        values.put ("Title" , etTitle.text.toString())
        values.put ("Description", etDesc.text.toString())



        if(id==0) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this, "note is added ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "cannot add note", Toast.LENGTH_LONG).show()

            }
        } else {
            var selectionArs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?" ,selectionArs )
            if (ID > 0) {
                Toast.makeText(this, "note is added ", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "cannot add note", Toast.LENGTH_LONG).show()

            }

        }

    }
}
