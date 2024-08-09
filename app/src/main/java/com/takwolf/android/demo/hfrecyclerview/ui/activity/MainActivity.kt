package com.takwolf.android.demo.hfrecyclerview.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.hfrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLinearVertical.setOnClickListener {
            startActivity(Intent(this, LinearVerticalActivity::class.java))
        }

        binding.btnLinearHorizontal.setOnClickListener {
            startActivity(Intent(this, LinearHorizontalActivity::class.java))
        }

        binding.btnGridVertical.setOnClickListener {
            startActivity(Intent(this, GridVerticalActivity::class.java))
        }

        binding.btnGridHorizontal.setOnClickListener {
            startActivity(Intent(this, GridHorizontalActivity::class.java))
        }

        binding.btnStaggeredVertical.setOnClickListener {
            startActivity(Intent(this, StaggeredVerticalActivity::class.java))
        }

        binding.btnStaggeredHorizontal.setOnClickListener {
            startActivity(Intent(this, StaggeredHorizontalActivity::class.java))
        }

        binding.btnRefreshLinear.setOnClickListener {
            startActivity(Intent(this, RefreshLinearActivity::class.java))
        }

        binding.btnRefreshGrid.setOnClickListener {
            startActivity(Intent(this, RefreshGridActivity::class.java))
        }

        binding.btnRefreshStaggered.setOnClickListener {
            startActivity(Intent(this, RefreshStaggeredActivity::class.java))
        }

        binding.btnMultiRecyclerView.setOnClickListener {
            startActivity(Intent(this, MultiRecyclerViewActivity::class.java))
        }
    }
}
