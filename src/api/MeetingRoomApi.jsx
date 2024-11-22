import axios from "axios";
import.meta.env.BASE_API_URL;

const MeetingRoomApi = {
    getAllMeetingRooms: (placeId, pageIndex, pageSize) => 
    axios.get(`${BASE_API_URL}/rooms`, {
        placeId: placeId,
        pageIndex: pageIndex,
        pageSize: pageSize
    }),
}

export default MeetingRoomApi