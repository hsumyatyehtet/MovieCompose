package com.hmyh.moviecompose.ui.tutorial

fun main(){

    val list = listOf("A","B","C")
    val finder = Finder(list)
    val list2 = listOf(1,2,3)
    val finder2 = Finder2(list2)

    finder.find("A"){
        println("Found: $it")
    }

    finder2.findItem(10){
        println("Found Number: $it")
    }

}

//Generic class
class Finder<T>(val elements: List<T>){
    fun find(element: T,foundItem: (element: T?)-> Unit){

        val itemFoundList = elements.filter{
            it == element
        }

        if (itemFoundList.isNullOrEmpty())foundItem(null)else
            foundItem(itemFoundList.first())
    }
}

class Finder2(val elements: List<Int>){
    fun findItem(element: Int,foundItem: (element: Int?) -> Unit){

        val itemFoundList = elements.filter {
            it == element
        }

        if(itemFoundList.isNotEmpty()) foundItem(itemFoundList.first())
        else foundItem(null)

    }
}