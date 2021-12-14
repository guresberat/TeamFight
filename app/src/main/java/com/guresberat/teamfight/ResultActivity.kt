package com.guresberat.teamfight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_score = findViewById<TextView>(R.id.tv_score)
        val btn_finish = findViewById<Button>(R.id.btn_finish)
        val tv_congratulations = findViewById<TextView>(R.id.tv_congratulations)

        val text = intent.getStringExtra(Constants.RESULT)
        if (text == "0") {
            tv_congratulations.text = "Hey, You Lost!"
        }
        val username = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = username
        val score = intent.getIntExtra(Constants.SCORE, 0)
        tv_score.text = "Your Score is $score out of 10"
        btn_finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}