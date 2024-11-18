package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository;

import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraConnectionRepository extends JpaRepository<CameraConnectionEntity, Long> {
}
