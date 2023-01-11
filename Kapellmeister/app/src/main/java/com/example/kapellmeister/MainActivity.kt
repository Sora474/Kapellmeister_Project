package com.example.kapellmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kapellmeister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        BindingClass.bnvGeneric.selectedItemId = R.id.generic_menu_main_page // Флаг меню по дефолту


    }

    fun bnv_generic_Click (view: View){ // Подгрузга страницы в
        BindingClass.bnvGeneric.setOnItemSelectedListener {
            when(it.itemId){
                R.id.generic_menu_main_page          -> { Toast.makeText(this,"mmp",Toast.LENGTH_SHORT).show() }
                R.id.generic_menu_settings_page      -> { Toast.makeText(this,"msp",Toast.LENGTH_SHORT).show() }
                R.id.generic_menu_library_music_page -> { Toast.makeText(this,"mlm",Toast.LENGTH_SHORT).show() }
            }
            true
        }
    }
}