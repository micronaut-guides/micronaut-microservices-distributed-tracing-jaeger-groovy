package example.micronaut.bookinventory

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@CompileStatic
@TupleConstructor
@EqualsAndHashCode
class BookInventory {
    String isbn
    Integer stock
}
