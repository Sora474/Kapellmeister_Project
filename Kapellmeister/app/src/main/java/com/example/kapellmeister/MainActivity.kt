package com.example.kapellmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kapellmeister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)
    }
}