package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.parser.Input
import com.example.myapplication.parser.Parser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val stringRequireMap = mutableMapOf<Int, Pair<String, Boolean>>()
    val flow: Flow<List<List<Input>>> = flow {
        val list = Parser.parser()
        for (i in list)
            for (j in i) {
                stringRequireMap[j.fieldId] = Pair("", j.required)
            }
        emit(list)
    }

    fun textChangedListener(text: String, id: Int) {
        stringRequireMap[id] = stringRequireMap[id]?.copy(first = text) ?: Pair("", false)
    }

    fun check(): Boolean {
        for (key in stringRequireMap.keys)
            if (stringRequireMap[key]!!.first == "" && stringRequireMap[key]!!.second)
                return false
        return true
    }

}