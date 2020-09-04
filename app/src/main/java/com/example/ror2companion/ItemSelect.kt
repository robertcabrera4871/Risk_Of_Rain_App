package com.example.ror2companion

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.item_select.*
import kotlinx.android.synthetic.main.itementry.view.*

/*
Contemplations on the layout for item grid
 */
private lateinit var backgroundAnimation: AnimationDrawable

var adapter: ItemAdapter? = null
var itemList = ArrayList<Item>()
var isAttached = false
var chosenCharacter = "Commando"
var itemsChosenArray = ArrayList<Item>()
var createdRun = Run(chosenCharacter, itemsChosenArray)

class ItemSelect: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.item_select)
        val theLayout: View = findViewById(R.id.itemSelectConstraint)
        theLayout.apply { setBackgroundResource(R.drawable.animation)
            backgroundAnimation = background as AnimationDrawable
        }
        addAdapter()
        isAttached = true
        chosenCharacter = intent.getStringExtra("charChosen")
        createdRun = Run(chosenCharacter, itemsChosenArray)
    }
    override fun onStart() {
        super.onStart()
        backgroundAnimation.start()

    }

    override fun onStop() {
        super.onStop()
        itemList.clear()
        var myAdaptor: ItemAdapter = itemGrid.adapter as ItemAdapter
        myAdaptor.notifyDataSetChanged()
        itemGrid.invalidate()
        isAttached = false
    }

    override fun onResume() {
        super.onResume()
        if(!isAttached){
            addAdapter()
        }
    }

    fun toRunScreen(view: View) {
        val myIntent= Intent(this, RunStats::class.java)
       myIntent.putExtra("RunObject", createdRun)
        myIntent.putExtra("Caller", "ItemSelect")
        startActivity(myIntent)
    }
    fun toItemArray(view:View){
        var chosenItem = adapter?.getItem(view.tag as Int) as Item
        createdRun.obtainedItems.add(chosenItem)
        when(view.tag){
            0, 1, 2, 3, 4, 5, 7, 9, 13, 14,20 -> {
                createdRun.damage += (createdRun.damage * chosenItem.damageIncPercent)
            }
            6, 8,19-> {
                createdRun.movementSpeed += (createdRun.movementSpeed * chosenItem.movementSpeedIncPercent)
            }
            10 ->{
                createdRun.damage *= chosenItem.damageIncPercent
                createdRun.health *= chosenItem.healthIncreasePercent
            }
            11->{
                createdRun.critChance += chosenItem.critChanceIncPercent
            }
            12->{
                createdRun.health += chosenItem.healthIncreaseNum
            }
            15->{
                createdRun.health += chosenItem.healthIncreaseNum
                createdRun.healthRegen += chosenItem.healthRegenNum
            }
            16->{
                createdRun.critChance += chosenItem.critChanceIncPercent
                createdRun.healthRegen += createdRun.healthRegen * chosenItem.healthIncreasePercent
            }
            17,18 ->{
                createdRun.healthRegen += createdRun.healthRegen * chosenItem.healthIncreasePercent
            }

        }
        Toast.makeText(this, chosenItem.name.plus(" Added"), Toast.LENGTH_SHORT).show()
    }
    private fun addAdapter(){
        //DMG, STACKDMG, STACKCHNC, STACKINCMOVE MVMTSPEED CRITINC HPINCPER HPINCNUM HPREGENPERC
        //Usually .2 but mostly 10% of enemies are bosses
        itemList.add(Item("Armor Piercing", R.drawable.armorepierce, .002, .002, .0, .0, .0,.0,.0,0,.0)) //*
        //Usually 3 but w/ 10% chance of hitting
        itemList.add(Item("ATG", R.drawable.atg, 0.3, 0.3, .0,.0,.0, .0,.0,0,.0)) //*
        itemList.add(Item("Behemoth", R.drawable.behemoth,0.6, .0, .0,.0,.0, .0,.0,0,.0))//***
        //5% chance to hit 180%
        itemList.add(Item("Sticky", R.drawable.bomb, 1.8,.0,.05,.0,.0, .0,.0,0,.0))//**
        //150% damage above 90% HP
        itemList.add(Item("Crowbar", R.drawable.crowbar,0.15,.005,.0,.0,.0, .0,.0,0,.0)) //*
        itemList.add(Item("Dagger", R.drawable.dagger,2.4,.0,.15,.0,.0, .0,.0,0,.0))//**
        //Movement speed up 30% 75% of the time
        itemList.add(Item("Energy Drink", R.drawable.energydrink,.0,.0,.0,.15,.225, .0,.0,0,.0))
        //Damage up 15% about 50% of time
        itemList.add(Item("Focus Crystal", R.drawable.focuscrystal,.075,.075,.0,.0,.0, .0,.0,0,.0))//*
        itemList.add(Item("Hoof", R.drawable.foot, .0,.0,.0,.14,.14, .0,.0,0,.0))
        //Killing enemies 50% of the time (give or take) for 150% damage
        itemList.add(Item("Gas", R.drawable.gas,.75,.375,.0,.0,.0, .0,.0,0,.0))//*
        //NOTE TAKE CARE OF SHAPED GLASS CASE
        itemList.add(Item("Shaped Glass", R.drawable.glass,2.0,2.0,.0,.0,.0,.0,.5,0,.0))//*
        itemList.add(Item("Crit Glasses", R.drawable.glasses,.0,.0,.0,.0,.0,10.0,.0,0,.0))
        itemList.add(Item("Infusion", R.drawable.infusion,.0,.0,.0,.0,.0,.0,.0,100,.0))//+
        //500% damge 8% of time
        itemList.add(Item("Kjaro's", R.drawable.kband,0.4,0.2,.0,.0,.0,.0,.0,0,.0))//*
        itemList.add(Item("Ronald's",R.drawable.rband,0.2,0.1,.0,.0,.0,.0,.0,0,.0))//*
        itemList.add(Item("Knurl", R.drawable.knurl,.0,.0,.0,.0,.0,.0,.0,40,1.6))//+
        itemList.add(Item("Scythe", R.drawable.scythe,.0,.0,.0,.0,.0,5.0,.0,0,.80))//+
        itemList.add(Item("Leaching Seed", R.drawable.seed,.0,.0,.0,.0,.0,.0,.0,0,.05))//+
        itemList.add(Item("Cautious Slug", R.drawable.slug,.0,.0,.0,.0,.0,.0,.0,0,.05))//+
        itemList.add(Item("Red Whip", R.drawable.whip,.0,.0,.0,.003,.003,.0,.0,0,.0))
        //Killing enemies 50% of the time (give or take) for 350% damage
        itemList.add(Item("Will-o-Wisp", R.drawable.wisp,1.75,1.4,.0,.0,.0,.0,.0,0,.0))//*

        adapter = ItemAdapter(this, itemList)
        itemGrid.adapter = adapter

    }
}
