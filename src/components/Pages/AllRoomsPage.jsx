import Layout from '../Layout';
import '../../assets/css/allRooms.css';
import fetchRooms from "../../api/fetchRooms.js";

import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import searchIcon from '../../assets/img/search.png';
import {useEffect, useState} from "react";

function AllRoomsPage() {

    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        const getRooms = async () => {
            try {
                const roomsData = await fetchRooms();
                setRooms(roomsData);
                console.log('Fetched rooms data:', roomsData);
            } catch (error) {
                console.error('Error fetching rooms:', error);
            }
        };
        getRooms();
    }, []);

    useEffect(() => {
        console.log('Updated rooms:', rooms); // Log whenever rooms changes
        console.log('Rooms length:', rooms.length); // Log whenever rooms length changes
        console.log('Is rooms an array:', Array.isArray(rooms)); // Log whenever rooms is an array
    }, [rooms]);

    const sidebarContent = (
        <div className="sidebar">
            <div className="img-div">
                <img src={logo} alt="IO_Logo"/>
            </div>
            <h2>Filters</h2>
            <div className="filter">
                <label>Status: </label>
                <select name="status" id="status">
                    <option value="available">Available</option>
                    <option value="o-soon">OccupiedSoon</option>
                    <option value="o-now">Occupied Now</option>
                </select>
            </div>
            <br/>
            <form>
                <button className="btn" type="submit">Clear Filters</button>
            </form>
        </div>
    );

    const mainContent = (
        <div className="main-content">
            <div className="search-bar">
                <input type="text" placeholder="Search Rooms"/>
                <button className="btn" type="submit"><img src={searchIcon} alt="search icon"/></button>
            </div>

            <div className="rooms-list">
                {rooms.length > 0 ? (
                    rooms.map(room => (
                        <div key={room.email} className={`indiv-room border-${room.status}`}>
                            <div className="room-left">
                                <h2>{room.name}</h2>
                                <img src={userIcon} alt="person icon" />
                                <p className="people-amount">0/{room.maxCapacity}</p>
                            </div>
                            <div className="room-right">
                                <p className={`room-status text-${room.status}`}>{room.status}</p>
                                <p className="until">Until</p>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No rooms available</p>
                )}
            </div>
        </div>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent}/>
    );
}

export default AllRoomsPage;