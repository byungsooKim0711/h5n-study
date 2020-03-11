# SpringBoot, Kafka ...
## :book: Todo List
```text
- Concurrency
    - 단순히 Listener의 쓰레드로 생각해도 되는가?
    - 4개의 partition이 있는 topic을 concurrency가 3인 컨슈머 어플리케이션을 하나만 띄웠을 경우 성능적인 이슈가 있는가?
        - 즉 topic의 partition의 갯수만큼 concurrency가 있어야 하거나
        - 총 concurrency의 배수가 topic의 partition의 갯수와 일치해야 하거나..
        - concurrency의 수 >= partition의 수 이여야 하거나... 그런게 있을까..?
- Message Header 는 보통 어디에 활용하는가?
- Message Key 값의 활용 용도는?
- Custom-Header 사용 시 무조건 spring_json_header_types 는 자동으로 딸려오는 것인가?
- Kafka 보안 공부
    - JAAS, 
    - SASL
- Message Header의 변환된 값 -> byte[]
- Topic option들 이해
    - partition 
    - ...
- KafkaManager 설치해보기
```
## :notebook_with_decorative_cover: complete!
```text
- partition=3 인 topic에 message 전송
    - Key 값이 null일 경우
        > 여러개의 리스너가 소비함
    - Key 값이 동일할 경우
        > 하나의 리스너가 소비함 
```
## Producer
- **application.yml**
```yaml
spring:
  kafka:
     # 카프카 설치 서버
    bootstrap-servers:
      - 127.0.0.1:9092
    producer:
      retries: 0
      # 배치 크기
      batch-size: 1000 
      compression-type: gzip
      # 버퍼 메모리
      buffer-memory: 33554432
      # key, value Serializer
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 지정한 milliseconds 데이터 모음, batch-size 를 넘길경우 즉시 전송 
      linger-ms: 500

server:
  port: 8080
```
- **Message Send**
```java
@SpringBootApplication
@Slf4j
@RestController
public class KafkaProducerApplication {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public void sendMessage() {
        for (int i=0; i<1000; i++) {
            Message<String> message = MessageBuilder
                    .withPayload("" + i) // value
                    .setHeader(KafkaHeaders.TOPIC, "kbs") // topic
                    .setHeader(KafkaHeaders.MESSAGE_KEY, ""+i) // key
                    .setHeader("X-Custom-Header", "WC1DdXN0b20tSGVhZGVy") // custom header -> spring_json_header_types 는 자동으로 딸려오는 것인가?
                    .build();

            // 비동기 전송
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
//            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("kbs", ""+i, ""+i);

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("Send fail topic : {}", "kbs", throwable.getCause());
                }

                @Override
                public void onSuccess(SendResult<String, String> stringStringSendResult) {
                    log.info("Send message : {}", stringStringSendResult.toString());
                }
            });
        }
    }
}
```
## Consumer
- **application.yml**
```yaml
spring:
  kafka:
    # 카프카 설치 서버
    bootstrap-servers:
      - 127.0.0.1:9092
    consumer:
      # 컨슈머 그룹 아이디
      group-id: kimbs
      # 자동 커밋 설정
      enable-auto-commit: false
      # 한번에 최대로 읽을 수 있는 양
      max-poll-records: 500
      # key, value Serializer
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    listener:
      # 리스너 갯수(?) 쓰레드로 돌아감
      concurrency: 1
      ack-mode: manual-immediate
      poll-timeout: 3000
      # KafkaProperties 에는 없음 - Kafka config 에서 사용 중
      batch: true

server:
  port: 8082

```
- **Message Listener**
```java
@Service
@Slf4j
public class ReportConsumer {

    @KafkaListener(topics = "kbs", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecords<String, String> list, Acknowledgment acknowledgment) throws Exception {

        list.forEach(record -> {
            log.info("[Message Info] Header: {}, Partition: {}, Offset: {}, Topic: {},  Key: {}, Value: {}", record.headers(), record.partition(), record.offset(), record.topic(), record.key(), record.value());
        });
        log.info("Consumed message size: {}", list.count());

        // 수동 ack
        acknowledgment.acknowledge();
    }
}
```