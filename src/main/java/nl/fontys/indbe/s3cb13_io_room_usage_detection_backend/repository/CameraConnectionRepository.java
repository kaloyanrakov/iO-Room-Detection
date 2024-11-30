package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository;

import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CameraConnectionRepository extends JpaRepository<CameraConnectionEntity, Long> {
    @Query("select cc from MeetingRoomEntity mr join mr.cameraConnection cc where mr.cameraConnection.id != cc.id")
    List<CameraConnectionEntity> getUnassignedCameraConnections();
}
