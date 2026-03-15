package br.com.alura.marketplace.domain.setup;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public interface RabbitMqSetup {

  RabbitMQContainer RABBIT_MQ = new RabbitMQContainer(DockerImageName
      .parse("rabbitmq:3.7.25-management-alpine"));

  @DynamicPropertySource
  static void rabbitmqDynamicPropertySource(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.host", RABBIT_MQ::getHost);
    registry.add("spring.rabbitmq.port", RABBIT_MQ::getAmqpPort);
    registry.add("spring.rabbitmq.username", RABBIT_MQ::getAdminUsername);
    registry.add("spring.rabbitmq.password", RABBIT_MQ::getAdminPassword);
  }

  @BeforeAll
  static void rabbitmqBeforeAll() {
    RABBIT_MQ.start();
  }


}