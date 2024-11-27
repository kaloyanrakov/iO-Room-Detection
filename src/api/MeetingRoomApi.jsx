import axios from "axios";

const BASE_API_URL = "https://graph.microsoft.com/v1.0";

const MeetingRoomApi = {
    getAllMeetingRooms: (placeId, pageIndex, pageSize) => 
    axios.get(`${BASE_API_URL}/places/microsoft.graph.room`, {
        placeId: placeId,
        pageIndex: pageIndex,
        pageSize: pageSize
    }),
}

export default MeetingRoomApi