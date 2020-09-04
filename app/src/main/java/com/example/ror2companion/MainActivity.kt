package com.example.ror2companion

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.pick_character.*


class MainActivity : AppCompatActivity() {


    private lateinit var backgroundAnimation: AnimationDrawable
    var characterChosenString: String = "None Selected"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.pick_character)
        val theLayout: View = findViewById(R.id.pickCharLayout)
        theLayout.apply { setBackgroundResource(R.drawable.animation)
            backgroundAnimation = background as AnimationDrawable
        }

    }

    override fun onStart() {
        super.onStart()
        val theScroll: View = findViewById(R.id.characterScrollView)
        theScroll.setBackgroundColor(Color.TRANSPARENT)
        backgroundAnimation.start()
    }

    fun toItemSelect(view: View) {
        val myIntent= Intent(this, ItemSelect::class.java)
        when(view.id){
            commandoSelect.id -> characterChosenString = getString(R.string.commando)
            huntressSelect.id -> characterChosenString = getString(R.string.huntress)
            multSelect.id -> characterChosenString = getString(R.string.mult)
            engineerSelect.id -> characterChosenString = getString(R.string.engineer)
            artificerSelect.id -> characterChosenString = getString(R.string.artificer)
            mercSelect.id -> characterChosenString = getString(R.string.mercenary)
            rexSelect.id -> characterChosenString = getString(R.string.rex)
            loaderSelect.id -> characterChosenString = getString(R.string.loader)
            acridSelect.id -> characterChosenString = getString(R.string.acrid)
            else -> {
                characterChosenString = "Character Not Found"
            }
        }
        myIntent.putExtra("charChosen", characterChosenString)
        startActivity(myIntent)
    }
    fun toPastRuns(view: View) {
        val myIntent= Intent(this, PastRuns::class.java)
        startActivity(myIntent)
    }
}
