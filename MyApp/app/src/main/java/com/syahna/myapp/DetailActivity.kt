package com.syahna.myapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import android.content.Intent
import android.widget.Toast

class DetailActivity : AppCompatActivity() {

    private var dataFruit: Fruit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dataFruit = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Fruit>("key_fruit", Fruit::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Fruit>("key_fruit")
        }
        Log.d("DetailActivity", "dataFruit: $dataFruit")

        dataFruit?.let { fruit ->

            val nameTextView: TextView = findViewById(R.id.tv_fruit_name)
            val descriptionTextView: TextView = findViewById(R.id.tv_fruit_description)
            val photoImageView: ImageView = findViewById(R.id.img_fruit_photo)

            nameTextView.text = fruit.name
            descriptionTextView.text = fruit.description

            Glide.with(this)
                .load(fruit.photo)
                .into(photoImageView)
        } ?: run {
            Toast.makeText(this, "Data buah tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("DetailActivity", "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareFruitDetails()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareFruitDetails() {
        dataFruit?.let { fruit ->
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Lihat Buah Ini!")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Nama Buah: ${fruit.name}\nDeskripsi: ${fruit.description}"
                )
            }

            startActivity(Intent.createChooser(shareIntent, "Bagikan buah ini via"))
        } ?: run {
            Toast.makeText(this, "Data buah tidak tersedia", Toast.LENGTH_SHORT).show()
        }
    }
}