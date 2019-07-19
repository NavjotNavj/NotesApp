package com.example.navjotsingh.notesapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.EventLogTags
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // listNotes.add(notes(1, "owl" , "jakjkfjefiuwehfnmcsndj"))
       //  listNotes.add(notes(2, "friend" , "jakjkfjefiuwehfnmcsndj"))


        LoadQuery("%")

    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    fun LoadQuery (title:String) {

        var dbManager = DbManager(this)
        val projections = arrayOf ("ID", "Title" , "Description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.Query(projections,"Title like ?",  selectionArgs , "Title")
        listNotes.clear()
        if ( cursor.moveToFirst()){
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title= cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))



                listNotes.add(notes(ID, Title , Description))
            }
                while (cursor.moveToNext())
        }

        var myNotesAdapter = MyNotesAdapter(this,listNotes)
        lvNotes.adapter= myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        val sv = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                LoadQuery("%" + query + "%" )
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId){
                R.id.addNote-> {
                    var intent = Intent(this,AddNotes::class.java)
                    startActivity(intent)

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    inner class MyNotesAdapter : BaseAdapter{
        var listNotesAdapter = ArrayList<notes>()
        var context : Context ?= null
        constructor(context: Context ,listNotesAdapter:ArrayList<notes>) : super(){
            this.listNotesAdapter = listNotesAdapter
            this.context=context

        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticket, null)
            var myNode = listNotesAdapter[p0]
            myView.tvTitle.text = myNode.NodeName
            myView.tvDes.text = myNode.NodeDes
            myView.ivDelete.setOnClickListener( View.OnClickListener {

                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myNode.nodeId.toString())
                dbManager.Delete("ID=?" , selectionArgs)
                LoadQuery("%")
            })

            myView.ivEdit.setOnClickListener( View.OnClickListener {
            GoToUpdate(myNode)

            })
            return myView
        }

        override fun getItem(p0: Int): Any {
           return listNotesAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
           return p0.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }


    }

    fun GoToUpdate (notes: notes) {
        var intent = Intent(this,AddNotes::class.java)
        intent.putExtra("ID", notes.nodeId)
        intent.putExtra("name", notes.NodeName)
        intent.putExtra("des", notes.NodeDes)




        startActivity(intent)
    }


}

