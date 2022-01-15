package com.example.suitmedia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmedia.ui.adapter.UsersAdapter
import com.example.suitmedia.databinding.ActivityMainBinding
import com.example.suitmedia.model.UsersResponse
import com.example.suitmedia.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: UsersAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var totalPage: Int = 1

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        binding?.toolbar?.setNavigationOnClickListener { onBackPressed() }

        layoutManager = LinearLayoutManager(this)
        binding?.swipeRefresh?.setOnRefreshListener(this)
        setupRecyclerView()
        getUsers(false)

        binding?.rvUsers?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                Log.d("MainActivity", "onScrollChange: ")
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total  = adapter.itemCount
                if (!isLoading && page < totalPage){
                    if (visibleItemCount + pastVisibleItem>= total){
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) binding?.progressBar?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            val parameters = HashMap<String, String>()
            parameters["page"] = page.toString()
            Log.d("PAGE", "$page")
            RetrofitClient.instance.getUsers(parameters).enqueue(object : Callback<UsersResponse>{
                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    binding?.progressBar?.visibility = View.GONE
                    isLoading = false
                    binding?.swipeRefresh?.isRefreshing = false
                }

                override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                    totalPage = response.body()?.total_pages!!
                    Log.d("PAGE", "totalPage: $totalPage")
                    val listResponse = response.body()?.data
                    if (listResponse != null){
                        Log.d("PAGE", "listResponse != null")
                        adapter.addList(listResponse)
                    }
                    binding?.progressBar?.visibility = View.GONE
                    isLoading = false
                    binding?.swipeRefresh?.isRefreshing = false
                }
            })
            Log.d("memek", "ini $page")

            if (page == 2){
                binding?.lastPage?.visibility = View.VISIBLE
            }else{
                binding?.lastPage?.visibility = View.GONE
            }
        }, 3000)
    }

    private fun setupRecyclerView() {
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.layoutManager = layoutManager
        adapter = UsersAdapter(this)
        binding?.rvUsers?.adapter = adapter
    }

    override fun onRefresh() {
        binding?.lastPage?.visibility = View.GONE
        adapter.clear()
        page = 1
        getUsers(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
