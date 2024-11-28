package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import com.microsoft.graph.models.Room;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetCurrentRoomEventUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetMeetingRoomUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.exception.InvalidRoomIdException;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetMeetingRoomResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi.RoomApi;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.MeetingRoom;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEvent;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEventStatus;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.MeetingRoomRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.CameraConnectionEntity;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.MeetingRoomEntity;
import org.springframework.stereotype.Service;

import static nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl.GetRoomEventStatus.getRoomEventStatus;

@Service
@AllArgsConstructor
public class GetMeetingRoomUseCaseImpl implements GetMeetingRoomUseCase {

    private final MeetingRoomRepository meetingRoomRepository;
    private final RoomApi roomApi;
    private final GetCurrentRoomEventUseCase getCurrentRoomEventUseCase;


    @Override
    public GetMeetingRoomResponse getMeetingRoom(long roomId) {
        MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.findById(roomId).orElse(null);

        if (roomId == 1) {
            meetingRoomEntity = MeetingRoomEntity.builder()
                    .id(1L)
                    .email("jupiter.eindhoven@iodigital.com")
                    .currentCapacity(0)
                    .cameraConnection(CameraConnectionEntity.builder()
                            .id(1L)
                            .macAddress("00-50-56-C0-00-01")
                            .build())
                    .build();
        }

        if (meetingRoomEntity == null) {
            throw new InvalidRoomIdException("Invalid Room Id");
        }

        Room room = getRoom(meetingRoomEntity);

        RoomEvent roomEvent = null;
        try {
            roomEvent = getCurrentRoomEventUseCase.getCurrentRoomEvent(room.getEmailAddress());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        RoomEventStatus status = getRoomEventStatus(roomEvent);

        MeetingRoom meetingRoom = MeetingRoomConverter.convertMeetingRoom(room, meetingRoomEntity, roomEvent, status);
        return GetMeetingRoomResponse.builder().meetingRoom(meetingRoom).build();
    }

    private Room getRoom(MeetingRoomEntity meetingRoomEntity) {
        String replacedEmail = "jupiter.eindhoven@iodigital.com";
        String replacementEmail = "Testruimte1.eindhoven@iodigital.com";

        if (meetingRoomEntity.getEmail().equals(replacementEmail)) {
            return roomApi.getRoom(replacedEmail);
        }
         return roomApi.getRoom(meetingRoomEntity.getEmail());

    }
}
