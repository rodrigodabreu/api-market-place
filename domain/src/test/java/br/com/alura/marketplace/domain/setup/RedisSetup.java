package br.com.alura.marketplace.domain.setup;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

public interface RedisSetup {

  RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:6.2.6"));

  //configuração das properties
  @DynamicPropertySource
  static void localstackDynamicPropertySource(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
    registry.add("spring.data.redis.portß", REDIS_CONTAINER::getRedisPort);
  }

  @BeforeAll
  static void localStackBeforeAll() {
    REDIS_CONTAINER.start();
  }
}
