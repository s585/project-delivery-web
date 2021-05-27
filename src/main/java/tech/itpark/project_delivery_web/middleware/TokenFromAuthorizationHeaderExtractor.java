package tech.itpark.project_delivery_web.middleware;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenFromAuthorizationHeaderExtractor implements Middleware {
  @Override
  public boolean process(HttpServletRequest request, HttpServletResponse response) {
    final var token = request.getHeader("Authorization");
    request.setAttribute("TOKEN", token);
    return false;
  }
}
