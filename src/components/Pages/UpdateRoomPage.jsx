import Layout from "../Layout";
import logo from '../../assets/img/IO_Logo.png';
import '../../assets/css/updateRoom.css'
import React, { useState, useEffect } from 'react';
import MeetingRoomApi from "../../api/MeetingRoomApi";
import CameraApi from "../../api/CameraApi";
import { useNavigate, useParams } from 'react-router-dom';


function UpdateRoomPage() {
    const [room, setRoom] = useState(null);
    const [camera, setCamera] = useState(0);
    const [cameras, setCameras] = useState([]);
    const navigate = useNavigate();
    const params = useParams();

    const fetchRoom = () => {
        MeetingRoomApi.getMeetingRoomByEmail(params.email)
            .then(data => setRoom(data))
            .catch(error => console.log(error));
    }

    const fetchCameras = () => {
        CameraApi.getCameras()
            .then(data => setCameras(data))
            .catch(error => console.log(error));
    }

    const handleSubmit = e =>  {
        e.preventDefault();
        const newMeetingRoom = {
            email: room.email,
            cameraConnectionId: camera
        }
        MeetingRoomApi.createMeetingRoom(newMeetingRoom)
            .then(() => navigate(`/rooms/${params.id}`))
            .catch(error => console.log(error));
    }

    const cameraChanged = e => {
        setCamera(e.target.value);
    }

    useEffect(() => {
        fetchCameras();
        fetchRoom();
    }, [])

    const sidebar = (
        <div className="sidebar">
            <div className="img-div">
                <a href="/rooms"><img src={logo} className="logo" alt="IO_Logo"/></a>
            </div>
        </div>
    );

    const mainContent = (
        <div className="main-content">
            <form className="add-room-form" onSubmit={handleSubmit}>
                <div className="add-room-form__wrapper">
                    <div className="add-room-form__grid">
                        <label htmlFor="roomCamera">
                            Camera:
                        </label>
                        <select
                            name="roomCamera"
                            id="roomCamera"
                            value={camera}
                            onChange={cameraChanged}
                        >  
                        {cameras.map(camera => {
                            return (
                                <option value={camera.id}>{camera.macAddress}</option>
                            )
                        })}
                        </select>
                    </div>
                    <button className="btn" type="submit">
                        Update room
                    </button>
                </div>
            </form>
        </div>
    );
    return <Layout sidebarContent={sidebar} rightSideContent={mainContent} />;
}

export default UpdateRoomPage;