package tech.itpark.project_delivery_web.service;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.data.MediaSaveResponseDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Service
public class MediaService implements InitializingBean {
    private final Path path = Path.of("/opt/media");

    @Override
    public void afterPropertiesSet() throws Exception {
        Files.createDirectories(path);
    }

    public MediaSaveResponseDto save(Collection<Part> dto) {
        return null;
    }


}
