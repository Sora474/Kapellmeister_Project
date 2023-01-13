package com.example.kapellmeister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.kapellmeister.Pages.AuthorPage
import com.example.kapellmeister.Pages.LibraryMusicPage
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BindingClass = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Kapellmeister)
        setContentView(BindingClass.root)

        BindingClass.bnvGeneric.selectedItemId = R.id.generic_menu_list_page // Флаг меню по дефолту

        BindingClass.bnvGeneric.setOnItemSelectedListener {
            when(it.itemId){
                R.id.generic_menu_list_page          -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, ListPage())
                        .commit()
                }
                R.id.generic_menu_author_page        -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, AuthorPage())
                        .commit()
                }
                R.id.generic_menu_library_music_page -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, LibraryMusicPage())
                        .commit()
                }
            }
            true
        }
    }

    ///////////////// Не Menu

    fun bnv_generic_Click (menu: Menu){ // Подгрузга страниц в Frame
        BindingClass.bnvGeneric.setOnItemSelectedListener {
            when(it.itemId){
                R.id.generic_menu_list_page          -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, ListPage())
                        .commit()
                }
                R.id.generic_menu_author_page        -> {
                    Toast.makeText(this,"2",Toast.LENGTH_SHORT).show()
                }
                R.id.generic_menu_library_music_page -> {
                    Toast.makeText(this,"3",Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}