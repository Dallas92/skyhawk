# NBA system


## Description:

1.System can accept multiple requests and process them
2.System saves player's statistics to database
3.System can provide aggregate statistics for "season average per player" or "season average per team"


## Prerequisites

- Docker
- Docker Compose
- Java 17 or later
- Maven


## Formatting

We use Google FMT formatting for Java code <br/>
Run `mvn fmt:format` command before making any commit to GIT

## Getting Started

### Running applications locally

1. Run Postgresql in Docker

    `docker run --name local-postgres \
   -e POSTGRES_USER=user \
   -e POSTGRES_PASSWORD=password \
   -e POSTGRES_DB=postgres \
   -p 5432:5432 \
   -d postgres
   `

2. Run PgAdmin in Docker

   `docker run --name local-pgadmin -p 80:80 \
   -e PGADMIN_DEFAULT_EMAIL=user@mail.com \
   -e PGADMIN_DEFAULT_PASSWORD=password \
   -d dpage/pgadmin4
`

3. Run nba-service app with environment variables

   `PLAYER_ID=ivan;
   PLAYER_FIXED_RATE=4000;
   OPPONENT_PLAYER_URL=http://localhost:8082;`

4. Run SVC in Docker

   `docker run --name local-nba-service \
   -e DB_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres \
   -e DB_DATASOURCE_USERNAME=user \
   -e DB_DATASOURCE_PASSWORD=password \
   -p 8080:8080 \
   -d nba-service
   `
### Running applications in Docker

1. Build docker image
    
    `docker build -t nba-service .`

2. Run docker compose

    `docker-compose up -d`

## Postman collection

Import Postman collection to send requests to application.

Use GET /api/games endpoints for monitoring progress of all games
