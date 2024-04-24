# Shopping Cart Application

This is a simple shopping cart application written in Java using Gradle as a build tool.

## Requirements

- Java 8 or higher
- Kotlin 1.5 or higher
- Gradle 7.0 or higher

## Setup

1. Clone the repository to your local machine.
2. Navigate to the project directory.

This is where all the java classes are.


## Running the Tests

To run the tests, use the following command:

./gradlew test

Then if you go into the ShoppingCartTest.java class you can click the run button, and it shall display the tests.

## IDE

The project can be imported into any IDE that supports Gradle projects. For IntelliJ IDEA, you can simply open the project directory and IntelliJ IDEA will automatically import the Gradle project.

## Project Structure

The project follows a standard layout with source code located in `src/main/java/org.example` and `test/java/org.example` 

## Key Classes

- `ShoppingCart`: Represents a shopping cart to which products can be added or removed.
- `Product`: Represents a product with a name, unit price, and optional offer.
- `Offer`: Represents a special offer associated with a product.
- `ShoppingCartTest`: Contains unit tests for the `ShoppingCart` class.


