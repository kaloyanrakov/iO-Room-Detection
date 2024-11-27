package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.CreateCameraConnectionUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.CreateCameraConnectionResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.CameraConnectionRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.MeetingRoomRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.MeetingRoomEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCameraConnectionUseCaseImpl implements CreateCameraConnectionUseCase {

    private final CameraConnectionRepository cameraConnectionRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Override
    public CreateCameraConnectionResponse createCameraConnection(CreateCameraConnectionRequest request) {

        if (meetingRoomRepository.isMacAddressAssignedToMeetingRoom(request.getMacAddress())) {
            CameraConnectionEntity cameraConnectionEntity = cameraConnectionRepository.findCameraConnectionEntityByMacAddress(request.getMacAddress());
            MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.findMeetingRoomEntitiesByCameraConnection(cameraConnectionEntity);

            meetingRoomRepository.updateCurrentCapacityByMeetingRoomId(meetingRoomEntity.getId(), request.getNrOfOccupants());

            return CreateCameraConnectionResponse.builder()
                    .cameraId(cameraConnectionEntity.getId())
                    .build();
        }

        if (!cameraConnectionRepository.existsCameraConnectionEntityByMacAddress(request.getMacAddress())) {
            CameraConnectionEntity cameraConnectionEntity =  saveNewCameraConnection(request);

            return CreateCameraConnectionResponse.builder()
                    .cameraId(cameraConnectionEntity.getId())
                    .build();
        }
        else {
            return null;
        }

    }


    private CameraConnectionEntity saveNewCameraConnection(CreateCameraConnectionRequest request) {
        CameraConnectionEntity cameraConnectionEntity = CameraConnectionEntity.builder()
                .macAddress(request.getMacAddress())
                .build();

        return cameraConnectionRepository.save(cameraConnectionEntity);
    }
}
