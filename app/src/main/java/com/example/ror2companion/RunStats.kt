package com.example.ror2companion

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.run_stats.*

private lateinit var backgroundAnimation: AnimationDrawable

var theRun = Run("Commando",  ArrayList<Item>())
class RunStats: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.run_stats)
        val theLayout: View = findViewById(R.id.runStatsLayout)
        theLayout.apply { setBackgroundResource(R.drawable.animation)
            backgroundAnimation = background as AnimationDrawable

        }
        if(intent.hasExtra("RunObject")) {
            theRun = intent.getSerializableExtra("RunObject") as Run
            runStatNext.text = "Add to Database"
        }
        else if(intent.getStringExtra("Caller") == "PastRuns"){
            runStatNext.text = "Modify Database Entry"
            theRun = intent.getSerializableExtra("PastRun") as Run
        }
        runCharImage.setImageResource(theRun.imgId)
        runStatsTextView.text = theRun.outputData()
    }
    override fun onStart() {
        super.onStart()
        backgroundAnimation.start()
    }
    fun toCharacterSelect(view: View) {
        val caller = intent.getStringExtra("Caller")
        if(caller == "ItemSelect"){
           insertAsync().execute(theRun)
        }
        else if(caller == "PastRuns"){
            modifyAsync().execute(theRun)
        }
        val myIntent= Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }
    fun editDateAndLevel(view: View){
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
         val popupWindow = PopupWindow(650, LinearLayout.LayoutParams.WRAP_CONTENT)
        val myView = inflater.inflate(R.layout.date_level_popup,null)
        val levelTextEdit = myView.findViewById<TextView>(R.id.LevelTextView)
        val dateTextView = myView.findViewById<TextView>(R.id.DateTextView)
        val levelDialog = levelDialogBuilder(levelTextEdit)
        val dateDialog = dateDialogBuilder(dateTextView)
        popupWindow.contentView = myView
         myView.findViewById<Button>(R.id.exitButton).setOnClickListener{
                popupWindow.dismiss()
        }
        myView.findViewById<Button>(R.id.finishEditButton).setOnClickListener{
            var userLevelInput = levelTextEdit.text.toString()
            var userDateInput = dateTextView.text.toString()
            if(userLevelInput == "Level" && userDateInput != "Date"){
                theRun.dateCreated = userDateInput
            } else if(userLevelInput != "Level" && userDateInput == "Date"){
                theRun.levelChanged(userLevelInput.toInt())
            } else if(userLevelInput != "Level" && userDateInput != "Date"){
                theRun.dateCreated = userDateInput
                theRun.dateCreated = userDateInput
            }
            runStatsTextView.text = theRun.outputData()
            popupWindow.dismiss()
        }
        levelTextEdit.setOnClickListener{
            levelDialog.show()
        }
        dateTextView.setOnClickListener{
            dateDialog.show()
        }
        popupWindow.showAtLocation(
            findViewById<ConstraintLayout>(R.id.runStatsLayout),
            Gravity.CENTER,
            0,
            0
        )
    }
    private fun levelDialogBuilder(myTextView: TextView): Dialog{
        val builder = AlertDialog.Builder(this)
        val title = SpannableString("Pick Your Level")
        title.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0)
        builder.setTitle(title)
        val numberPicker = NumberPicker(this)
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        builder.setView(numberPicker)
        builder.setPositiveButton("Accept"){dialogInterface, which ->
           myTextView.text = numberPicker.value.toString()
        }
        builder.setNegativeButton("Cancel"){dialogInterface, which ->
        }
        return builder.create()
    }
    private fun dateDialogBuilder(myTextView: TextView): Dialog{
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val myView = inflater.inflate(R.layout.custom_date_picker,null)
        val builder = AlertDialog.Builder(this)
        val title = SpannableString("Set Date of Run")
        title.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length, 0)
        builder.setTitle(title)
        builder.setView(myView)
        val datePicker = myView.findViewById<DatePicker>(R.id.runDatePicker)
        builder.setPositiveButton("Accept"){dialogInterface, which ->
                myTextView.text = "${datePicker.month + 1}/${datePicker.dayOfMonth}/${datePicker.year}"
    }
        builder.setNegativeButton("Cancel"){dialogInterface, which ->
        }
        return builder.create()
    }
    inner class insertAsync: AsyncTask<Run, Unit, Unit>(){
        override fun doInBackground(vararg params: Run?) {
            val myDb = DataBaseManager(applicationContext)
            myDb.writableDatabase
            myDb.insert(params[0]!!)
            return
        }

    }
    inner class modifyAsync: AsyncTask<Run,Unit,Unit>(){
        override fun doInBackground(vararg params: Run?) {
            val myDb= DataBaseManager(applicationContext)
            myDb.writableDatabase
            myDb.modify(params[0]!!)
        }
    }
}