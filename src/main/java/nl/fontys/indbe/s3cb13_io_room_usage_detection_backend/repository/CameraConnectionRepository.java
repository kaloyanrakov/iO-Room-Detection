package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CameraConnectionRepository extends JpaRepository<CameraConnectionEntity, Long> {
    @Query("select cc from MeetingRoomEntity mr join mr.cameraConnection cc where mr.cameraConnection.id != cc.id")
    List<CameraConnectionEntity> getUnassignedCameraConnections();
    boolean existsCameraConnectionEntityByMacAddress(@NotBlank @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$", message = "invalid format") String macAddress);

    CameraConnectionEntity findCameraConnectionEntityByMacAddress(@NotBlank @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$", message = "invalid format") String macAddress);
}
