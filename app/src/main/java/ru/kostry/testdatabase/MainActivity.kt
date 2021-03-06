package ru.kostry.testdatabase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kostry.testdatabase.db.AppDatabase
import ru.kostry.testdatabase.db.model.*

class MainActivity : AppCompatActivity() {

    private val db = AppDatabase
    private val firstEntity = FirstEntity(
        id = 1,
        text = "text",
        stringList = listOf("q", "w", "r")
    )
    private val secondEntity = SecondEntity(
        id = 1,
        box = Box(name = "test box")
    )
    private val thirdEntity = ThirdEntity(
        id = 1,
        myPackages = listOf(
            MyPackage("package_1"),
            MyPackage("package_2")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            db.instance.firstDao.insert(firstEntity)
            val a = db.instance.firstDao.getAll()
            Log.d("testRoom firstEntity", a[0].stringList[1])

            db.instance.secondDao.insert(secondEntity)
            val b = db.instance.secondDao.getAll()
            Log.d("testRoom secondEntity", b[0].box.name)

            db.instance.thirdDao.insert(thirdEntity)
            val c = db.instance.thirdDao.getAll()
            Log.d("testRoom_thirdEntity", c[0].myPackages[1].text)
        }
    }
}