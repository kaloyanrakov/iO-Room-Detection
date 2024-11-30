package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetCameraConnectionsUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraReportRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraReportResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetCameraConnectionsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cameras")
@AllArgsConstructor
public class CamerasController {
    private final GetCameraConnectionsUseCase getCameraConnectionsUseCase;

    @PostMapping
    public ResponseEntity<CreateCameraReportResponse> createCameraReport(@Valid @RequestBody CreateCameraReportRequest request) {
        CreateCameraReportResponse response = CreateCameraReportResponse.builder().cameraId(1L).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetCameraConnectionsResponse> getCameraConnections() {
        GetCameraConnectionsResponse response = this.getCameraConnectionsUseCase.getCameraConnections();
        return ResponseEntity.ok(response);
    }
}
