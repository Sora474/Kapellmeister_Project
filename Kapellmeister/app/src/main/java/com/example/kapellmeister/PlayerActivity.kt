package com.example.kapellmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kapellmeister.databinding.ActivityMainBinding
import com.example.kapellmeister.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BindingClass = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)
    }
}