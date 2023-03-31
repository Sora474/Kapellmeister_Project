package com.example.kapellmeister


import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Pages.AuthorPage
import com.example.kapellmeister.Pages.FavoritePage
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var BindingClass : ActivityMainBinding
    lateinit var toogle : ActionBarDrawerToggle
    companion object{
        lateinit var sound_list: ArrayList<SoundModel>
        var sound_position: Int = 0
        var isPlaing      : Boolean = false
        var isShuffle     : Boolean = false
        var isRepeat      : Int = 0

        var soundService: SoundService? = null
    }

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
        BindingClass.bnvGeneric.setOnItemSelectedListener /* Подгрузга страниц в Frame */ {
            when(it.itemId){
                R.id.generic_bottom_menu_list_page          -> {
                    RequestRuntimePermission(11)
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
    private fun initializeLayout(){
        installSplashScreen()   // Подгрузка SplashScreen

        BindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(BindingClass.root)

        ////////////////////    For left_menu
        toogle = ActionBarDrawerToggle(this, BindingClass.root , R.string.open, R.string.close)
        BindingClass.root.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun RequestRuntimePermission(requestCode: Int) /* Запрос на разрешение использования */ {
        when(requestCode){
            11 /* Чтение внутренних файлов */ -> {
                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) /* Обработчик запросов */ {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            11 /* Чтение внутренних файлов */ -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.notification_reed_storage_true), Toast.LENGTH_SHORT).show()
                }
                else ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),11)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean /* Обработчик подтверждения нажатия в left_menu */{
            if(toogle.onOptionsItemSelected(item)){
                true
        }
        return super.onOptionsItemSelected(item)
    }
}

