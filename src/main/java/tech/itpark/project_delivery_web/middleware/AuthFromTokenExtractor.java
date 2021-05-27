package tech.itpark.project_delivery_web.middleware;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthFromTokenExtractor implements Middleware {

    @Override
    public boolean process(HttpServletRequest request, HttpServletResponse response) {
        final var token = (String) request.getAttribute("TOKEN");
//        final var auth = provider.provide(token);
//        request.setAttribute("AUTH", auth);
        return false;
    }
}
