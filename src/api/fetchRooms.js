import axios from 'axios';

const fetchRooms = async () => {
    try {
        const response = await axios.get('http://localhost:8080/rooms?pageSize=12');
        console.log('Fetched rooms:', response.data);
        return response.data.rooms;
    } catch (error) {
        console.error('Error fetching rooms:', error);
        throw error;
    }
};
export default fetchRooms;