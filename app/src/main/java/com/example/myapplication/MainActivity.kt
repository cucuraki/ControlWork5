package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.OuterAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val model: MyViewModel by viewModels()
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private val adapter by lazy {
        OuterAdapter().apply {
            setTextListener{text, index->
                model.textChangedListener(text, index)
            }
        }
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        collect()
        setOnBtnClick()
    }

    private fun collect() {
        lifecycleScope.launch(mainDispatcher) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.flow.collect {
                    adapter.setData(it)
                    initRecycler()
                }
            }
        }
    }

    private fun initRecycler(){
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }
    private fun setOnBtnClick(){
        binding.btn.setOnClickListener{
            if(!model.check()){
                Toast.makeText(this, "Some required fields are empty", Toast.LENGTH_SHORT).show()
            }else Toast.makeText(this, "You are registered", Toast.LENGTH_SHORT).show()
        }
    }

}