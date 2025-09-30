package org.example.accountprocessing.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.accountprocessing.dto.ClientCardDto;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, ClientProductDto> clientProductsConsumerFactory() {
        final String VALUE_DEFAULT_TYPE = "org.example.accountprocessing.dto.ClientProductDto";
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(VALUE_DEFAULT_TYPE));
    }

    private Map<String, Object> consumerConfigs(String valueDefaultType) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "account-processing");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueDefaultType);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        return config;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientProductDto> clientProductsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClientProductDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientProductsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ClientCardDto> clientCardsConsumerFactory() {
        final String VALUE_DEFAULT_TYPE = "org.example.accountprocessing.dto.ClientCardDto";
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(VALUE_DEFAULT_TYPE));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientCardDto> clientCardsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClientCardDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientCardsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Transaction> clientTransactionsConsumerFactory() {
        final String VALUE_DEFAULT_TYPE = "org.example.accountprocessing.model.Transaction";
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(VALUE_DEFAULT_TYPE));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> clientTransactionsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientTransactionsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Payment> clientPaymentsConsumerFactory() {
        final String VALUE_DEFAULT_TYPE = "org.example.accountprocessing.model.Payment";
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(VALUE_DEFAULT_TYPE));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Payment> clientPaymentsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Payment> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientPaymentsConsumerFactory());
        return factory;
    }
}
