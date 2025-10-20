package metalabs.metalabstest.model.repository;

import metalabs.metalabstest.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long Id);

    Optional<Image> findByUserId(Long userId);

}
