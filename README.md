# Function Programming in Kotlin

[![Build Status](https://travis-ci.org/fpinkotlin/fpinkotlin.svg?branch=master)](https://travis-ci.org/fpinkotlin/fpinkotlin)

This is the official repository for Manning's [Functional Programming in
Kotlin](https://www.manning.com/books/functional-programming-in-kotlin) book.

The book is is written in [AsciiDoc](http://asciidoc.org/), and is built using
[Asciidoctor](https://asciidoctor.org).

The code that appears in the book is the _exact_ code that can be found in this
repository. When Asciidoctor generates the book, the code samples are pulled in
from this repo. The result is that all code appearing in the book has been
compiled, and where appropriate, tested.  The exercise listings are also taken
from this repo, as are the solutions found in the appendix at the back of the
book. All exercises and solutions have unit tests to prove their validity.

### How to use this repo

It is encouraged that the student works through _all_ exercises in the book.
The exercise method signatures are given for each chapter under
`src/test/kotlin/chapterX/exercises`. Each unit test has been ignored, and can
be enabled in turn by removing the `!` prefix in the test methods while
implementing the exercises in a TDD fashion. Solutions are also provided for
verification of the exercises, but should be avoided until at least an attempt
has been made at doing the exercises. The solutions can be found under
`src/test/kotlin/chapterX/solutions`.

### Building the project

The project is built using Gradle with the Kotlin DSL. To run your build, simply
run the following from the command line:

	$ ./gradlew check

Alternatively, the project can be imported into IntelliJ IDEA and run from the
IDE.

### Continuous Integration

The project builds on Travis CI, ensuring that the code always compiles and all
tests pass, thus validating all the code in the book.
