package com.ashok.Kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaAdminClient {

    private final AdminClient client ;


    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public KafkaAdminClient(String bootstrap) {

        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-lzvrd.us-west4.gcp.confluent.cloud:9092");
        //return new KafkaAdmin(configs);

        /*Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        props.put("request.timeout.ms", 3000);
        props.put("connections.max.idle.ms", 5000);*/

        this.client = AdminClient.create(configs);
    }

    public boolean verifyConnection() throws ExecutionException, InterruptedException {
        Collection<Node> nodes = this.client.describeCluster()
                .nodes()
                .get();
        return nodes != null && nodes.size() > 0;
    }


}
