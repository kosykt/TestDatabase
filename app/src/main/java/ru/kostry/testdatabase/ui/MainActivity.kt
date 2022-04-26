package ru.kostry.testdatabase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kostry.testdatabase.databinding.ActivityMainBinding
import ru.kostry.testdatabase.ui.tablefragment.TableFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, TableFragment.newInstance())
                .commitNow()
        }
    }
}