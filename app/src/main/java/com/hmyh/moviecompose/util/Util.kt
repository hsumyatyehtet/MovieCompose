package com.hmyh.moviecompose.util

fun calculateTotalTip(totalBill: Double?, tipPercentage: Int): Double? {

    var finalValue: Double?=null

    if (totalBill != null) {
        if (totalBill > 1 && totalBill.toString().isNotEmpty()){
            finalValue = (totalBill * tipPercentage) / 100
        }
        else {
            finalValue = 0.0
        }
    }

    return finalValue

}