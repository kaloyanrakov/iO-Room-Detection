import Layout from "../Layout";
import logo from '../../assets/img/IO_Logo.png';
import '../../assets/css/addRoom.css'
import { useState } from "react";


function AddRoomPage() {
    const [camera, setCamera] = useState("");

    function handleSubmit(event) {
        event.preventDefault();
    
        console.log("Camera:", camera);
    }


    const sidebar = (
        <div className="sidebar">
            <div className="img-div">
                <img src={logo} className="logo" alt="IO_Logo" />
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
                            onChange={(e) => setCamera(e.target.value)}
                        >
                            
                            <option value="" disabled>
                                Select a camera
                            </option>
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

export default AddRoomPage;