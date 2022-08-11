package com.example.myapplication.parser

import android.util.Log.d




import org.json.JSONArray
import org.json.JSONTokener


object Parser {
    private const val json: String = "[ \n" +
            "[ \n" +
            "{ \n" +
            "\"field_id\":1, \n" +
            "\"hint\":\"UserName\", \n" +
            "\"field_type\":\"input\", \n" +
            "\"keyboard\":\"text\", \n" +
            "\"required\":false, \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" \n" +
            "}, \n" +
            "{ \n" +
            "\"field_id\":2, \n" +
            "\"hint\":\"Email\", \n" +
            "\"field_type\":\"input\", \n" +
            "\"required\":true, \n" +
            "\"keyboard\":\"text\", \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" \n" +
            "}, \n" +
            "{ \n" +
            "\"field_id\":3, \n" +
            "\"hint\":\"phone\", \n" +
            "\"field_type\":\"input\", \n" +
            "\"required\":true, \n" +
            "\"keyboard\":\"number\", \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" \n" +
            "} \n" +
            "], \n" +
            "[ \n" +
            "{ \n" +
            "\"field_id\":4,\n" +
            "\"hint\":\"FullName\", \n" +
            "\"field_type\":\"input\", \n" +
            "\"keyboard\":\"text\", \n" +
            "\"required\":true, \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" }, \n" +
            "{ \n" +
            "\"field_id\":14, \n" +
            "\"hint\":\"Jemali\", \n" +
            "\"field_type\":\"input\", \n" +
            "\"keyboard\":\"text\", \n" +
            "\"required\":false, \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" }, \n" +
            "{ \n" +
            "\"field_id\":89, \n" +
            "\"hint\":\"Birthday\", \n" +
            "\"field_type\":\"chooser\", \n" +
            "\"required\":false, \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" }, \n" +
            "{ \n" +
            "\"field_id\":898, \n" +
            "\"hint\":\"Gender\", \n" +
            "\"field_type\":\"chooser\", \n" +
            "\"required\":\"false\", \n" +
            "\"is_active\":true, \n" +
            "\"icon\":\"https://jemala.png\" } \n" +
            "] \n" +
            "]\n"


    fun parser(): List<List<Input>> {
        val array = JSONTokener(json).nextValue() as JSONArray
        val mtList: MutableList<List<Input>> = mutableListOf()
        var tempList = mutableListOf<Input>()
        d("length", array.length().toString())
        var array2: JSONArray
        for (i in 0 until array.length()) {
            array2 = JSONTokener(array[i].toString()).nextValue() as JSONArray
            d("length", array2.length().toString())
            for (j in 0 until array2.length()) {
                val obj = array2.getJSONObject(j)
                tempList.add(
                    Input(
                        fieldId = obj.getInt("field_id"),
                        hint = obj.getString("hint"),
                        fieldType = obj.getString("field_type"),
                        required = obj.getBoolean("required"),
                        isActive = obj.getBoolean("is_active"),
                        icon = obj.getString("icon")
                    )
                )
                if (array2.getJSONObject(j).has("keyboard"))
                    tempList[j].keyboard = obj.getString("keyboard")
            }
            mtList.add(tempList)
            tempList = mutableListOf()
        }
        return mtList
    }
}