import Layout from '../Layout';
import '../../assets/css/roominfo.css';
import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import serachIcon from '../../assets/img/search.png';
import MeetingRoomApi from "../../api/MeetingRoomApi";
import EventApi from "../../api/EventApi";
import { useParams } from 'react-router-dom';
import { Link } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

/**
 * 
 * @param {Date} date 
 * @returns string
 */
function formatDate(date) {
    return String(`${date.getHours().toString().padStart(2, "0")
        }:${date.getMinutes().toString().padStart(2, '0')
        }`);

}

function formatName(name) {
    return name.replace(name.substring(0, name.lastIndexOf("-")+2), "");
}

// const sidebarContent = (
//     <div className="sidebar">
//         <div className="img-div">
//             <img src={logo} className="logo" alt="IO_Logo" />
//         </div>
//         <h2>Room Name</h2>
//         <div className="filter">
//             <h1 className="status">Status: <span className="text-occupied">Occupied</span></h1>
//         </div>
//         <br />
//         <div className="room-right-image">
//             <PeopleAmount label="8/10" />
//         </div>
//     </div>
// );

function Appointment({ appointment, nthPerson }) {
    return <div aria-label="Appointment" className='appointment-card'>
        <span aria-label="Time span">
            {formatDate(new Date(appointment.startTime))} - {formatDate(new Date(appointment.endTime))}
        </span>
        <div className="appointment-person">
            <span>{appointment.organizerName}</span>
            <PeopleAmount label="-" />
        </div>
    </div>
}

function PeopleAmount({ label }) {
    return (
        <span className="people-amount">
            <img className="people_img" src={userIcon} alt="person icon" />
            <span>{label}</span>
        </span>
    );
}

function RoomInfoPage() {
    const initialRoomState = {
        id: 0,
        email: "",
        name: "",
        maxCapacity: 0,
        cameraConnection: null,
        currentCapacity: 0,
        status: "",
        roomEvent: null
    }
    const [room, setRoom] = useState(initialRoomState);
    const [events, setEvents] = useState([]);
    const params = useParams();

    const fetchRoom = () => {
        MeetingRoomApi.getMeetingRoomByEmail(params.email)
            .then(data => setRoom(data))
            .catch(error => console.log(error));
    }

    const fetchEvents = () => {
        EventApi.getEventsByEmail(params.email)
            .then(data => setEvents(data))
            .catch(error => console.log(error));
    }

    // useEffect(() => {
    //     fetchRoom();
    //     fetchEvents();
    // }, [room.currentCapacity])

    useEffect(() => {
        const interval = setInterval(() => {
            fetchRoom();
            fetchEvents();
        }, 5000);
      
        return () => clearInterval(interval);
      }, []);

    const statusElem = room.status === 'OCCUPIED_NOW'
        ? <span className="text-occupied">Occupied</span>
        : (room.status === 'AVAILABLE'
            ? <span className="text-available">Available</span>
            : (room.status === 'OCCUPIED_SOON'
                ? <span className="text-meeting_soon">Meeting soon</span>
                : <span>Unknown</span>));

    const sidebarContent = (
        <div className="sidebar">
            <div className="img-div">
                <img src={logo} className="logo" alt="IO_Logo" />
            </div>
            <h2>{formatName(room.name)}</h2>
            <div className="filter">
                <h1 className="status">Status: &nbsp; {statusElem}</h1>
            </div>
            <br />
            <div className="room-right-image">
                <PeopleAmount label={`${room.currentCapacity}/10`} />
            </div>
            <div className="sidebar-buttons">
                <button className="btn btn-delete" onClick={() => handleDeleteRoom()}>
                    Delete Room
                </button>
                <button className="btn btn-update">
                    <Link to="update">
                        Update Room
                    </Link>
                </button>
            </div>
        </div >
    );


    // const appointments = [
    //     {
    //         id: 1,
    //         name: 'Mark Fishbark',
    //         from: new Date('2024-11-12 09:00'), // 2024-11-12 09:00
    //         to: new Date('2024-11-12 10:30'), // 2024-11-12 10:30
    //     },
    //     {
    //         id: 2,

    //         name: 'Justin Case',
    //         from: new Date('2024-11-12 11:00'), // 2024-11-12 11:00
    //         to: new Date('2024-11-12 11:30'), // 2024-11-12 11:30
    //     },
    //     {
    //         id: 3,
    //         name: 'Dave Jobs',
    //         from: new Date('2024-11-12 13:00'), // 2024-11-12 13:00
    //         to: new Date('2024-11-12 14:00'), // 2024-11-12 14:00
    //     },
    // ];

    const mainContent = (
        <div className="main-content">
            <div className="search-bar">
                <input type="text" placeholder="Search Rooms" />
                <button className="btn" type="submit"><img src={serachIcon} alt="search icon" /></button>
            </div>

            <div className="appointments-list">
                {events.map((event, idx) => (
                    <Appointment appointment={event} nthPerson={idx + 1} key={idx + 1} />
                ))}
            </div>
        </div>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent} />
    );
}

export default RoomInfoPage;