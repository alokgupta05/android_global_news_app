package com.personal.paginglibrary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.personal.paginglibrary.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, SearchRepositoriesFragment())
                    .commit()
        }
    }
}