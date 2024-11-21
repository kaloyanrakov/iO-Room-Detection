package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi;

import com.microsoft.graph.models.Room;
import com.microsoft.graph.models.RoomCollectionResponse;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomApi {

    private final GraphServiceClient graphServiceClient;

    public RoomApi (GetGraphServiceClient getGraphClient) {
        graphServiceClient = getGraphClient.getGraphServiceClient();
    }

    public List<Room> getAllRooms(String placeId, int pageIndex, int pageSize) {
        if (placeId == null) {
            placeId = "rooms.eindhoven@iodigital.com";
        }

        RoomCollectionResponse result = graphServiceClient.places().
                byPlaceId(placeId)
                .graphRoomList()
                .rooms()
                .get(RequestConfiguration -> {
                    RequestConfiguration.queryParameters.skip = pageIndex * pageSize;
                    RequestConfiguration.queryParameters.top = pageSize;
                });

        return result.getValue();
    }

}
