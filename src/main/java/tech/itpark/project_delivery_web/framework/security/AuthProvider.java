package tech.itpark.project_delivery_web.framework.security;

@FunctionalInterface
public interface AuthProvider {
  Auth provide(String token);
}
