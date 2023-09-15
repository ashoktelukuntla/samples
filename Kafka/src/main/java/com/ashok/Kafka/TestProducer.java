package com.ashok.Kafka;

//import guru.learningjournal.kafka.examples.JsonSerializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class TestProducer {
    public static void main(String args[]) throws InterruptedException {

        Properties properties = new Properties();

            //properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-lzvrd.us-west4.gcp.confluent.cloud:9092");
           // properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafka-source-consumer-1");
            //properties.put(ConsumerConfig.GROUP_ID_CONFIG, "DPKafkaProj-1");
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put("sasl.mechanism", "PLAIN");
            properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + "3CYW7WCVIT6D5VGO" + "\" password=\"" + "QfrXcO2N7Fl7hJPpQcZRb3Eo7eMxOQVLhB4d/zy5BWahX6EBeKkQiU0L9xLv2Rht" + "\";");
            properties.put("security.protocol", "SASL_SSL");
            properties.put("client.dns.lookup","use_all_dns_ips");
           // properties.put("schema.registry.url","https://psrc-em82q.us-east-2.aws.confluent.cloud");
           // properties.put("basic.auth.credentials.source","lsrc-nwnmzv");
           // properties.put("basic.auth.user.info","GLCUDRFRY64UZ6N6:szavwSgnw/CUM3XytSRIid5wlaXqeJWM/67ilPSARUf2/WGGt8CVHq5+X7l0UCP8");
            Producer<String, String> producer = new KafkaProducer
                <String, String>(properties);
        Consumer<String, String> consumer = new KafkaConsumer
                <String, String>(properties);

        for(int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<String, String>("my-topic-2",
                    Integer.toString(i), Integer.toString(i)));
            //Thread.sleep(200);
            System.out.println("Message -> " + Integer.toString(i) + " sent successfully");
        }
        producer.close();

       }
    }

