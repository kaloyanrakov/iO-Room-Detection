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

import java.util.ArrayList;
import java.util.List;

import static nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl.GetRoomEventStatus.getRoomEventStatus;

@Service
@AllArgsConstructor
public class GetAllRoomsUseCaseImpl implements GetAllRoomsUseCase {

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

        System.out.println(request.getStatus());

        if (request.getFloorNumber() == -1 && request.getStatus().isEmpty()) {
            List<Room> rooms =  roomApi.getAllRooms(request.getPlaceId(), request.getPageIndex() , request.getPageSize());

            String replacedEmail = "jupiter.eindhoven@iodigital.com";
            String replacementEmail = "Testruimte1.eindhoven@iodigital.com";


            List<MeetingRoom> meetingRooms = rooms.stream()
                    .map(room ->  {

                        if (room.getEmailAddress().equals(replacedEmail)) {
                            room.setEmailAddress(replacementEmail);
                        }

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

        List<Room> rooms = roomApi.getAllRooms(request.getPlaceId(), 0, Integer.MAX_VALUE); // Fetch all rooms

        List<MeetingRoom> filteredRooms = rooms.stream()
                .filter(room -> request.getFloorNumber() == -1 || extractFloorFromDisplayName(room.getDisplayName()) == request.getFloorNumber())
                .filter(room -> {
                    if (request.getStatus().isEmpty())
                    {
                        return true;
                    }
                    RoomEvent roomEvent = null ;

                    try {
                        roomEvent = getCurrentRoomEventUseCase.getCurrentRoomEvent(room.getEmailAddress());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    RoomEventStatus status = GetRoomEventStatus.getRoomEventStatus(roomEvent);
                    return status.name().equalsIgnoreCase(request.getStatus());
                })
                .map(room -> {
                    MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.getMeetingRoomEntityByEmail(room.getEmailAddress());
                    RoomEvent roomEvent = null;
                    try {
                        roomEvent = getCurrentRoomEventUseCase.getCurrentRoomEvent(room.getEmailAddress());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    RoomEventStatus status = GetRoomEventStatus.getRoomEventStatus(roomEvent);
                    return MeetingRoomConverter.convertMeetingRoom(room, meetingRoomEntity, roomEvent, status);
                })
                .toList();

        int start = request.getPageIndex() * request.getPageSize();
        int end = Math.min(start + request.getPageSize(), filteredRooms.size());
        List<MeetingRoom> paginatedRooms = start >= filteredRooms.size() ? new ArrayList<>() : filteredRooms.subList(start, end);

        return GetAllRoomsResponse.builder().rooms(paginatedRooms).build();
    }

    private int extractFloorFromDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return -1;
        }
        String[] parts = displayName.split(" - ");
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
