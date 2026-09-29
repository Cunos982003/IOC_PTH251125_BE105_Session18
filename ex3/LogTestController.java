package com.example.loggingdemo.controller;

import com.example.loggingdemo.appender.DiscordAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/test-error")
    public String triggerError() {
        log.error("Lỗi DB: Không thể kết nối tới cơ sở dữ liệu!");

        // Gửi thử trực tiếp bằng Appender để test Webhook
        DiscordAppender appender = new DiscordAppender();
        appender.setWebhookUrl("https://discord.com/api/webhooks/1554306241625460766/12w38e4y6QK9w1RqvkiIDADwdzByNkuzt9JEspP9n6N-ZG8zWFZ3aqD1KIusVyums7y2");
        appender.start();

        // Tạo thử 1 event log giả lập để test
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        appender.doAppend(new ch.qos.logback.classic.spi.LoggingEvent(
                "ch.qos.logback.classic.Logger",
                rootLogger,
                ch.qos.logback.classic.Level.ERROR,
                "TEST GỬI TRỰC TIẾP TỪ CONTROLLER",
                null,
                null
        ));

        return "Error triggered";
    }
}
