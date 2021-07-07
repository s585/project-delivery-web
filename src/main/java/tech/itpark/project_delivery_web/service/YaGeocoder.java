package tech.itpark.project_delivery_web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import tech.itpark.project_delivery_web.model.GeoObject;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class YaGeocoder {
    private static final String GEOCODER_HOST = "https://geocode-maps.yandex.ru/1.x/";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Stream<GeoObject> directGeocode(String geocode) {
        try {
            String apiKey = System.getenv("YAGEO");

            String format = "json";
            String url = GEOCODER_HOST + "?apikey=" + apiKey + "&format=" + format + "&geocode=" +
                    URLEncoder.encode(geocode, StandardCharsets.UTF_8);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            ResponseBody responseBody = client.newCall(request).execute().body();

            JsonNode node = mapper.readTree(responseBody.byteStream());
            JsonNode members = node.get("response").get("GeoObjectCollection").get("featureMember");
            if (!members.isArray()) {
                throw new RuntimeException("no results");
            }

            return StreamSupport.stream(members.spliterator(), false)
                    .map(o -> {
                        String name = o.get("GeoObject").get("name").asText();
                        String pos = o.get("GeoObject").get("Point").get("pos").asText();
                        GeoObject object = new GeoObject();
                        object.setName(name);
                        object.setPoint(pos);

                        return object;
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
