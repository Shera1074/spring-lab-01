# Lab 8 — PostgreSQL, entities, associations, queries, paging

1. Start PostgreSQL (Docker):
   `docker run --name pg-springlab -e POSTGRES_DB=springlab -e POSTGRES_USER=springlab -e POSTGRES_PASSWORD=springlab -p 5432:5432 -d postgres:16`
2. IntelliJ: File > Open > select `pom.xml`, choose "Open as project". Java 25 SDK.
3. Run `Lab8Application`. In the console read the `create table` statements and the seed `insert`s.
4. Try:
   - http://localhost:8080/api/books?page=0&size=3
   - http://localhost:8080/api/books?page=1&size=3&sort=year,desc
   - http://localhost:8080/api/books?genre=TECH
   - http://localhost:8080/api/books/search?genre=TECH&year=2010
   - http://localhost:8080/api/books/count?author=Craig Walls
   - POST http://localhost:8080/api/books/archive?year=2000
5. Count SQL statements in the console for the N+1 demo (`N+1 author:` lines vs `JOIN FETCH author:` lines).

Note: this is a standalone project written as a reference. It has not been compiled in this environment.
Read every class and be ready to explain it: it is what the defence asks about.
