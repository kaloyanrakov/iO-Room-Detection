package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi;

import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.EventCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl.DateTimeConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventApi {
    private final GraphServiceClient graphServiceClient;

    public EventApi (GetGraphServiceClient getGraphClient) {
        graphServiceClient = getGraphClient.getGraphServiceClient();
    }

    public Event getCurrentRoomEvent(String roomEmail) {
        String nowUtc = DateTimeConverter.getCurrentUtcDateTimeAsString();

        String ongoingOrNextEventFilter = String.format(
                "(start/dateTime le '%s' and end/dateTime ge '%s') or start/dateTime ge '%s'",
                nowUtc, nowUtc, nowUtc
        );

        EventCollectionResponse result = graphServiceClient.users()
                .byUserId(roomEmail)
                .calendar()
                .events()
                .get(RequestConfiguration -> {
                    RequestConfiguration.queryParameters.filter = ongoingOrNextEventFilter;
                    RequestConfiguration.queryParameters.orderby = new String[]{"start/dateTime asc"};
                    RequestConfiguration.queryParameters.top = 1;
                });

        if (result.getValue() == null) {
            return null;
        }

        return result.getValue().get(0);
    }

    public List<Event> getRoomEvents(String roomEmail, LocalDateTime startDate, LocalDateTime endDate) {
        final String TIMEZONE_SUFFIX_UTC = "Z";

        String filter = String.format(
                "start/dateTime ge '%s' and end/dateTime le '%s'",
                DateTimeConverter.formatLocalDateTimeForApi(startDate, TIMEZONE_SUFFIX_UTC),
                DateTimeConverter.formatLocalDateTimeForApi(endDate, TIMEZONE_SUFFIX_UTC)
        );

        EventCollectionResponse result = graphServiceClient.users()
                .byUserId(roomEmail)
                .calendar()
                .events()
                .get(RequestConfiguration -> {
                    RequestConfiguration.queryParameters.filter = filter;
                    RequestConfiguration.queryParameters.orderby = new String[]{"start/dateTime asc"};
                });

        if (result.getValue() == null) {
            return null;
        }

        return result.getValue();
    }
}
