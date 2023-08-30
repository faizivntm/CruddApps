package com.example.crudapps.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapps.R
import com.example.crudapps.databinding.FragmentHomeBinding
import com.example.crudapps.firebases.adapters.ListAdapter
import com.example.crudapps.ui.add.AddActivity
import com.example.crudapps.utils.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kennyc.view.MultiStateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.homeRecycleView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        //  RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Initialize and set up your RecyclerView adapter here
        val adapter = ListAdapter()
        recyclerView.adapter = adapter

        viewModel.getBook().observe(
            this, {
                when(it){
                    is Result.Loading -> {
                        binding.multiStateView.viewState = MultiStateView.ViewState.LOADING
                    }
                    is Result.Success ->{
                        if(it.data.isNotEmpty()){
                            binding.apply {
                                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                                val content = multiStateView.getView(MultiStateView.ViewState.CONTENT)
                                if (content != null){
                                    val layoutManager = LinearLayoutManager(this@HomeActivity)
                                    recyclerView.adapter = adapter
                                    recyclerView.layoutManager = layoutManager
                                    recyclerView.setHasFixedSize(true)
                                }

                            }
                            adapter.updateData(it.data)
                        }else{
                            binding.multiStateView.viewState = MultiStateView.ViewState.EMPTY

                        }

                    }
                    is Result.Error -> {
                        binding.multiStateView.viewState = MultiStateView.ViewState.ERROR
                    }

                }
            }
        )

        floatingActionButton.setOnClickListener {
            //pindah ke halaman AddActivity
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

    }

}
