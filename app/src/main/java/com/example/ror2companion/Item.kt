package com.example.ror2companion


import java.io.Serializable

class Item: Serializable {
        var name: String? = null
        var image: Int? = null

        var damageIncPercent = 0.0
        var movementSpeedIncPercent = 0.0
        var critChanceIncPercent = 0.0
        var healthIncreasePercent = 0.0
        var healthIncreaseNum = 0
        var healthRegenNum = 0.0

        var stackIncDmg = 0.0
        var stackIncChance = 0.0
        var stackIncMove = 0.0

        constructor(name: String, image: Int, damageIncrease: Double, stackIncDmg: Double, stackIncChance: Double,
                    stackIncMove: Double, moveSpeedInc:Double,critChanceIncPercent:Double,healthIncreasePercent:Double,healthIncreaseNum: Int,
                    healthRegenNum:Double) {
            this.name = name
            this.image = image
            this.stackIncDmg = stackIncDmg
            this.stackIncChance = stackIncChance
            //Takes care of items that have INITIAL chance to hit and INCREASE change, not DMG (STICKY/ATG)
            if(stackIncDmg == .0 && stackIncChance != .0){
                this.damageIncPercent = damageIncrease
                this.damageIncPercent = this.damageIncPercent * stackIncChance
            }else {
                this.damageIncPercent = damageIncrease
            }

            //In this case, stackInChance acts as a stacker not used for damage, does not actually increase chance
           this.movementSpeedIncPercent = moveSpeedInc
            this.stackIncMove = stackIncMove
            this.stackIncChance = stackIncMove
            this.healthIncreasePercent = healthIncreasePercent
            this.healthIncreaseNum = healthIncreaseNum
            this.critChanceIncPercent = critChanceIncPercent
            this.healthRegenNum = healthRegenNum
        }
//    fun stack(damage: Double): StatChanged{
//        var returnDamage:Double = damage
//        if(stackIncDmg != .0){
//            var takeOff = damage * damageIncPercent
//            returnDamage -= takeOff
//            damageIncPercent += stackIncDmg
//            var addOn = returnDamage * damageIncPercent
//            returnDamage += addOn
//            damageIncPercent -= stackIncDmg
//            return StatChanged(
//                damageChanged = true,
//                healthChanged = false,
//                speedChanged = false,
//                regenChanged = false,
//                newDamage = returnDamage,
//                newHealth = .0,
//                newSpeed = .0,
//                newRegen = .0
//            )
//        }
//        else if(stackIncChance != .0){
//            var takeOff = damage * damageIncPercent
//            returnDamage -= takeOff
//            damageIncPercent += 2 *stackIncChance
//            var addOn = returnDamage * damageIncPercent
//            returnDamage += addOn
//            damageIncPercent -= 2 * stackIncChance
//            return StatChanged(
//                damageChanged = true,
//                healthChanged = false,
//                speedChanged = false,
//                regenChanged = false,
//                newDamage = returnDamage,
//                newHealth = .0,
//                newSpeed = .0,
//                newRegen = .0
//            )
//        }
//        else if(stackIncDmg == .0 && stackIncChance == .0){
//            return StatChanged(
//                damageChanged = true,
//                healthChanged = false,
//                speedChanged = false,
//                regenChanged = false,
//                newDamage = returnDamage,
//                newHealth = .0,
//                newSpeed = .0,
//                newRegen = .0
//            )
//        }
//        else if(healthIncreaseNum != 0 ){
//
//        }
//    }
    }