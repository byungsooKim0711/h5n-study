package com.humuson.imc;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@EmbeddedKafka
@SpringBootTest
class TopicCreatorApplicationTests {

	AdminClient adminClient;

	@Value("${spring.embedded.kafka.brokers}")
	String brokers;

	@BeforeEach
	void setUp() {
		// Embedded Kafka Admin 설정
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		adminClient = AdminClient.create(props);
	}

	@AfterEach
	void tearDown() {
		adminClient.close();
	}


	@Test
	@DisplayName("application.yml 파일에서 읽은 데이터를 기반으로 토픽 이름이 제대로 생성되었는지 확인")
	void testCreateTopicName() throws Exception {
		// arrange

		// act

		// assert
		Collection<String> embeddedTopics = adminClient.listTopics().names().get();

		assertThat(embeddedTopics).contains("example1", "example2", "example3");
	}

	@Test
	@DisplayName("application.yml 파일에서 읽은 데이터를 기반으로 파티션 갯수가 제대로 생성되었는지 확인")
	void testCreateTopicPartitionSize() throws Exception {
		// arrange

		// act

		// assert
		DescribeTopicsResult topicsResult = adminClient.describeTopics(Arrays.asList("example1", "example2", "example3"));

		assertEquals(2, topicsResult.values().get("example1").get().partitions().size());
		assertEquals(10, topicsResult.values().get("example2").get().partitions().size());
		assertEquals(1, topicsResult.values().get("example3").get().partitions().size());
	}

	@Test
	@DisplayName("Replica-Factor 는 어떻게 검증하나?")
	void testCreateTopicReplicaFactor() {
		// arrange

		// act

		// assert
	}

}
