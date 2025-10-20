package metalabs.metalabstest.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class ImageDto {
    private Long id;
    private Long userId;
    private MultipartFile file;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
