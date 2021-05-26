package tech.itpark.project_delivery_web.framework.security;

import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestAuth {
  private HttpServletRequestAuth() {}

  public static Auth auth(HttpServletRequest request) {
    return (Auth) request.getAttribute("AUTH");
  }
}
