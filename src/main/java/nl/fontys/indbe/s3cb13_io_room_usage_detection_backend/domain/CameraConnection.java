package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CameraConnection {
    Long id;
    String macAddress;
}
