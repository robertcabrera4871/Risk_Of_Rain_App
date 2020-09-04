package com.example.ror2companion

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.date_level_popup.view.*
import kotlinx.android.synthetic.main.past_run_box.view.*
import kotlinx.android.synthetic.main.run_stats.view.*

private lateinit var backgroundAnimation: AnimationDrawable

var pastRuns = ArrayList<Run>()

class PastRuns: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.past_runs)
        val theLayout: View = findViewById(R.id.pastRunsLayout)
        theLayout.apply { setBackgroundResource(R.drawable.animation)
            backgroundAnimation = background as AnimationDrawable

        }
        val myDb = DataBaseManager(applicationContext)
        myDb.writableDatabase
         pastRuns = myDb.selectAll()
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val spaceInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rootView = findViewById<LinearLayout>(R.id.linearOfScroll)

        for(i in pastRuns){
           var newView = inflater.inflate(R.layout.past_run_box,null)
            newView.pastRunCharView.setImageResource(i.imgId)
            newView.pastRunText.text = "Character: ${i.character} \n Date: ${i.dateCreated}"
            newView.tag = i.dbId
            rootView.addView(newView)
            rootView.addView(spaceInflater.inflate(R.layout.custom_space, null))
        }
    }
    override fun onStart() {
        super.onStart()
        backgroundAnimation.start()
    }

    fun toCharacterSelect(view: View){
        finish()
    }
    fun toRunScreen(view: View){
        val myIntent= Intent(this, RunStats::class.java)
        myIntent.putExtra("Caller", "PastRuns")
        for(i in pastRuns){
            if(view.tag == i.dbId){
                myIntent.putExtra("PastRun", i)
            }
        }
        startActivity(myIntent)
    }
}