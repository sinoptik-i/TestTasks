package com.sinoptik_.effectivemobile.practice1.task1




/*
data calss Key(
val field1: Int,
var field2: String
) {
    var field3: String? = null
}*/
/*Могут ли возникнуть какие-то проблемы, если мы будем использовать подобный класс
в качестве ключа для HashMap?*/

/*
Ответ:
    Да, есть 2 проблемы.
    1)поле field3 не учитывается equals и hashCode, т к оно не в первичном конструкторе
    2)поле field2 var и может измениться, что вызовет коллизию.
    Можно исправить, например, вот так:
*/

data class Key(
    val field1: Int,
    val field2: String,
    val field3: String? = null
)