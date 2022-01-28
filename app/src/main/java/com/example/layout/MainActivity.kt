package com.example.layout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.layout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var edUserName: EditText
    private lateinit var edPassword: EditText
    private lateinit var btnAdd: Button

    private lateinit var edUserName2: EditText
    private lateinit var edPassword2: EditText
    private lateinit var btnAdd2: Button

//    private lateinit var btnView: Button

    private lateinit var sqliteHelper: SQLiteHelper
//    private lateinit var recyclerView: RecyclerView
//    private var adapter: UserAdapter? = null

    private fun clearEditText() {
        edUserName2.setText("")
        edPassword2.setText("")
        edUserName2.requestFocus()
    }

//    private fun initRecyclerView(){
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = UserAdapter()
//        recyclerView.adapter = adapter
//    }
    private fun startMainActivity() {

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LogActivity::class.java))
            finish()
    }, 1000)

    }

    private fun initView(){
        edUserName = findViewById(R.id.edUserName)
        edPassword = findViewById(R.id.edPassword)
        btnAdd = findViewById(R.id.btnAdd)

        edUserName2 = findViewById(R.id.edUserName2)
        edPassword2 = findViewById(R.id.edPassword2)
        btnAdd2 = findViewById(R.id.btnAdd2)



//        btnView = findViewById(R.id.btnView)

//        recyclerView = findViewById(R.id.recyclerView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

//        initRecyclerView()

        sqliteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener  { logUser() }
        btnAdd2.setOnClickListener { addUser() }

//        btnView.setOnClickListener { getUser() }

//        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_maps
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

//    private fun getAmountPost() {
//        val stdList = sqliteHelper.getPost()
//        Log.e("Liczba użytkowników: ", "${stdList.size}")
//
//        adapter?.addItems(stdList)
//    }

    private fun addUser() {
        val username = edUserName2.text.toString()
        val password = edPassword2.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Prosze wpisać wymagane pole ",Toast.LENGTH_SHORT).show()
        } else {
            val std = UserModel(username = username, password = password)
            val status = sqliteHelper.insertUser(std)

            if(status > -1){
                Toast.makeText(this,"Dodawanie użytkownika",Toast.LENGTH_SHORT).show()
                clearEditText()

            } else {
                Toast.makeText(this,"Nie udało się dodać nowego użytkownika",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logUser() {
        val username = edUserName.text.toString()
        val password = edPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Prosze wpisać wymagane pole ", Toast.LENGTH_SHORT).show()
        } else {
            val std = UserModel(username = username, password = password)
            val status = sqliteHelper.logUser(std)
//            Toast.makeText(this,"Status to: "+status,Toast.LENGTH_SHORT).show()
//            Toast.makeText(this,"Status to: "+status.find{ it.id = },Toast.LENGTH_SHORT).show()
            if (status.isEmpty()) {
                Toast.makeText(this, "Nie udało się zalogować", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Zalogowano. Przekierowanie...", Toast.LENGTH_SHORT).show()
                clearEditText()
                startMainActivity()
            }
        }
    }
}
