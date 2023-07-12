package com.example.dogimagesharing

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

import android.widget.ProgressBar

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.dogimagesharing.R

class MainActivity : AppCompatActivity() {
    private var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImage()
    }

    private fun loadImage() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        currentImageUrl = "https://dog.ceo/api/breeds/image/random"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageUrl, null,
            { response ->
                currentImageUrl = response.getString("message")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        return false
                    }
                }).into(findViewById<ImageView>(R.id.dogImageView))
            },
            {}
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareImage(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Check this adorable picture I got from Sahil $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this picture by Sahil using...")
        startActivity(chooser)
    }

    fun skipImage(view: View) {
        loadImage()
    }
}
