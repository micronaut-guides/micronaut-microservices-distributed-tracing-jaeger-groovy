package example.micronaut.bookinventory

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.Test
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BooksControllerSpec extends Specification {
    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient client = HttpClient.create(embeddedServer.URL)

    void "for a book with inventory true is returned"() {
        when:
        Boolean hasStock = client.toBlocking().retrieve(HttpRequest.GET("/books/stock/1491950358"), Boolean)

        then:
        noExceptionThrown()
        hasStock
    }

    void "for a book without inventory false is returned"() {
        when:
        Boolean hasStock = client.toBlocking().retrieve(HttpRequest.GET("/books/stock/1680502395"), Boolean)

        then:
        noExceptionThrown()
        hasStock == Boolean.FALSE
    }

    void "for an invalid ISBN 404 is returned"() {
        when:
        client.toBlocking().retrieve(HttpRequest.GET("/books/stock/XXXXX"))

        then:
        def e = thrown(HttpClientResponseException)
        e.response.status == HttpStatus.NOT_FOUND
    }
}
