package example.micronaut.bookrecommendation

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Flowable

@CompileStatic
@Controller("/books")
class BookController {

    private final BookCatalogueOperations bookCatalogueOperations
    private final BookInventoryOperations bookInventoryOperations

    BookController(BookCatalogueOperations bookCatalogueOperations,
                   BookInventoryOperations bookInventoryOperations) {
        this.bookCatalogueOperations = bookCatalogueOperations
        this.bookInventoryOperations = bookInventoryOperations
    }

    @Get("/")
    Flowable<BookRecommendation> index() {
        return bookCatalogueOperations.findAll()
            .flatMapMaybe { b ->
                bookInventoryOperations.stock(b.isbn)
                    .filter { hasStock -> hasStock == Boolean.TRUE }
                    .map { rsp -> b }
            }.map { book -> new BookRecommendation(book.name) }
    }
}
