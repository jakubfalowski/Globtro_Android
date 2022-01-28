package com.example.layout

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var stdListPost: ArrayList<PostModel> = ArrayList()
    private var onClickDeleteItem: ((PostModel) -> Unit)? = null

    fun  addPost(items: ArrayList<PostModel>){
        this.stdListPost = items
        notifyDataSetChanged()
    }

    fun setOnClickDeleteItem(callback: (PostModel)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent,false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val std = stdListPost[position]
        holder.bindView(std)
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdListPost.size
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ID = view.findViewById<TextView>(R.id.parameterId)
        private var title = view.findViewById<TextView>(R.id.parameter1)
        private var description = view.findViewById<TextView>(R.id.parameter2)
        private var image = view.findViewById<ImageView>(R.id.imageView2)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: PostModel) {
            ID.text = std.ID.toString()
            title.text = std.title
            description.text = std.description
            image.setImageBitmap(BitmapFactory.decodeByteArray(std.image, 0, std.image.size))
        }

    }
}