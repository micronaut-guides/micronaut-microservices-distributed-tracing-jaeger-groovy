package example.micronaut.bookrecommendation

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BookControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient client = HttpClient.create(embeddedServer.URL)

    void "retrieve books"() {
        when:
        HttpResponse response = client.toBlocking().exchange(HttpRequest.GET("/books"), Argument.of(List, BookRecommendation))

        then:
        response.status() == HttpStatus.OK
        response.body().size() == 1
        response.body().get(0).name == "Building Microservices"
    }
}
