package dev.binhcn.header;

import dev.binhcn.proto.GreeterGrpc;
import dev.binhcn.proto.HelloReply;
import dev.binhcn.proto.HelloRequest;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that like {@link io.grpc.examples.helloworld.HelloWorldClient}.
 * This client can help you create custom headers.
 */
public class CustomHeaderClient {
  private static final Logger logger = Logger.getLogger(CustomHeaderClient.class.getName());

  private final ManagedChannel originChannel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  /**
   * A custom client.
   */
  private CustomHeaderClient(String host, int port) {
    originChannel = ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext()
        .build();
    ClientInterceptor interceptor = new HeaderClientInterceptor();
    Channel channel = ClientInterceptors.intercept(originChannel, interceptor);
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  private void shutdown() throws InterruptedException {
    originChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * A simple client method that like {@link io.grpc.examples.helloworld.HelloWorldClient}.
   */
  private void greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Greeting: " + response.getMessage());
  }

  /**
   * Main start the client from the command line.
   */
  public static void main(String[] args) throws Exception {
    // Access a service running on the local machine on port 50051
    CustomHeaderClient client = new CustomHeaderClient("localhost", 50051);
    try {
      String user = "world";
      // Use the arg as the name to greet if provided
      if (args.length > 0) {
        user = args[0]; 
      }
      client.greet(user);
    } finally {
      client.shutdown();
    }
  }
}