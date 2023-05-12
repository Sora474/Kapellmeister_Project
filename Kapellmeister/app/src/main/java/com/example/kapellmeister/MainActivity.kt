package com.example.kapellmeister


import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.graphics.red
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kapellmeister.Datas.SoundModel
import com.example.kapellmeister.Pages.AuthorPage
import com.example.kapellmeister.Pages.FavoritePage
import com.example.kapellmeister.Pages.ListPage
import com.example.kapellmeister.Services.SoundService
import com.example.kapellmeister.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import kotlin.system.exitProcess

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()
        RequestRuntimePermission(11)
        sound_list = getSoundAll()  //  Получение ПлейЛиста

        ////////////////////    For left_menu
        BindingClass.nvGeneric.setNavigationItemSelectedListener{
        when(it.itemId){
                    R.id.generic_left_menu_exit      -> {   // Обработчик выхода из приложения
                        val builder = MaterialAlertDialogBuilder(this)
                            builder.setTitle(getString(R.string.exit))
                            .setMessage(getString(R.string.exit_question))
                            .setPositiveButton(getString(R.string.yes)){ temp_atribut, _ ->
                                if(soundService != null){
                                    soundService?.stopForeground(true)
                                    soundService?.mediaPlayer?.release()
                                    soundService = null
                                }
                                exitProcess(0)
                            }
                            .setNegativeButton(getString(R.string.no)){ dialog, _ ->
                                dialog.dismiss()
                            }

                        val customDialog = builder.create()
                        customDialog.show()
                        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.ChapmanRed))
                        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.ChapmanRed))
                    }
                }
                true
            }

        ////////////////////    For bottom_menu
        BindingClass.bnvGeneric.setOnItemSelectedListener /* Подгрузга страниц в Frame */ {
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
    override fun onDestroy() {
        super.onDestroy()
        if(isPlaing && soundService != null){
            soundService?.stopForeground(true)
            soundService?.mediaPlayer?.release()
            soundService = null
            exitProcess(1)
        }
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
    private fun getSoundAll() : ArrayList<SoundModel> /* Получение всех аудиофайлов из внешнего хранилища */ {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val temp_list = ArrayList<SoundModel>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )

        //  Регистрация поисковика
        val cursor = this.contentResolver.query(uri , projection, selection,
            null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if (cursor == null){
            Toast.makeText(this,"Something wrong",Toast.LENGTH_SHORT)
        } else if(!cursor.moveToFirst()){
            Toast.makeText(this,"Sound not found",Toast.LENGTH_SHORT)
        } else {
            do {
                //  Присвоение данных аудиофайла
                val idC     = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val nameC   = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))

                val authorC = if(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) == "<unknown>")
                    "Неизвестный исполнитель"
                else  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                val albumC  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val timeC   = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val pathC   = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

                val albumIdC   = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)).toString()
                val uriArt = Uri.parse("content://media/external/audio/albumart")
                val uriArtC = Uri.withAppendedPath(uriArt,albumIdC).toString()

                val sound = SoundModel(idC, nameC, authorC.capitalize(), albumC, timeC, pathC, uriArtC)
                val file = File(sound.path)

                if(file.exists()){
                    temp_list.add(sound)
                }
            }while (cursor.moveToNext())
            cursor.close()
        }
        return temp_list
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
                    // Перезапкск Activity
                    finish()
                    startActivity(intent)
                    overridePendingTransition(0,0)
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

