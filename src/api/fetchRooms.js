import axios from 'axios';

const fetchRooms = async (searchInput) => {
    try {
        let response;
        if (searchInput?.trim()) {
            response = await axios.get('http://localhost:8080/rooms', {
                params: {
                  searchInput: searchInput.toString()
                }
        });
        } else {
            response = await axios.get('http://localhost:8080/rooms');
        }

        console.log('Fetched rooms:', response.data);
        return response.data.rooms;
    } catch (error) {
        console.error('Error fetching rooms:', error);
        throw error;
    }
};
export default fetchRooms;