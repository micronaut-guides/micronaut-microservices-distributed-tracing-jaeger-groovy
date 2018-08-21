package example.micronaut.bookcatalogue

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@TupleConstructor
@EqualsAndHashCode
@CompileStatic
class Book {
    String isbn
    String name
}
