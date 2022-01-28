package com.example.layout

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class  SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "projekt.db"
        private const val TABLE_NAME = "users"
        private const val TABLE_COLUMN1 = "username"
        private const val TABLE_COLUMN2 = "password"
        private const val ID = "ID"
        private const val POST_TABLE = "post"
        private const val POST_COLUMN1 = "title"
        private const val POST_COLUMN2 = "description"
        private const val POST_COLUMN3 = "image"
        private const val POST_COLUMN4 = "user_id"
        private const val POST_COLUMN5 = "latitude"
        private const val POST_COLUMN6 = "longtitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + TABLE_COLUMN1 + " TEXT," + TABLE_COLUMN2 + " TEXT)")
        db?.execSQL(createTable)

        val createPost = ("CREATE TABLE "  + POST_TABLE + "(" + ID + " INTEGER PRIMARY KEY," + POST_COLUMN1 + " TEXT," + POST_COLUMN2 + " TEXT," + POST_COLUMN3 +" BLOB," + POST_COLUMN4 +" INTEGER," + POST_COLUMN5+" REAL,"+ POST_COLUMN6+" REAL)")
        db?.execSQL(createPost)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

        if (db != null) db.execSQL("DROP TABLE IF EXISTS $POST_TABLE") else throw NullPointerException("Expression 'db' must not be null")
        onCreate(db)
    }

    fun insertUser(std:UserModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.ID)
        contentValues.put(TABLE_COLUMN1, std.username)
        contentValues.put(TABLE_COLUMN2, std.password)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun insertPost(std:PostModel): Long{
        val db = this.writableDatabase
//        val sql = "INSERT INTO $POST_TABLE($ID,$POST_COLUMN1,$POST_COLUMN2,$POST_COLUMN3) VALUES(?,?,?,?)"
//        val statement = db.compileStatement(sql)
//
//        statement.b

//        val blobAsBytes: ByteArray = std.image.getBytes(1, std.image.length().toInt())

        val contentValues2 = ContentValues()
        contentValues2.put(ID, std.ID)
        contentValues2.put(POST_COLUMN1, std.title)
        contentValues2.put(POST_COLUMN2, std.description)
        contentValues2.put(POST_COLUMN5, std.latitude)
        contentValues2.put(POST_COLUMN6, std.longtitude)
        contentValues2.put(POST_COLUMN3, std.image)

        val success = db.insert(POST_TABLE, null, contentValues2)
        db.close()
        return success
    }

    fun getPost(): ArrayList<PostModel>{
        val stdList: ArrayList<PostModel> = ArrayList()
        val selectQuery = "SELECT * FROM $POST_TABLE ORDER BY $POST_COLUMN1 ASC"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }
        var ID:Int
        var title:String
        var description:String
        var latitude: Double
        var longtitude: Double
        var image:ByteArray

        if(cursor.moveToFirst()){
            do{
                ID = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"))
                longtitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longtitude"))
                image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"))

                val std = PostModel(ID, title, description,latitude,longtitude,image)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList

    }

    fun selectMaps(googleMap: GoogleMap): ArrayList<MapsModel>{
        var mMap = googleMap

        val stdList: ArrayList<MapsModel> = ArrayList()
        val selectQuery = "SELECT * FROM $POST_TABLE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }
        var title:String
        var latitude: Double
        var longtitude: Double

        if(cursor.moveToFirst()){
            do{
//                lateinit var mMap: GoogleMap
                title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"))
                longtitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longtitude"))
                mMap.addMarker(MarkerOptions().position(LatLng(latitude,longtitude)).title(title))

                val std = MapsModel(title,latitude,longtitude)
                stdList.add(std)
//                fun onMapReady(googleMap: GoogleMap) {
//                    mMap = googleMap
//                    mMap.addMarker(MarkerOptions().position(LatLng(latitude, longtitude)).title(title))
//                }
            } while (cursor.moveToNext())
        }
        return stdList

    }






    fun logUser(std:UserModel): ArrayList<UserModel>{
        val stdList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $TABLE_COLUMN1 = ? AND $TABLE_COLUMN2 = ?"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, arrayOf(std.username,std.password))
        } catch (e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }
        var ID:Int
        var username:String
        var password:String

        if(cursor.moveToFirst()){
            do{
                ID = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))

                val std = UserModel(ID, username, password)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList

    }

    fun deleteStudentById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,id)

        val success = db.delete(POST_TABLE, "id=$id", null)
        db.close()
        return success
    }
}