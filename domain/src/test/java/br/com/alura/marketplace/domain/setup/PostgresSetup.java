package br.com.alura.marketplace.domain.setup;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public interface PostgresSetup {

  PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest");

  //configuração das properties
  @DynamicPropertySource
  static void postgresDynamicPropertySource(DynamicPropertyRegistry registry){
    registry.add("spring.datasource.driverClassName", POSTGRES ::getDriverClassName);
    registry.add("spring.datasource.url", POSTGRES ::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES ::getUsername);
    registry.add("spring.datasource.password", POSTGRES ::getPassword);
    registry.add("spring.datasource.password", POSTGRES ::getPassword);

    registry.add("spring.flyway.enabled", ()-> "true");
    registry.add("spring.flyway.baseline-on-migrate", ()-> "true");
    registry.add("spring.flyway.locations", ()-> "classpath:/db/migrations");

    registry.add("spring.jpa.generate-ddl", ()-> "false"); //don't create tables automatically
    registry.add("spring.jpa.show-sql", ()-> "true"); //show queries
    registry.add("spring.jpa.hibernate.ddl-auto", ()-> "validate"); //validate ddl when service is creating
    registry.add("spring.jpa.properties.hibernate.hbm2ddl.create_namespace", ()-> "false"); //when you've a name space is possible to use it
  }

  @BeforeAll
  static void postgresBeforeAll(){
    POSTGRES.start();
  }

}
