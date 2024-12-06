package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.CreateCameraConnectionUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.UpdateCameraConnectionUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.CameraConnectionRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.MeetingRoomRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.MeetingRoomEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateCameraConnectionUseCaseImpl implements UpdateCameraConnectionUseCase {

    private final CameraConnectionRepository cameraConnectionRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final CreateCameraConnectionUseCase createCameraConnectionUseCase;

    @Transactional
    @Override
    public CreateCameraConnectionResponse updateCameraConnection(CreateCameraConnectionRequest request) {
        if (!cameraConnectionRepository.existsCameraConnectionEntityByMacAddress(request.getMacAddress())) {
            try {
                return createCameraConnectionUseCase.createCameraConnection(request);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (meetingRoomRepository.isMacAddressAssignedToMeetingRoom(request.getMacAddress())) {
            CameraConnectionEntity cameraConnectionEntity = cameraConnectionRepository.findCameraConnectionEntityByMacAddress(request.getMacAddress());
            MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.findMeetingRoomEntitiesByCameraConnection(cameraConnectionEntity);

            meetingRoomRepository.updateCurrentCapacityByMeetingRoomId(meetingRoomEntity.getId(), request.getNrOfOccupants());
            cameraConnectionRepository.updateLastUpdated(cameraConnectionEntity.getId(), LocalDateTime.now());

            return CreateCameraConnectionResponse.builder()
                    .cameraId(cameraConnectionEntity.getId())
                    .build();
        }
        return null;
    }
}
