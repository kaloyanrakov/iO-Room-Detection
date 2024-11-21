package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl;

import com.microsoft.graph.models.Event;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.domain.RoomEvent;

final class RoomEventConverter {
    private RoomEventConverter() {}

    public static RoomEvent convertRoomEvent(Event event) {
        RoomEvent roomEvent = RoomEvent.builder()
                .roomEmail(event.getLocation().getLocationUri())
                .startTime(DateTimeConverter.convertUtcToLocal(event.getStart().getDateTime(),event.getStart().getTimeZone()))
                .endTime(DateTimeConverter.convertUtcToLocal(event.getEnd().getDateTime(),event.getEnd().getTimeZone()))
                .build();
        return roomEvent;
    }
}
