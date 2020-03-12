# SpringBoot, Kafka ...
## :book: Todo List
```text
- ack-mode 종류에 따른 동작 방식
- rebalanceListener ?
- 리밸런싱 되는 기준 ? 전략 ?

- Message Header 는 보통 어디에 활용하는가?
- Custom-Header 사용 시 무조건 spring_json_header_types 는 자동으로 딸려오는 것인가?
- Kafka 보안 공부
    - JAAS, 
    - SASL
- Message Header의 변환된 값 -> byte[]
- Topic option들 이해
    - partition 
    - ...
- KafkaManager 설치해보기
- Replica 설정
    -ISR(In Sync Replica)
```
## :notebook_with_decorative_cover: complete!
```text
- partition=3 인 topic에 message 전송
    - Key 값이 null일 경우
        > 여러개의 리스너가 소비함
    - Key 값이 동일할 경우
        > 하나의 리스너가 소비함

- Concurrency
    - topic의 partition 수 = consumer의 수
    - 3개의 partition이 있는 topic이 있을 때
        - TOPIC: |partition-0|partition-1|partition-2| 각 파티션마다 처리하는 속도가 1초라고 가정
        - consumer가 1개라면, 약 3초+@ 걸림,
        - consumer가 3개라면, 약 1초+@ 걸림

- Message Key
    - 카프카는 유사한 키를 갖고 있는 메시지가 동일한 파티션으로 전송되도록 하며, 메시지 키의 해시(Hash)값을 산출하고 해당 파티션에 메시지를 추가한다.
    - 파티션에 대한 메시지 매핑은 전적으로 메시지 키를 사용해 생성된 해시 코드에 달려있고, 메시지 키는 동일한 키를 사용하는 메시지가 동일한 파티션으로 기록되는 것을 보장한다.
    - Key 값이 null일 경우 라운드 로빈 방식으로 파티션에 배분한다.

- Topic에 partition이 2개 이상일 경우
    - 메시지의 시간적 순서는 토픽에 대해서는 보장되지 않는다.
    - 단, partition 안에서는 항상 보장된다.
    - 만약 순서가 중요하다면, partition을 1개로 두자?
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
      # 메시지 압축 타입: none(사용안함), gzip, lz4, snappy, zstd(version >= 2.1.0 추가)
      ## 자세한 내용은 kafka compression benchmark 검색..
      compression-type: gzip
      # 버퍼 메모리
      buffer-memory: 33554432
      # key, value Serializer
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 지정한 milliseconds 데이터 모음, batch-size 를 넘길경우 즉시 전송 
      linger-ms: 500
      # -1, 0, 1, all(대소문자 구분)
      ## 0  : 매우 빠르지만, 파티션의 리더가 받았는지 알 수 없음
      ## 1  : 메시지 전송도 빠른편, 파티션의 리더가 받았는지 확인 가능 (강추)
      ## all: 전송속도 가증 느림, 손실 없는 메시지 전송 가능
      acks: all
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