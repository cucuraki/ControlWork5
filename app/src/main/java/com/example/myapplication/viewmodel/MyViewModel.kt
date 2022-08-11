package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.parser.Input
import com.example.myapplication.parser.Parser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyViewModel : ViewModel() {
    private val listOfStrings = Array(7){""}
    private val listOfRequires= Array(7){false}
    val flow: Flow<List<List<Input>>> = flow {
        val list = Parser.parser()
        var ind = 0
        for(i in list)
            for (j in i){
                listOfRequires[ind] = j.required
                ind++
            }

        emit(list)
    }

    fun textChangedListener(text: String, index: Int) {
        listOfStrings[index] = text
    }

    fun check(): Boolean{
        for(i in listOfStrings.indices)
            if(listOfStrings[i] == "" && listOfRequires[i])
                return false
        return true
    }

}