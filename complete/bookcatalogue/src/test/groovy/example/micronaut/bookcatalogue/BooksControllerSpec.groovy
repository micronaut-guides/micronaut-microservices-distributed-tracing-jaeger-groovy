package example.micronaut.bookcatalogue

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BooksControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient client

    void "it is possible to retrieve books"() {
        when:
        HttpRequest request = HttpRequest.GET("/books")
        List books = client.toBlocking().retrieve(request, Argument.listOf(Book))

        then:
        books.size() == 3
        books.contains(new Book("1491950358", "Building Microservices"))
        books.contains(new Book("1680502395", "Release It!"))
    }
}
