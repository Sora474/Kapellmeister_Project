package com.example.kapellmeister

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kapellmeister.Adapters.SoundAdapter
import com.example.kapellmeister.Pages.AuthorPage
import com.example.kapellmeister.Pages.FavoritePage
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var BindingClass : ActivityMainBinding
    lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        ////////////////////    For left_menu
        BindingClass.nvGeneric.setNavigationItemSelectedListener{
        when(it.itemId){
                    R.id.generic_left_menu_list_page       -> {
                        Toast.makeText(this, "LMenu !", Toast.LENGTH_SHORT).show()
                        BindingClass.root.close()
                    }
                }
                true
            }

        ////////////////////    For bottom_menu
        BindingClass.bnvGeneric.setOnItemSelectedListener /*Подгрузга страниц в Frame*/ {
            when(it.itemId){
                R.id.generic_bottom_menu_list_page          -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, ListPage())
                        .commit()
                }
                R.id.generic_bottom_menu_author_page        -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, AuthorPage())
                        .commit()
                }
                R.id.generic_bottom_menu_favorite_page -> {
                    supportFragmentManager
                        .beginTransaction().replace(R.id.fl_generic, FavoritePage())
                        .commit()
                }
            }

            true
        }
        BindingClass.bnvGeneric.selectedItemId = R.id.generic_bottom_menu_list_page // Флаг bottom_menu по дефолту
    }

    private fun RequestRuntimePermission(requestCode: Int) /* Запрос на разрешение использования */ {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) /* Обработчик запросов */ {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11) /* Память */ {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Доступ к памяти получен", Toast.LENGTH_SHORT).show()
            }
            else ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean /* Обработчик подтверждения нажатия в left_menu */{
            if(toogle.onOptionsItemSelected(item)){
                true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeLayout(){
        installSplashScreen()   // Подгрузка SplashScreen
        RequestRuntimePermission(11)    // Запрос на доступ к памяти

        BindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        ////////////////////    For left_menu
        toogle = ActionBarDrawerToggle(this, BindingClass.root , R.string.open, R.string.close)
        BindingClass.root.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}