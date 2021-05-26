package tech.itpark.project_delivery_web.configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Configuration
public class JacksonAdapter {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .failOnUnknownProperties(false)
                .serializationInclusion(Include.NON_EMPTY)
                .serializerByType(Page.class, new JsonPageSerializer());
    }

    public static class JsonPageSerializer extends JsonSerializer<Page> {

        @Override
        public void serialize(Page page, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {

            ObjectMapper objectMapper = new ObjectMapper()
                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                    .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                    .setSerializationInclusion(Include.NON_EMPTY);
            jsonGen.writeStartObject();
            jsonGen.writeFieldName("size");
            jsonGen.writeNumber(page.getSize());
            jsonGen.writeFieldName("number");
            jsonGen.writeNumber(page.getNumber());
            jsonGen.writeFieldName("totalElements");
            jsonGen.writeNumber(page.getTotalElements());
            jsonGen.writeFieldName("last");
            jsonGen.writeBoolean(page.isLast());
            jsonGen.writeFieldName("totalPages");
            jsonGen.writeNumber(page.getTotalPages());
            jsonGen.writeObjectField("sort", page.getSort());
            jsonGen.writeFieldName("first");
            jsonGen.writeBoolean(page.isFirst());
            jsonGen.writeFieldName("numberOfElements");
            jsonGen.writeNumber(page.getNumberOfElements());
            jsonGen.writeFieldName("content");
            jsonGen.writeRawValue(objectMapper.writerWithView(serializerProvider.getActiveView())
                    .writeValueAsString(page.getContent()));
            jsonGen.writeEndObject();
        }

    }
}
