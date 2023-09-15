import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-lzvrd.us-west4.gcp.confluent.cloud:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Deserializer keyDeserializer=null;
        Deserializer valueDeserializer = null;
        String messageFormat = "avro"; // set the message format to either "json", "plaintext", or "avro"
        if (messageFormat.equals("json")) {
            keyDeserializer = new StringDeserializer();
          //  valueDeserializer = new JSONDeserializer<>();
        } else if (messageFormat.equals("plaintext")) {
            keyDeserializer = new StringDeserializer();
            valueDeserializer = new StringDeserializer();
        } else if (messageFormat.equals("avro")) {
            // keyDeserializer = new AvroDeserializer();
            // valueDeserializer = new AvroDeserializer();
        } else {
            throw new IllegalArgumentException("Invalid message format: " + messageFormat);
        }

        // Create a KafkaConsumer with the key and value deserializers
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props, keyDeserializer, valueDeserializer);

        // Subscribe to the topic
        String topic = "test-topic";
        consumer.subscribe(Collections.singletonList(topic));

        // Start consuming messages
        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(1000);
            for (ConsumerRecord<String, Object> record : records) {
                System.out.printf("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }
}
