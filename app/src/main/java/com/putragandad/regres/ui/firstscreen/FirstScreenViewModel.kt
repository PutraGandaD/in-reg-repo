package com.putragandad.regres.ui.firstscreen

import androidx.lifecycle.ViewModel

class FirstScreenViewModel : ViewModel() {
    fun checkPalindrome(word: String) : Boolean {
        var newStr = ""

        for (c in word) {
            if(c in 'a'..'z' || c in 'A'..'Z' || c in '0'..'9') {
                newStr += c.lowercaseChar()
            }
        }

        return newStr == newStr.reversed()
    }
}