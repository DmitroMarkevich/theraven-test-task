### APPLICATION CONFIG

For local development, no additional installations are necessary.
The application utilizes an in-memory H2 database,
and all required configurations are provided within the project.


For production profile configure the following environment variables:

#### 1. Create environment variables:

```
setx DB_URL ""
setx DB_USERNAME "user"
setx DB_PASSWORD "12345"
```

Example for Linux, run SH and execute commands:

```
export DB_URL=''
export DB_USERNAME='user'
export DB_PASSWORD='12345'
```

#### 2. Start Application with 'prod' Profile.

## **Technologies**

- Java 17
- Spring Boot 3
- Gradle
- H2 (in-memory)
- PostgreSQL (for prod profile)
- Flyway
- Hibernate
