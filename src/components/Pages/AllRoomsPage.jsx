import Layout from '../Layout';
import '../../assets/css/allRooms.css';
import fetchRooms from "../../api/fetchRooms.js";

import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import searchIcon from '../../assets/img/search.png';
import {useEffect, useState} from "react";
import { Link } from 'react-router-dom';

function formatName(name) {
    return name.replace(name.substring(0, name.lastIndexOf("-")+2), "");
}

function AllRoomsPage() {

    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        const getRooms = async () => {
            try {
                const roomsData = await fetchRooms();
                setRooms(roomsData.reverse());
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
                <a href="/rooms"><img src={logo} className="logo" alt="IO_Logo"/></a>
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
                    // Sort rooms by floor before mapping
                    [...rooms]
                        .sort((a, b) => {
                            const floorA = parseInt(a.name.split(" - ")[1]); // Extract floor number
                            const floorB = parseInt(b.name.split(" - ")[1]);
                            return floorB - floorA; // Sort ascending
                        })
                        .map(room => {
                            // Extract parts of the name and reformat
                            const [campus, floor, ...nameParts] = room.name.split(" - ");
                            const roomName = nameParts.join(" ");
                            const formattedRoomName = `${roomName} - Floor ${floor}`;

                            return (
                                <Link to={room.email.toString()} key={room.email}>
                                    <div className={`indiv-room border-${room.status}`}>
                                        <div className="room-left">
                                            <h2>{formattedRoomName}</h2>
                                            <img src={userIcon} alt="person icon"/>
                                            <p className="people-amount">0/{room.maxCapacity}</p>
                                        </div>
                                        <div className="room-right">
                                            <p className={`room-status text-${room.status}`}>{room.status}</p>
                                            <p className="until">Until</p>
                                        </div>
                                    </div>
                                </Link>
                            );
                        })
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