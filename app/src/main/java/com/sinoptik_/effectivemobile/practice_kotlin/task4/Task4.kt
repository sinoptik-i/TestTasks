package com.sinoptik_.effectivemobile.practice_kotlin.task4

import java.util.Collections

class Task4 {
    private val intNullableList: List<Int?> = listOf(
        7, 5, 2, null, 8, 10, null, null, 4, 1
    )

    fun exampleSort() =
        shakeSort(intNullableList.shuffled())


    fun shakeSort(list: List<Int?>?): List<Int?> {
        println(list)
        list ?: return emptyList()
        val listInt = list.filterNotNull().toMutableList()
        var complete = false
        var iterCount = 1
        println(listInt)
        while (!complete) {
            complete = true
            for (num in iterCount - 1..listInt.lastIndex - iterCount) {
                if (listInt[num] > listInt[num + 1]) {
                    Collections.swap(listInt, num, num + 1)
                    complete = false
                }
            }
            if(complete){
                break
            }
            complete = true
            println("$iterCount, $listInt")
            for (num in listInt.lastIndex - iterCount downTo iterCount) {
                if (listInt[num] < listInt[num - 1]) {
                    Collections.swap(listInt, num, num - 1)
                    complete = false
                }
            }
            println("$iterCount, $listInt")
            iterCount++
        }
        listInt.addAll(Collections.nCopies((list.count() - listInt.count()), null))
        return listInt.toList()
    }


}

