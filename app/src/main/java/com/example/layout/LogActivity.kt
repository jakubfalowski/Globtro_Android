package com.example.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LogActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: PostAdapter? = null
    private lateinit var btnToAdd: Button
    private lateinit var btnAdd3: Button
//    private lateinit var btnDelete: Button
    private lateinit var sqliteHelper: SQLiteHelper

    private fun getAmountPost() {
        val stdListPost = sqliteHelper.getPost()
        Log.e("Liczba użytkowników: ", "${stdListPost.size}")

        adapter?.addPost(stdListPost)
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter()
        recyclerView.adapter = adapter
    }
    private fun initView(){
        recyclerView = findViewById(R.id.recyclerView)
        btnToAdd = findViewById(R.id.btnToAdd)
        btnAdd3 = findViewById(R.id.btnAdd3)
//        btnDelete = findViewById(R.id.btnDelete)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)
        getAmountPost()

        btnAdd3.setOnClickListener{
            var nowaaktywnosc: Intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(nowaaktywnosc)
        }

        btnToAdd.setOnClickListener{
            var nowaaktywnosc: Intent = Intent(applicationContext, AddActivity::class.java)
            startActivity(nowaaktywnosc)
        }

        adapter?.setOnClickDeleteItem {
            deletePost((it.ID))
        }
    }

    private fun deletePost(id:Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Czy na pewno chcesz usunąć ten post?")
        builder.setCancelable(true)

        builder.setPositiveButton("TAK"){
            dialog,_ ->
            sqliteHelper.deleteStudentById(id)
            getAmountPost()
            dialog.dismiss()
        }

        builder.setNegativeButton("NIE"){
            dialog,_ -> dialog.dismiss()
        }

        var alert = builder.create()
        alert.show()

    }
}