package com.cmaye.tic_toc_toe.model

data class TTT(
    var id : Int ,
    var status : Int = 0, // 0 - start , 1 - first , 2 - second
    var check : Boolean = false
)
