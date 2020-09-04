package com.example.ror2companion

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Run: Serializable{

    var dbId = -1
    var imgId: Int = 0
    var character: String = ""
    var obtainedItems = ArrayList<Item>()
    var dateCreated = ""
    var level: Int = 0
    // m/s
    var movementSpeed: Double = 0.0
    var damage = 0.0
    var health = 0.0
    //per sec
    var healthRegen = 0.0

    var healthGrowthCoef  = 0.0
    var regenGrowthCoef = 0.0
    var damageGrowthCoef = 0.0
    var critChance = 1.0

    constructor(character: String, obtainedItems: ArrayList<Item>) {
        this.character = character
        this.obtainedItems = obtainedItems
        assignBaseStats()
    }
    constructor(dbId: Int, character: String, runDate: String, level: String, mvmtSpeed: String, damage: String, health: String, healthRegen:String, critChance:String) {
        this.character = character
        assignBaseStats()
        this.dbId = dbId
        this.dateCreated = runDate
        this.level = level.toInt()
        this.movementSpeed = mvmtSpeed.toDouble()
        this.damage = damage.toDouble()
        this.health = health.toDouble()
        this.healthRegen = healthRegen.toDouble()
        this.critChance = critChance.toDouble()

    }
     private fun assignBaseStats(){
        var timeNow = Calendar.getInstance()
        dateCreated = "${(timeNow.get(Calendar.MONTH)) + 1}/${timeNow.get(Calendar.DAY_OF_MONTH)}/${timeNow.get(Calendar.YEAR)}"
        when(character){
            "Commando" ->{
                imgId = R.drawable.commando
                health = 110.0
                healthRegen = 1.0
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 33.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.4
            }
           "Huntress" ->{
                imgId = R.drawable.huntress
                health = 90.0
                healthRegen = 1.0
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 27.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.4
            }
            "Mul-T" ->{
                imgId = R.drawable.mult
                health = 200.0
                healthRegen = 1.0
                damage = 11.0
                movementSpeed = 7.0

                healthGrowthCoef = 60.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.2
            }
            "Engineer" ->{
                imgId = R.drawable.engineer
                health = 130.0
                healthRegen = 1.0
                damage = 14.0
                movementSpeed = 7.0

                healthGrowthCoef = 39.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.8
            }
            "Artificer" ->{
                imgId = R.drawable.artificer
                health = 110.0
                healthRegen = 1.0
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 33.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.4
            }
            "Mercenary" ->{
                imgId = R.drawable.mercenary
                health = 140.0
                healthRegen = 2.5
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 42.0
                regenGrowthCoef = 0.5
                damageGrowthCoef = 2.4
            }
            "Rex" ->{
                imgId = R.drawable.rex
                health = 130.0
                healthRegen = 1.0
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 39.0
                regenGrowthCoef = 0.2
                damageGrowthCoef = 2.4
            }
            "Loader" ->{
                imgId = R.drawable.loader
                health = 160.0
                healthRegen = 2.5
                damage = 12.0
                movementSpeed = 7.0

                healthGrowthCoef = 48.0
                regenGrowthCoef = 0.5
                damageGrowthCoef = 2.4
            }
            "Acrid" ->{
                imgId = R.drawable.acrid
                health = 160.0
                healthRegen = 2.5
                damage = 15.0
                movementSpeed = 7.0

                healthGrowthCoef = 48.0
                regenGrowthCoef = 0.5
                damageGrowthCoef = 3.0
            }
        }
    }
    fun levelChanged(newLevel: Int){
        health -= level * healthGrowthCoef
        healthRegen -= level * regenGrowthCoef
        damage -= level * damageGrowthCoef

        level = newLevel

        health += level * healthGrowthCoef
        healthRegen += level * regenGrowthCoef
        damage += level * damageGrowthCoef
    }
    fun outputData(): String{
        return "Character: "
            .plus(character).plus("\n")
            .plus("Date: ").plus(dateCreated).plus("\n")
            .plus("Level: ").plus(level).plus("\n\n")
            .plus("Movement Speed: \n").plus(roundOutput(movementSpeed)).plus("m/s\n")
            .plus("Damage: \n").plus(roundOutput(damage)).plus("\n")
            .plus("Health: \n").plus(health).plus("\n")
            .plus("Health Regen: \n").plus(roundOutput(healthRegen)).plus("/s\n")
            .plus("Critical Strike Chance: \n").plus(critChance).plus("%")
    }
    fun roundOutput(input: Double): Double{
        return Math.round(input * 100.0) / 100.0
    }
}