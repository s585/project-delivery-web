package tech.itpark.framework.http;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class ServerRequest {

    private static final String TOKEN = "token";

    private final HttpServletRequest original;
    private final RequestResponseReaderWriter rw;


    public String getToken() {
        return original.getHeader("Authorization").replace("Bearer_", "");
    }

    public <T> T read(Class<T> clazz) {
        return rw.read(clazz, original);
    }
}
