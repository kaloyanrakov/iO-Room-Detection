import Layout from "../Layout";
import logo from '../../assets/img/IO_Logo.png';
import '../../assets/css/updateRoom.css'
import { useState } from "react";


function AddRoomPage() {
    const [name, setName] = useState("");
    const [camera, setCamera] = useState("");

    function handleSubmit(event) {
        event.preventDefault();
        console.log("Room Name:", name);
        console.log("Camera:", camera);
    }


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
                        <label htmlFor="roomName">
                            Room Name:
                        </label>
                        <input
                            type="text"
                            name="roomName"
                            id="roomName"
                            required
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />

                        <label htmlFor="roomCamera">
                            Camera:
                        </label>
                        <select
                            name="roomCamera"
                            id="roomCamera"
                            value={camera}
                            onChange={(e) => setCamera(e.target.value)}
                        >
                            {/* Add empty first option */}
                            <option value="" disabled>
                                Select a camera
                            </option>
                        </select>
                    </div>
                    <button className="btn" type="submit">
                        Add room
                    </button>
                </div>
            </form>
        </div>
    );
    return <Layout sidebarContent={sidebar} rightSideContent={mainContent} />;
}

export default AddRoomPage;