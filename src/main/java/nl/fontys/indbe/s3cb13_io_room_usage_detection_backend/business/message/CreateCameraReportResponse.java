package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCameraReportResponse {
    private Long cameraId;
}
