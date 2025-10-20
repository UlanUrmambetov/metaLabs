package metalabs.metalabstest.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import metalabs.metalabstest.DTO.ImageDto;
import metalabs.metalabstest.model.User;
import metalabs.metalabstest.model.repository.ImageRepository;
import metalabs.metalabstest.model.repository.UserRepository;
import metalabs.metalabstest.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import metalabs.metalabstest.model.Image;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final UserRepository usersRepository;

    @SneakyThrows
    public String saveUploadedFile(MultipartFile file, String subDir) {

        String uuidFile = UUID.randomUUID().toString();
        String filename = uuidFile + "_" + file.getOriginalFilename();

        log.info("saveUploadedFile: originalName={}, storedName={}", file.getOriginalFilename(), filename);

        Path pathDir = Paths.get("data/" + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + filename);
        if (!Files.exists(filePath)) Files.createFile(filePath);

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String filename, String folder, MediaType mediaType) {
        try {
            Path path = Paths.get(folder).resolve(filename).normalize();
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public void create(ImageDto imageDto) throws IOException {
        String filename = saveUploadedFile(imageDto.getFile(), "images");

        Image image = new Image();
        image.setFileName(filename);

        Long userId = usersRepository.findById(imageDto.getUserId())
                .map(User::getId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + imageDto.getUserId()));
        image.setUserId(userId);

        imageRepository.save(image);
    }


}