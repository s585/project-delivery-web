package tech.itpark.project_delivery_web.framework.crypto;

public interface PasswordHasher {
  String hash(String raw, int saltLength);

  // raw, plaintext
  String hash(String raw);

  boolean matches(String hash, String raw);
}
