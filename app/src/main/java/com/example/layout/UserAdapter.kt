package com.example.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
//
//    private var stdList: ArrayList<UserModel> = ArrayList()
//
//    fun  addItems(items: ArrayList<UserModel>){
//        this.stdList = items
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent,false))
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val std = stdList[position]
//        holder.bindView(std)
//    }
//
//    override fun getItemCount(): Int {
//        return stdList.size
//    }
//
//    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        private var ID = view.findViewById<TextView>(R.id.tvId)
//        private var username = view.findViewById<TextView>(R.id.tvUsername)
//        private var password = view.findViewById<TextView>(R.id.tvPassword)
//        private var btnDelete = view.findViewById<Button>(R.id.btnDelete)
//
//        fun bindView(std: UserModel) {
//            ID.text = std.ID.toString()
//            username.text = std.username
//            password.text = std.password
//        }
//
//    }
//}