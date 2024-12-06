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

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GetRoomEventsUseCaseImpl implements GetRoomEventsUseCase {

    private final MeetingRoomRepository meetingRoomRepository;
    private final EventApi eventApi;

    @Override
    public GetRoomEventsResponse getRoomEvents(GetRoomEventsRequest request) {
        String email = getEmailFromRequest(request);

        List<Event>roomEvents = getRoomEventsApi(email, request.getStartTime(), request.getEndTime());

        List<RoomEvent> roomEventList = roomEvents.stream()
                .map(RoomEventConverter::convertRoomEvent)
                .toList();

        return GetRoomEventsResponse.builder().roomEvents(roomEventList).build();
    }

    private String getEmailFromRequest(GetRoomEventsRequest request) {
        if (request.getRoomEmail() != null) {
            return request.getRoomEmail();
        }
        MeetingRoomEntity meetingRoomEntity = meetingRoomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new InvalidRoomIdException("Invalid room ID: " + request.getRoomId()));
        return meetingRoomEntity.getEmail();
    }

    private List<Event> getRoomEventsApi(String email, LocalDateTime startTime, LocalDateTime endTime) {
        String resolvedEmail = getEmail(email);
        return eventApi.getRoomEvents(resolvedEmail, startTime, endTime);
    }

    private String getEmail(String email) {
        String replacedEmail = "jupiter.eindhoven@iodigital.com";
        String replacementEmail = "Testruimte1.eindhoven@iodigital.com";
        return replacedEmail.equals(email) ? replacementEmail : email;
    }
}
