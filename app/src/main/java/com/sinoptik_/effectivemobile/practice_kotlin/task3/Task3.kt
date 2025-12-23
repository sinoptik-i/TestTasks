package com.sinoptik_.effectivemobile.practice_kotlin.task3

/*Написать extention-функцию для List, которая в рантайме будет искать Int в списке типа Any и возвращать его.
Заранее подготовить список, наполненный разными классами(5-10 шт будет достаточно).
По нажатию на кнопку выводить результат в логи (не использовать рефлексию).*/


//Сам я написал вот так.
fun List<Any>.findInt(): Int? {
    forEach {
        if (it is Int) {
            return it
        }
    }
    return null
}

//Но, конечно, вариант из конвы мне больше нравится.
fun List<Any>.findInt2() = filterIsInstance<Int>().firstOrNull()


class Task3 {
    private val mixedList: List<Any> = listOf(
        "Hello String",
        42,
        3.14,
        true,
        listOf(1, 2, 3),
        mapOf("key" to "value")
    )

    fun findInt() = mixedList.findInt()
}