package example.micronaut.bookcatalogue

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BooksControllerSpec extends Specification {
    @Shared
    @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient client = HttpClient.create(embeddedServer.URL)

    void "it is possible to retrieve books"() {
        when:
        HttpRequest request = HttpRequest.GET("/books")
        List books = client.toBlocking().retrieve(request, Argument.of(List, Book)) // <3>

        then:
        books.size() == 3
        books.contains(new Book("1491950358", "Building Microservices"))
        books.contains(new Book("1680502395", "Release It!"))
    }
}
