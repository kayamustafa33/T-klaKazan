package com.mustafa.tklakazan.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.mustafa.tklakazan.model.User
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignInBinding

    private var secondaryHandler: Handler? = Handler()
    private var primaryProgressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        supportActionBar?.hide()

    }

    fun signInClicked(view : View){
        val email : String = binding.emailEditText.text.toString().trim()
        val password : String = binding.passwordEditText.text.toString().trim()

        if(email != "" && password != ""){
            startProgress(email,password)
        }else{
            Toast.makeText(applicationContext,"Fill in the requirements!",Toast.LENGTH_LONG).show()
        }
    }

    fun singUpTextClicked(view : View){
        val intent = Intent(this@SignInActivity,SignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun clearAttr(){
        binding.emailEditText.text.clear()
        binding.passwordEditText.text.clear()
    }

    private fun startProgress(email : String,password : String){
        primaryProgressStatus = 0


        val dialog = Dialog(this@SignInActivity)
        dialog.setContentView(R.layout.progress_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val textViewPrimary = dialog.findViewById<TextView>(R.id.textViewPrimary)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBarSecondary)
        dialog.show()

        Thread {
            while (primaryProgressStatus < 100) {
                primaryProgressStatus += 1

                try {
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                secondaryHandler?.post {
                    progressBar.progress = primaryProgressStatus
                    textViewPrimary.text = "%$primaryProgressStatus"

                    if (primaryProgressStatus == 100) {
                        clearAttr()
                        val user = User()
                        user.signInUser(applicationContext, email, password)
                        dialog.dismiss()
                    }
                }
            }
        }.start()
    }

    fun forgotPasswordClicked(view: View){
        startActivity(Intent(this@SignInActivity,ForgotPasswordActivity::class.java))
    }
}