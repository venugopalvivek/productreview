# Product Review
A Basic product review engine

## Features
* list products prepopulated in database
* list products of a specific score
* list reviews for a product
* list reviews of a specific score
* review score can only be between 1 and 5
* post review for a product - no need for user registration
* can only post one review per email per product
* review will be available in about 5 seconds after posting when it gets approved

## Running
* change the application.properties file to change the following properties
    * `db.url=jdbc:mysql://<host>:<port>/intuit`
    * `db.username=<username>`
    * `db.password=<password>`
* run gradle clean jettyRun
* will be available at http://localhost:8080/productreview/api/...
* documentation of apis available at http://localhost:8080/productreview/api/swagger.json

## Improvements
* to add a new product
* pagination on listing of products
* pagination on listing of reviews
* unit testing using H2 in-memory database
* more unit tests


