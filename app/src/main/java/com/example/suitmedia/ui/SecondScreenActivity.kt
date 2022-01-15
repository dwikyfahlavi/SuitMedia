package com.example.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.suitmedia.ui.adapter.UsersAdapter
import com.example.suitmedia.databinding.ActivitySecondScreenBinding
import com.example.suitmedia.model.Data

class SecondScreenActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivitySecondScreenBinding? = null
    private val binding get() = _binding as ActivitySecondScreenBinding

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == UsersAdapter.RESULT_CODE && result.data != null) {
            val item = result.data?.getParcelableExtra<Data>(UsersAdapter.EXTRA_ITEM)
            with(binding){
                empty.visibility = View.GONE
                linear.visibility = View.VISIBLE
                val name = item?.first_name +" "+ item?.last_name
                tvItemName.text = name
                tvItemEmail.text = item?.email
                Glide.with(applicationContext)
                    .load(item?.avatar)
                    .into(imgItemAvatar)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.btnChoose.setOnClickListener(this)

        val name = intent.getStringExtra(EXTRA_NAME)

        binding.tvName.text = name
    }

    override fun onClick(p: View) {
        val intent = Intent(this@SecondScreenActivity, MainActivity::class.java)
        resultLauncher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object{
        const val EXTRA_NAME = "extra_name"
    }


}