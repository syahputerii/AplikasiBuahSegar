package com.syahna.myapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.util.Log


class ListFruitAdapter(private val listFruit: ArrayList<Fruit>) : RecyclerView.Adapter<ListFruitAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_fruit, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val fruit = listFruit[position]

        holder.imgPhoto.setImageDrawable(null)

        Glide.with(holder.itemView.context)
            .load(fruit.photo)
            .into(holder.imgPhoto)

        holder.tvName.text = fruit.name
        holder.tvDescription.text = fruit.description

        holder.itemView.setOnClickListener {
            Log.d("RecyclerView", "Item clicked: ${fruit.name}")

            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_fruit", fruit)
            holder.itemView.context.startActivity(intentDetail)
        }
    }


    override fun getItemCount(): Int = listFruit.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Fruit)
    }
}