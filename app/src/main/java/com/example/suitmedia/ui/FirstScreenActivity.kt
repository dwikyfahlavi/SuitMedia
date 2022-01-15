package com.example.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.suitmedia.R
import com.example.suitmedia.databinding.ActivityFirstScreenBinding

class FirstScreenActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivityFirstScreenBinding? = null
    private val binding get() = _binding as ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }
    private fun isPalindrome(text : String): Boolean{
        val reverseText = text.reversed().replace(" ","")
        val newText = text.replace(" ","")
        return newText.equals(reverseText, ignoreCase = true)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(p: View) {
        when (p.id){
            R.id.btn_check -> {
                val text = binding.edtPalindrom.text.toString()
                if (text.isEmpty()){
                    binding.edtPalindrom.error = "Palindrome is required"
                }else{
                    if (isPalindrome(text)){
                        Toast.makeText(this@FirstScreenActivity,"Entered word is Palindrome", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@FirstScreenActivity,"Entered word is not Palindrome", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.btn_next -> {
                val name = binding.edtName.text.toString()
                if (name.isEmpty()){
                    binding.edtName.error = "Name is required"
                }else {
                    val intent = Intent(this@FirstScreenActivity, SecondScreenActivity::class.java)
                    intent.putExtra(SecondScreenActivity.EXTRA_NAME,name)
                    startActivity(intent)
                }
            }
        }
    }
}