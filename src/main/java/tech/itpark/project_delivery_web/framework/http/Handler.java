package tech.itpark.project_delivery_web.framework.http;

@FunctionalInterface
public interface Handler {
  void handle(ServerRequest request, ServerResponse response) throws Exception;
}
