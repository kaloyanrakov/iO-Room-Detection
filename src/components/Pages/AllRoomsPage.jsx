import Layout from '../Layout';
import '../../assets/css/allRooms.css';
import fetchRooms from "../../api/fetchRooms.js";
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import searchIcon from '../../assets/img/search.png';
import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import EventApi from "../../api/EventApi.jsx";

function formatName(name) {
    return name.replace(name.substring(0, name.lastIndexOf("-") + 2), "");
}

function PeopleAmount({ label }) {
    return (
        <span className="people-amount">
            <img className="people_img" src={userIcon} alt="person icon" />
            <span>{label}</span>
        </span>
    );
}

function AllRoomsPage() {

    const navigate = useNavigate();
    const [rooms, setRooms] = useState([]);
    const [statusFilter, setStatusFilter] = useState("");
    const [floorFilter, setFloorFilter] = useState("");
    const [searchInput, setSearchInput] = useState();

    const onChangeSearchInput = e => {
        setSearchInput(e.target.value);
    }

    const handleSearch = e => {
        e.preventDefault();
        const getRooms = async () => {
            try {
                console.log('Fetching rooms...');
                const roomsData = await fetchRooms(searchInput);
                console.log('Fetched rooms data:', roomsData);

                const roomsWithEvents = await Promise.all(roomsData.map(async (room) => {
                    if (room.email === 'Testruimte1.eindhoven@iodigital.com') {
                        console.log(`Fetching events for room: ${room.email}`);
                        const events = await EventApi.getEventsByEmail(room.email);
                        console.log(`Fetched events for room ${room.email}:`, events);
                        return { ...room, meetings: events };
                    } else {
                        return { ...room, meetings: [] };
                    }
                }));
                console.log('Rooms with events:', roomsWithEvents);
                setRooms(roomsWithEvents);
            } catch (error) {
                console.error('Error fetching rooms:', error);
            }
        };
        getRooms();
    }

    useEffect(() => {
        if (!searchInput?.trim()) {
            const getRooms = async () => {
                try {
                    console.log('Fetching rooms...');
                    const roomsData = await fetchRooms();
                    console.log('Fetched rooms data:', roomsData);

                    const roomsWithEvents = await Promise.all(roomsData.map(async (room) => {
                        if (room.email === 'Testruimte1.eindhoven@iodigital.com') {
                            console.log(`Fetching events for room: ${room.email}`);
                            const events = await EventApi.getEventsByEmail(room.email);
                            console.log(`Fetched events for room ${room.email}:`, events);
                            return { ...room, meetings: events };
                        } else {
                            return { ...room, meetings: [] };
                        }
                    }));
                    console.log('Rooms with events:', roomsWithEvents);
                    setRooms(roomsWithEvents);
                } catch (error) {
                    console.error('Error fetching rooms:', error);
                }
            };
            getRooms();
            const interval = setInterval(() => {
                getRooms();
            }, 30000);

            return () => clearInterval(interval);
        }
    }, [searchInput]);

    useEffect(() => {
        console.log('Updated rooms:', rooms); // Log whenever rooms changes
        console.log('Rooms length:', rooms.length); // Log whenever rooms length changes
        console.log('Is rooms an array:', Array.isArray(rooms)); // Log whenever rooms is an array
    }, [rooms]);

    const getNextMeetingTime = (meetings, status) => {
        const now = new Date();
        const todayMeetings = meetings.filter(meeting => new Date(meeting.startTime).getDate() === now.getDate());

        if (todayMeetings.length === 0) {
            return "Until end of the day";
        }

        if (status === 'OCCUPIED_NOW') {
            const currentMeeting = todayMeetings.find(meeting => new Date(meeting.startTime) <= now && new Date(meeting.endTime) > now);
            if (currentMeeting) {
                return `Until ${new Date(currentMeeting.endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
            }
        }

        const nextMeeting = todayMeetings.find(meeting => new Date(meeting.startTime) > now);
        return nextMeeting ? `Until ${new Date(nextMeeting.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}` : "Until end of the day";
    };

    const filteredRooms = rooms.filter((room) => {
        const matchesStatus = !statusFilter || room.status === statusFilter.toUpperCase();
        const floorMatch = room.name.match(/ - (\d+)/);
        const roomFloor = floorMatch ? floorMatch[1] : null;
        const matchesFloor = !floorFilter || roomFloor === floorFilter;

        return matchesStatus && matchesFloor;
    });

    const handleStatusChange = (e) => setStatusFilter(e.target.value);
    const handleFloorChange = (e) => setFloorFilter(e.target.value);

    const sidebarContent = (
        <div className="sidebar">
            <div className="img-div">
                <a href="/rooms"><img src={logo} className="logo" alt="IO_Logo" /></a>
            </div>
            <h2>Filters</h2>
            <div className="filter">
                <label>Status:</label>
                <select name="status" id="status" value={statusFilter} onChange={handleStatusChange}>
                    <option value="">All</option>
                    <option value="AVAILABLE">Available</option>
                    <option value="OCCUPIED_SOON">Meeting Soon</option>
                    <option value="OCCUPIED_NOW">Occupied</option>
                </select>
            </div>

            <div className="filter">
                <label>Floor:</label>
                <select name="floor" id="floor" value={floorFilter} onChange={handleFloorChange}>
                    <option value="">All</option>
                    <option value="0">Floor 0</option>
                    <option value="1">Floor 1</option>
                    <option value="2">Floor 2</option>
                    <option value="3">Floor 3</option>
                    <option value="4">Floor 4</option>
                    <option value="5">Floor 5</option>
                </select>
            </div>
            <br />
            <form>
                <button className="btn" type="button" onClick={() => { setStatusFilter(""); setFloorFilter(""); }}>Clear Filters</button>
            </form>
            <a href="/login" className="btn login-btn custom-login-btn">Log In</a>
        </div>
    );

    const statusElem = (status) => {
        return status === 'OCCUPIED_NOW'
            ? <span className="text-occupied">Occupied</span>
            : (status === 'AVAILABLE'
                ? <span className="text-available">Available</span>
                : (status === 'OCCUPIED_SOON'
                    ? <span className="text-meeting_soon">Meeting Soon</span>
                    : <span>Unknown</span>));
    }

    const mainContent = (
        <div className="main-content">
            <form onSubmit={handleSearch}>
                <div className="search-bar">
                        <input type="text" placeholder="Search Rooms" required onChange={onChangeSearchInput}/>
                        <button className="btn" type="submit"><img src={searchIcon} alt="search icon"/></button>
                </div>
            </form>

            <div className="rooms-list">
                {filteredRooms.length > 0 ? (
                    filteredRooms.map(room => {
                        const [campus, floor, ...nameParts] = room.name.split(" - ");
                        const roomName = nameParts.join(" ");
                        const formattedRoomName = `${roomName} - Floor ${floor}`;

                        return (
                            <Link to={room.email.toString()} key={room.email}>
                                <div className={`indiv-room border-${room.status}`}>
                                    <div className="room-left">
                                        <h2>{formattedRoomName}</h2>
                                        <PeopleAmount label={`${room.currentCapacity}/10`} />
                                    </div>
                                    <div className="room-right">
                                        <p className={`room-status text-${room.status}`}>{statusElem(room.status)}</p>
                                        <p className="until">{getNextMeetingTime(room.meetings, room.status)}</p>
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
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent} />
    );
}

export default AllRoomsPage;
