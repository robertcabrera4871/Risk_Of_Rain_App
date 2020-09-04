package com.example.ror2companion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val PAST_RUNS = "PastRuns"
val ID = "run_ID"
val CHARACTER = "Character_Name"
val DATE = "Run_Date"
val LEVEL = "Level"
val MOVEMENT_SPEED = "Movement_Speed"
val DAMAGE = "Damage"
val HEALTH = "Health"
val HEALTH_REGEN = "Health_Regen"
val CRIT_CHANCE = "Crit_Chance"



class DataBaseManager(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val  DATABASE_VERSION = 1
        const val  DATABASE_NAME = "past_runs.db"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${PAST_RUNS} (" +
                "${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CHARACTER} TEXT, " +
                "${DATE} TEXT, " +
                "${LEVEL} TEXT, " +
                "${MOVEMENT_SPEED} TEXT, " +
                "${DAMAGE} TEXT, " +
                "${HEALTH} TEXT, " +
                "${HEALTH_REGEN} TEXT, " +
                "${CRIT_CHANCE} TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $PAST_RUNS")
        onCreate(db)
    }

    fun insert(run: Run){
        val db = this.writableDatabase
        val insertString = "INSERT INTO $PAST_RUNS (${CHARACTER},${DATE},${LEVEL},${MOVEMENT_SPEED},${DAMAGE},${HEALTH},${HEALTH_REGEN},${CRIT_CHANCE})" +
                "VALUES ('${run.character}','${run.dateCreated}','${run.level.toString()}','${run.movementSpeed.toString()}','${run.damage.toString()}','${run.health.toString()}'" +
                ",'${run.healthRegen.toString()}','${run.critChance.toString()}')"
        db.execSQL(insertString)
    }
    fun selectAll():ArrayList<Run>
    {
        val sqlQuery = "select * from $PAST_RUNS"
        val db = this.writableDatabase
        var toReturn = ArrayList<Run>()
        var cursor = db.rawQuery(sqlQuery,null)
        while(cursor.moveToNext()){
            toReturn.add(Run(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8)))
        }
        return toReturn
    }
    fun modify(run: Run){
        val db = this.writableDatabase
        val modifyString = "UPDATE $PAST_RUNS SET $DATE = '${run.dateCreated}'," +
                "${LEVEL} = '${run.level}', ${MOVEMENT_SPEED} = '${run.movementSpeed}'," +
                "${DAMAGE} = '${run.damage}', ${HEALTH} = '${run.health}', " +
                "${HEALTH_REGEN} = '${run.healthRegen}', ${CRIT_CHANCE} = '${run.critChance}' " +
                "WHERE $ID = '${run.dbId}'"
        db.execSQL(modifyString)
    }
}