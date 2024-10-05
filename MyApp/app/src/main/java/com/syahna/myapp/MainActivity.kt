package com.syahna.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    private lateinit var rvFruits: RecyclerView
    private val list = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C5E1A5")))

        rvFruits = findViewById(R.id.rv_fruits)
        rvFruits.setHasFixedSize(true)

        list.addAll(getListFruits())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListFruits(): ArrayList<Fruit> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

        val listFruit = ArrayList<Fruit>()

        for (i in dataName.indices) {
            val photoId = dataPhoto.getResourceId(i, -1)
            val fruit = Fruit(dataName[i], dataDescription[i], photoId)
            listFruit.add(fruit)
        }

        dataPhoto.recycle()

        return listFruit
    }

    private fun showRecyclerList() {
        rvFruits.layoutManager = LinearLayoutManager(this)
        val listFruitAdapter = ListFruitAdapter(list)
        rvFruits.adapter = listFruitAdapter

        listFruitAdapter.setOnItemClickCallback(object : ListFruitAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Fruit) {
                showSelectedFruit(data)
            }
        })
    }

    private fun showSelectedFruit(fruit: Fruit) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key_fruit", fruit)
        startActivity(intent)
    }
}