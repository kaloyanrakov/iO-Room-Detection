package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi;

import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.EventCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.impl.DateTimeConverter;
import org.springframework.stereotype.Service;

@Service
public class EventApi {
    private final GraphServiceClient graphServiceClient;

    public EventApi (GetGraphServiceClient getGraphClient) {
        graphServiceClient = getGraphClient.getGraphServiceClient();
    }

    public Event getCurrentRoomEvent(String roomEmail) {
        String startDateFilter = String.format("start/dateTime ge '%s'",
                DateTimeConverter.getCurrentUtcDateTimeAsString());

        String nowUtc = DateTimeConverter.getCurrentUtcDateTimeAsString();

        String ongoingOrNextEventFilter = String.format(
                "(start/dateTime le '%s' and end/dateTime ge '%s') or start/dateTime ge '%s'",
                nowUtc, nowUtc, nowUtc
        );


        EventCollectionResponse result = graphServiceClient.users()
                .byUserId(roomEmail)
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
}
