package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import com.microsoft.graph.models.Event;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetRoomEventsUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.exception.InvalidRoomIdException;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetRoomEventsRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetRoomEventsResponse;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi.EventApi;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEvent;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.MeetingRoomRepository;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.MeetingRoomEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRoomEventsUseCaseImpl implements GetRoomEventsUseCase {

    private final MeetingRoomRepository meetingRoomRepository;
    private final EventApi eventApi;

    @Override
    public GetRoomEventsResponse getRoomEvents(GetRoomEventsRequest request) {
        MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.findById(request.getRoomId()).orElse(null);

        if  (meetingRoomEntity == null) {
            throw new InvalidRoomIdException("Invalid room id");
        }

        List<Event> roomEvents = getRoomEvents(meetingRoomEntity, request);

        List<RoomEvent> roomEventList = roomEvents.stream()
                .map(RoomEventConverter::convertRoomEvent)
                .toList();

        return GetRoomEventsResponse.builder().roomEvents(roomEventList).build();
    }

    private List<Event> getRoomEvents(MeetingRoomEntity meetingRoomEntity, GetRoomEventsRequest request) {
        String replacedEmail = "jupiter.eindhoven@iodigital.com";
        String replacementEmail = "Testruimte1.eindhoven@iodigital.com";

        if (meetingRoomEntity.getEmail().equals(replacedEmail)) {
            return eventApi.getRoomEvents(replacementEmail, request.getStartTime(), request.getEndTime());
        }
        else {
            return eventApi.getRoomEvents(meetingRoomEntity.getEmail(),request.getStartTime(),request.getEndTime());
        }
    }
}
