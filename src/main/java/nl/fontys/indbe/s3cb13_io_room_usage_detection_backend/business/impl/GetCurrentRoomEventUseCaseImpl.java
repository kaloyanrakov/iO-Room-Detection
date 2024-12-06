package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import com.microsoft.graph.models.Event;
import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.GetCurrentRoomEventUseCase;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi.EventApi;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCurrentRoomEventUseCaseImpl implements GetCurrentRoomEventUseCase {

    private EventApi eventApi;

    @Override
    public RoomEvent getCurrentRoomEvent(String roomEmail) {
        Event event = eventApi.getCurrentRoomEvent(roomEmail);
        if (event == null) {
            return null;
        }

        RoomEvent roomEvent = RoomEventConverter.convertRoomEvent(event);
        return roomEvent;
    }
}
