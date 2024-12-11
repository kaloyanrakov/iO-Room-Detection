import axios from 'axios';

const fetchRooms = async (pageIndex, pageSize) => {
    try {
        const response = await axios.get('http://localhost:8080/rooms', {
            params: {
                pageIndex: pageIndex,
                pageSize: pageSize,
            }
        });
        console.log('Fetched rooms:', response.data);
        return {
            rooms: response.data.rooms,
            totalRooms: response.data.totalRooms, // Assuming the backend returns the total number of rooms
        };
    } catch (error) {
        console.error('Error fetching rooms:', error);
        throw error;
    }
};


const fetchAllRooms = async () => {
    try {
        const response = await axios.get('http://localhost:8080/rooms');  // No pagination params
        console.log('Fetched all rooms:', response.data);
        return {
            rooms: response.data.rooms,
            totalRooms: response.data.totalRooms,  // Assuming the backend returns the total number of rooms
        };
    } catch (error) {
        console.error('Error fetching all rooms:', error);
        throw error;
    }
};

export default fetchRooms;