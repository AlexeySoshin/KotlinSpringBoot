package me.soshin.kotlinSpringBoot

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class NoCatsTest {

	@Autowired lateinit var restTemplate: TestRestTemplate

	@Test
	fun indexShouldReturnEmptyListWhenNoCatsExist() {
		val response = restTemplate.getForEntity("/", CatsList::class.java)

		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).isNotNull.isEmpty()
	}
}

class CatsList : MutableList<Cat> by ArrayList()

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class SingleCatTest {

	@Autowired lateinit var restTemplate: TestRestTemplate

	val fionaTheCat = Cat(name="Fiona", age=2.6f, weight=5.3f)

	@Before
	fun createCat() {
		restTemplate.postForLocation("/", HttpEntity(fionaTheCat))
	}

	@Test
	fun indexShouldReturnSingleCat() {
		val response = restTemplate.getForEntity("/", CatsList::class.java)

		assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(response.body).isNotEmpty
		assertThat(response.body.size).isEqualTo(1)

		// We would like to ignore id of the cat here
		assertThat(response.body[0]).isEqualToComparingOnlyGivenFields(fionaTheCat, "name", "age", "weight")
	}

	@Test
	fun getCatShouldReturnCatById() {
		val allCatsResponse = restTemplate.getForEntity("/", CatsList::class.java)

		val id = allCatsResponse.body[0].id

		assertThat(id).isNotNull()
		val getCatResponse = restTemplate.getForEntity("/$id", Cat::class.java)

		assertThat(getCatResponse.body).isEqualToComparingOnlyGivenFields(fionaTheCat, "name", "age", "weight")
	}

	@Test
	fun shouldReturnNotFoundIfReceivedWrongId() {
		val response = restTemplate.getForEntity("/${UUID.randomUUID()}", Any::class.java)

		assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
	}
}

