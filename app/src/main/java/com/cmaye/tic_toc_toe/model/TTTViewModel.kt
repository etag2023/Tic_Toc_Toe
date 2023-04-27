package com.cmaye.tic_toc_toe.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TTTViewModel : ViewModel() {

    var finalResult = MutableLiveData<TTTResult>()


    fun setGameWin(isWin: Boolean,status : Int,isTimeComplete: Boolean,winItems: MutableList<TTT>)
    {
        this.finalResult.value = TTTResult(isWin,status,isTimeComplete,winItems)
    }


}


data class TTTResult(
    var isWin : Boolean = true,
    var status : Int = 0,
    var isTimeComplete : Boolean = true,
    var winItems : MutableList<TTT>
)