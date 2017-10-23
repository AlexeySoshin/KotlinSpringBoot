package me.soshin.kotlinSpringBoot

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * This controller should return all known cats as JSON and support CRUD on them
 */
@RestController
class CatsController {

    // We'll save the cats in memory for the sake of excercise
    private val cats = ConcurrentHashMap<UUID, Cat>()

    @GetMapping("/")
    fun allCats(): List<Cat> {
        return cats.values.toList()
    }

    @PostMapping("/")
    fun addCat(@RequestBody cat : Cat) {
        UUID.randomUUID().let { id ->
            cat.id = id
            cats.put(id, cat)
        }
    }

    @GetMapping("/{id}")
    fun getCat(@PathVariable id : String) : Cat {
        return cats[UUID.fromString(id)] ?: throw CatNotFoundException()
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class CatNotFoundException : RuntimeException()

// No need for setters getters for a simple POJO
data class Cat(var id : UUID? = null, val name: String, val age: Float, val weight: Float, var sex : Cat.Sex = Sex.Unknown) {
    enum class Sex {
        Unknown, Male, Female, NonBinary
    }
}

