Prerequistes:

1. Maven
2. PostgreSQL database running on port 5432

Steps to start the application:

1. mvn clean package
2. mvn spring-boot:running


How it works:

1. The application uses Flyway to install the required tables and plugins, so running the SQL manually is not required.
2. The Flyway script creates 7 sample users and 2 groups.
3. The application runs on port 8080
4. After the application is started, an interactive API documentation and playground is available at http://localhost:8080/splitexp/swagger-ui.html