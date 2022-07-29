# Read Me

**Prerequisite**
1. Application requires a PostgreSQL DB to connect to
   1. DB connection information is configurable at \src\main\resources\application.properties
   2. DB tables will be auto-created by application


**Database Tables**
1. book
2. author
   1. Added a unique field "authorID" for author since the author name is very unlikely to be unique
3. book_authors
   1. Added table
   2. Purpose: Allow many-to-many relationship between books and authors and hence better relational data storage and retrieval

**APIs**

Swagger Open API v3 documentation available at http://localhost:8000/swagger-ui/index.html

![APIs documented on Swagger (OpenAPI v3)](https://github.com/nahwu/base_DXC/blob/main/Screenshot_APIs_on_Swagger.jpg?raw=true)
