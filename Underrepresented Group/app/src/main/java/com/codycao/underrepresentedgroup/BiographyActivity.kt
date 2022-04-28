package com.codycao.underrepresentedgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codycao.underrepresentedgroup.databinding.ActivityBiographyBinding

class BiographyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiographyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}