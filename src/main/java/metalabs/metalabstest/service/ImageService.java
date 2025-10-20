package metalabs.metalabstest.service;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface ImageService {
    ResponseEntity<Resource> downloadFile(String filename, String subDir, MediaType mediaType);

}
