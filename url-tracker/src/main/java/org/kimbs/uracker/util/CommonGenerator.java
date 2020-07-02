package org.kimbs.uracker.util;

import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommonGenerator {

    private static final AtomicInteger aiSerialNumberKey = new AtomicInteger();

    private static final Map<GeneratorKey, AtomicInteger> aiMap = new HashMap<>();

    public enum GeneratorKey {
        RECV_AT,
        RECV_FT,
        RECV_MT,

        SEND_AT,
        SEND_FT,

        @Deprecated
        SEND_MT,

        @Deprecated
        RESEND_AT,

        REPORT,

        TARGET,

        DB_AT,
        DB_MT,

        COMMON_LOG,
    }

    private static String REPORT_MQ_PREFIX = "CLIENT_REPORT_";

    public static String generateSerialNumber(String prefix, String serverName, String msgUid) {
        // M140_01122430123999_12345
        return new StringBuilder(prefix)
                .append(serverName).append("_")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmssSSS")))
                .append((aiSerialNumberKey.incrementAndGet() % 900 + 100)).append("_")
                .append(msgUid).toString();
    }

    public static String reportMqDestination(String clientId) {
        return REPORT_MQ_PREFIX + clientId;
    }

    public static Optional<String> getRoundRobinListValue(GeneratorKey key, List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        AtomicInteger ai = aiMap.get(key);
        if (ai == null) {
            ai = new AtomicInteger();
        }

        String value = list.get(ai.incrementAndGet() % list.size());
        aiMap.put(key, ai);

        return Optional.of(value);
    }

    public static int nextAiInteger(AtomicInteger ai, int min, int max) {
        if (ai.get() == max) {
            ai.set(min);
        }
        return ai.getAndIncrement();
    }

}
