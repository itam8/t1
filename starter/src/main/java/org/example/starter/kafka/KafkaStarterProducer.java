package org.example.starter.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.example.starter.dto.ErrorLogDto;
import org.example.starter.dto.HttpRequestLogDto;
import org.example.starter.service.ErrorLogService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaStarterProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ErrorLogService errorLogService;

    public void sendServiceLogMessage(ErrorLogDto message) {
        try {
            ProducerRecord<String, Object> record =
                    new ProducerRecord<>("service_logs", message.getServiceName(), message.getMessage());
            record.headers().add(new RecordHeader(message.getHeaderKey(), message.getHeaderValue().getBytes()));

            kafkaTemplate.send(record);
        } catch (KafkaException e) {
            errorLogService.create(message);
            log.error(e.getMessage());
        }
    }

    public void sendServiceLogMessage(HttpRequestLogDto message) {
        ProducerRecord<String, Object> record =
                new ProducerRecord<>("service_logs", message.getServiceName(), message.getMessage());
        record.headers().add(new RecordHeader(message.getHeaderKey(), message.getHeaderValue().getBytes()));

        kafkaTemplate.send(record);
    }
}
