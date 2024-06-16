package com.capstone.cuansampah.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(null)
        supportActionBar?.title = ""
    }
    private fun setupAction() {
        binding.btnConfirm.setOnClickListener {
            AlertDialog.Builder(this).apply {
                val dialogView = layoutInflater.inflate(R.layout.dialog_success, null)
                val btnConfirm = dialogView.findViewById<Button>(R.id.btn_confirm)
                val alertDialog = AlertDialog.Builder(this@ResetPasswordActivity)
                    .setView(dialogView)
                    .create()
                btnConfirm.setOnClickListener {
                    val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    alertDialog.dismiss()
                }
                alertDialog.show()
            }
        }
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}