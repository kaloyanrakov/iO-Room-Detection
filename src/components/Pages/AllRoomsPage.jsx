import Layout from '../Layout';
import '../../assets/css/allRooms.css';
import fetchRooms from "../../api/fetchRooms.js";
import { useNavigate } from 'react-router-dom'; 
import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import searchIcon from '../../assets/img/search.png';
import {useEffect, useState} from "react";
import { Link } from 'react-router-dom';
import fetchAllRooms from "../../api/fetchRooms.js";

function formatName(name) {
    return name.replace(name.substring(0, name.lastIndexOf("-")+2), "");
}

function AllRoomsPage() {
    const navigate = useNavigate(); 
    const [pageIndex, setPageIndex] = useState(0);
    const [pageSize] = useState(10);
    const [rooms, setRooms] = useState([]);
    const [allRooms, setAllRooms] = useState([]); 
    

    useEffect(() => {
        const loadRooms = async () => {
            try {
                const roomsData = await fetchRooms(pageIndex, pageSize);  // Fetch rooms based on pageIndex and pageSize
                setRooms(roomsData.rooms);  // Store paginated rooms
            } catch (error) {
                console.error("Error loading rooms:", error);
            }
        };
        loadRooms();
    }, [pageIndex, pageSize]);

    useEffect(() => {
        const loadAllRooms = async () => {
            try {
                const allRoomsData = await fetchAllRooms();  // Fetch all rooms
                setAllRooms(allRoomsData.rooms);  // Store all rooms in state
            } catch (error) {
                console.error("Error loading all rooms:", error);
            }
        };
        loadAllRooms();
    }, []); 

    const handleNextPage = () => {

        console.log('Current page index:', pageIndex);
        console.log('Total rooms to show for current page:', (pageIndex + 1) * pageSize);
        console.log('Total Rooms:', allRooms);
        console.log('Rooms:', rooms);

        if ((pageIndex + 1) * pageSize < allRooms.length) {
            setPageIndex(pageIndex + 1);
        }
    };

    const handlePreviousPage = () => {
        if (pageIndex > 0) {
            setPageIndex(pageIndex - 1);
        }
    };

    const sidebarContent = (
        <div className="sidebar">
            <div className="img-div">
                <a href="/rooms"><img src={logo} className="logo" alt="IO_Logo"/></a>
            </div>
            <h2>Filters</h2>
            <div className="filter">
                <label>Status:</label>
                <select name="status" id="status">
                    <option value="available">Available</option>
                    <option value="o-soon">Occupied Soon</option>
                    <option value="o-now">Occupied Now</option>
                </select>
            </div>

            <div className="filter">
                <label> Floor:</label>
                <select name="floor" id="floor">
                    <option value="0">Floor 0</option>
                    <option value="1">Floor 1</option>
                    <option value="2">Floor 2</option>
                    <option value="3">Floor 3</option>
                    <option value="4">Floor 4</option>
                    <option value="5">Floor 5</option>
                </select>
            </div>
            <br/>
            <form>
                <button className="btn" type="submit">Clear Filters</button>
            </form>
            <a href="/login" className="btn login-btn custom-login-btn">Log In</a>
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
            <div className="pagination">
                <button onClick={handlePreviousPage} disabled={pageIndex === 0} className="btn custom-pagin-btn">Previous</button>
                <button onClick={handleNextPage} disabled={(pageIndex + 1) * pageSize >= rooms} className="btn custom-pagin-btn">Next</button>
            </div>
        </div>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent}/>
    );
}

export default AllRoomsPage;