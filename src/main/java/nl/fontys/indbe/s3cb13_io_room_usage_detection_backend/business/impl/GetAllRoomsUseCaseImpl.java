package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import com.microsoft.graph.models.Room;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetAllRoomsUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetAllRoomsRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetAllRoomsResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi.RoomApi;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.MeetingRoom;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEvent;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEventStatus;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.MeetingRoomRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.MeetingRoomEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GetAllRoomsUseCaseImpl implements GetAllRoomsUseCase {

    private final long OFFSET_FOR_OCCUPIED_SOON = 30;

    private MeetingRoomRepository meetingRoomRepository;

    private RoomApi roomApi;

    private GetCurrentRoomEventUseCaseImpl getCurrentRoomEventUseCase;



    /**
     * @return response
     *
     * @should return List of MeetingRooms based on the placeId
     *
     * @should return InvalidPlaceException when placeId is invalid
     */

    @Override
    public GetAllRoomsResponse getAllRooms(GetAllRoomsRequest request) {
        if (request.getPlaceId() == null || request.getPlaceId().isEmpty()) {
            request.setPlaceId("rooms.eindhoven@iodigital.com");
        }

        List<Room> rooms = roomApi.getAllRooms(request.getPlaceId(), request.getPageIndex() , request.getPageSize());

        List<MeetingRoom> meetingRooms = rooms.stream()
                .map(room ->  {
                    MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.getMeetingRoomEntityByEmail(room.getEmailAddress());
                    RoomEvent roomEvent = null;
                    try {
                        roomEvent = getCurrentRoomEventUseCase.getCurrentRoomEvent(room.getEmailAddress());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    RoomEventStatus status = getRoomEventStatus(roomEvent);
                    return MeetingRoomConverter.convertMeetingRoom(room, meetingRoomEntity,roomEvent, status);
        }).toList();

        return GetAllRoomsResponse.builder().rooms(meetingRooms).build();
    }

    private RoomEventStatus getRoomEventStatus(RoomEvent roomEvent) {
        LocalDateTime now = LocalDateTime.now();

        if (roomEvent == null) {
            return RoomEventStatus.AVAILABLE;
        }

        LocalDateTime startTime = roomEvent.getStartTime();
        LocalDateTime endTime = roomEvent.getEndTime();

        if ((startTime.isBefore(now) || startTime.equals(now)) && endTime.isAfter(now)) {
            return RoomEventStatus.OCCUPIED_NOW;
        }
        LocalDateTime offset = startTime.minusMinutes(OFFSET_FOR_OCCUPIED_SOON);
        if (startTime.isAfter(now) && (offset.isAfter(now) || offset.equals(now))) {
                return RoomEventStatus.OCCUPIED_SOON;
        }
        return RoomEventStatus.AVAILABLE;
    }

}
