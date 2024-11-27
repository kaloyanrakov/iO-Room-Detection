package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.CreateCameraConnectionUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cameras")
@AllArgsConstructor
public class CamerasController {

    private final CreateCameraConnectionUseCase createCameraConnectionUseCase;

    @PostMapping
    public ResponseEntity<CreateCameraConnectionResponse> createCameraConnection(@Valid @RequestBody CreateCameraConnectionRequest request) {
        CreateCameraConnectionResponse response = createCameraConnectionUseCase.createCameraConnection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
