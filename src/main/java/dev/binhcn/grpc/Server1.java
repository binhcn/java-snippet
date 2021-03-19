package dev.binhcn.grpc;

import dev.binhcn.proto.GreeterGrpc;
import dev.binhcn.proto.HelloReply;
import dev.binhcn.proto.HelloRequest;
import io.grpc.Context;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server1 {
  private static final Logger logger = Logger.getLogger(Server1.class.getName());

  private static final int PORT = 50051;
  private Server server;

  private void start() throws IOException {
    server = ServerBuilder.forPort(PORT)
        .addService(ServerInterceptors.intercept(new GreeterImpl()))
        .build()
        .start();
    logger.info("Server started, listening on " + PORT);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          Server1.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    final Server1 server = new Server1();
    server.start();
    server.blockUntilShutdown();
  }

  private static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {


      ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
          .usePlaintext()
          .build();
      GreeterGrpc.GreeterBlockingStub blockingStub = GreeterGrpc.newBlockingStub(channel);

//      try {
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }

//      Context ctx = Context.current().fork();
//      ctx.run(() -> {
//        HelloReply response = blockingStub
//            .withDeadline(Deadline.after(5, TimeUnit.SECONDS))
//            .sayHello(req);
//        HelloReply reply = HelloReply.newBuilder().setMessage("Server1: " + response.getMessage()).build();
//        responseObserver.onNext(reply);
//        responseObserver.onCompleted();
//      });



      HelloReply response = blockingStub
          .withDeadline(Deadline.after(1, TimeUnit.SECONDS))
          .sayHello(req);



      HelloReply reply = HelloReply.newBuilder().setMessage("Server1: " + response.getMessage()).build();

      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }
}