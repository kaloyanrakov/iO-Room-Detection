package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.controller;

import lombok.AllArgsConstructor;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.exception.InvalidRoomIdException;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl.GetRoomEventsUseCaseImpl;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetRoomEventsRequest;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.message.GetRoomEventsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventsController {

    private GetRoomEventsUseCaseImpl getRoomEventsUseCase;

    @GetMapping("{roomId}")
    public ResponseEntity<GetRoomEventsResponse> getRoomEvents(@PathVariable long roomId,
                                                               @RequestParam(value = "startDate", required = false) String startDate,
                                                               @RequestParam(value = "endDate", required = false) String endDate) {
        GetRoomEventsRequest request;

        if (startDate != null && endDate != null) {
            request = GetRoomEventsRequest.builder()
                    .roomId(roomId)
                    .startTime(LocalDateTime.parse(startDate))
                    .endTime(LocalDateTime.parse(endDate))
                    .build();
        }
        else if (startDate != null) {
            LocalDateTime startTime = LocalDateTime.parse(startDate);
            LocalDateTime endTime = LocalTime.MAX.atDate(LocalDate.parse(startDate));
            request = GetRoomEventsRequest.builder()
                    .roomId(roomId)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
        }
        else{
            request = GetRoomEventsRequest.builder()
                    .roomId(roomId)
                    .startTime(LocalDate.now().atStartOfDay())
                    .endTime(LocalTime.MAX.atDate(LocalDate.now()))
                    .build();
        }

        GetRoomEventsResponse response = getRoomEventsUseCase.getRoomEvents(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(InvalidRoomIdException.class)
    @ResponseBody
    public ResponseEntity<Object> handleInvalidRoomIdException(InvalidRoomIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
