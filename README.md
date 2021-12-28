# Function Programming in Kotlin

![master build](https://github.com/fpinkotlin/fpinkotlin/actions/workflows/master.yml/badge.svg)

This is the official source code repository for Manning's [Functional
Programming in
Kotlin](https://www.manning.com/books/functional-programming-in-kotlin) book. It
contains all code samples and exercises that appear in the book.

### Book build process

The book itself is written in [AsciiDoc](http://asciidoc.org/), and is built
using [Asciidoctor](https://asciidoctor.org). The code in the repo is the
_exact_ code that appears in the book. Asciidoctor pulls all code snippets from
this repo via a git submodule, which implies that all code in the book has been
compiled, and where applicable, tested.  The exercise listings are also taken
from this repo, as are the solutions found in the appendix at the back of the
book. All exercises and solutions have unit tests to prove their validity.

### Running the tests when doing the exercises

This repo is not only used for the purpose of providing the code in book, but
can also be used by the reader to complete the exercises. All unimplemented
exercises can be found under `src/test/kotlin/chapterX/exercises`, and are
marked with the builtin `TODO()` function that Kotlin provides. Each unit test
has been ignored, and can be re-enabled by removing the `!` prefix in the test
methods while implementing the exercises in a TDD fashion. Solutions are also
provided for verification of the exercises, but should be avoided until at least
an attempt has been made at doing the exercises. The solutions can be found
under `src/test/kotlin/chapterX/solutions`. It is encouraged that the student
works through _all_ exercises in the book to gain the required proficiency.

### Building the project

The project is built using Gradle with the Kotlin DSL. The project requires
JDK 11 to build. You can install the correct version of JDK using [SDKMAN!](https://sdkman.io) as follows.

	$ sdk env

Next, run the build locally. Simply execute the following on the command line:

	$ ./gradlew check

Alternatively, the project can be imported into IntelliJ IDEA and run from
within the IDE.

### Continuous Integration

The project builds with GitHub actions, ensuring that the code always compiles,
is correctly formatted and all tests pass, thus validating all the code in the
book.

### Contributions

See something that doesn't look correct, or do you have a better solution to an
exercise? Feel free to raise a PR to improve or correct it.
