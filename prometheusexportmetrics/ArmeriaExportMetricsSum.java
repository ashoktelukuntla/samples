package org.opensearch.dataprepper.plugins.health;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest;
import io.opentelemetry.proto.collector.metrics.v1.MetricsServiceGrpc;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.common.v1.InstrumentationLibrary;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.metrics.v1.InstrumentationLibraryMetrics;
import io.opentelemetry.proto.metrics.v1.NumberDataPoint;
import io.opentelemetry.proto.metrics.v1.ResourceMetrics;
import io.opentelemetry.proto.metrics.v1.Sum;
import io.opentelemetry.proto.resource.v1.Resource;

public class ArmeriaExportMetricsSum {

    public static void main(String args[]) {

        NumberDataPoint.Builder p1 = NumberDataPoint.newBuilder().setAsDouble(6);
        p1.setTimeUnixNano(System.currentTimeMillis());
        p1.setStartTimeUnixNano(System.currentTimeMillis());
        p1.addAttributes(KeyValue.newBuilder()
                .setKey("MyLabelKey.From.Sum")
                .setValue(AnyValue.newBuilder().setStringValue("MyLabelValueFromSum").build()));

        Sum sum = Sum.newBuilder().addDataPoints(p1).build();

        io.opentelemetry.proto.metrics.v1.Metric.Builder metricBuilder =
                io.opentelemetry.proto.metrics.v1.Metric.newBuilder()
                        .setSum(sum)
                        .setName("MySampleMetricsSum")
                        .setUnit("seconds")
                        .setDescription("description");

        Resource resource = Resource.newBuilder()
                .addAttributes(KeyValue.newBuilder()
                        .setKey("service.name")
                        .setValue(AnyValue.newBuilder().setStringValue("MySampleService").build())
                ).build();


        InstrumentationLibraryMetrics instrumentationLibraryMetrics = InstrumentationLibraryMetrics.newBuilder()
                .addMetrics(metricBuilder)
                .setInstrumentationLibrary(InstrumentationLibrary.newBuilder()
                        .setName("my.library.name")
                        .setVersion("1.1")
                        .build())
                .build();

        ResourceMetrics resourceMetrics = ResourceMetrics.newBuilder()
                .addInstrumentationLibraryMetrics(instrumentationLibraryMetrics)
                .setResource(resource)
                .build();

        ExportMetricsServiceRequest exportMetricRequest = ExportMetricsServiceRequest.newBuilder()
                .addResourceMetrics(resourceMetrics).build();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 21891) // Replace with the actual server address and port
                .usePlaintext().build();

        MetricsServiceGrpc.MetricsServiceBlockingStub metricsClient = MetricsServiceGrpc.newBlockingStub(channel);

        metricsClient.export(exportMetricRequest);

        channel.shutdown();
    }

}

