package example.micronaut.bookrecommendation

import io.reactivex.Flowable

interface BookCatalogueOperations {
    Flowable<Book> findAll()
}
