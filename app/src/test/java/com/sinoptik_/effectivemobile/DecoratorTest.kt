package com.sinoptik_.effectivemobile

import org.junit.Test

// 1. Общий интерфейс
interface HttpClient {
    fun sendRequest(url: String, data: String): String
    fun anyFun()
}

// 2. Базовый компонент (чистая логика без мусора)
class OkHttpClient : HttpClient {
    override fun sendRequest(url: String, data: String): String {
        return "Ответ от $url на данные $data" // Имитируем запрос
    }
    override fun anyFun() = println("anyFun")
}

// 3. Декоратор Логирования
// Магия Kotlin: ключевое слово `by` делегирует все методы интерфейса объекту baseClient
class LoggingHttpClientDecorator(
    private val baseClient: HttpClient
) : HttpClient by baseClient {
    // Мы переопределяем ТОЛЬКО тот метод, поведение которого хотим расширить
    override fun sendRequest(url: String, data: String): String {
        println("[LOG] Отправка запроса на: $url") // Наша новая обязанность
        val response = baseClient.sendRequest(url, data) // Делегируем базовую работу
        println("[LOG] Получен ответ для: $url")
        return response
    }
}

// 4. Декоратор Кэширования
class CachingHttpClientDecorator(
    private val baseClient: HttpClient
) : HttpClient by baseClient {
    private val cache = mutableMapOf<String, String>()

    override fun sendRequest(url: String, data: String): String {
        val cacheKey = "$url?$data"
        if (cache.containsKey(cacheKey)) {
            println("[CACHE] Возвращаем данные из кэша!")
            return cache[cacheKey]!!
        }
        val response = baseClient.sendRequest(url, data)
        cache[cacheKey] = response
        return response
    }
}

class DecoratorTest {

    @Test
    fun decoratorTest() {
        // Собираем «матрёшку» из обязанностей
        val rawClient = OkHttpClient()
        val loggedClient = LoggingHttpClientDecorator(rawClient)
        val fullyLoadedClient = CachingHttpClientDecorator(loggedClient)
        println("\n--- 0, logg ---")
        println(loggedClient.sendRequest("https://api.com", "id=1"))
        println("\n--- 1, logg+net ---")
        // Первый запрос: отработает и логгер, и сеть
        println(fullyLoadedClient.sendRequest("https://api.com", "id=1"))
        println("\n--- 2 Повторный запрос ---")
        // Второй запрос: отработает кэш, до логгера и сети вызов даже не дойдет!
        println(fullyLoadedClient.sendRequest("https://api.com", "id=1"))
    }
}