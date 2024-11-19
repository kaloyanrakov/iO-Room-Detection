import Layout from '../Layout';
import '../../assets/css/allRooms.css';

import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import serachIcon from '../../assets/img/search.png';

function AllRoomsPage() {
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
                <button className="btn" type="submit"><img src={serachIcon} alt="search icon"/></button>
            </div>

            <div className="rooms-list">
                <div className="indiv-room available">
                    <div className="room-left">
                        <h2>Room Name</h2>
                        <img src={userIcon} alt="person icon"/>
                        <p className="people-amount">8/10</p>
                    </div>
                    <div className="room-right">
                        <p className="room-status available">Available</p>
                        <p className="until">Until 10:30</p>
                    </div>
                </div>

                <div className="indiv-room occupied">
                    <div className="room-left">
                        <h2>Room Name</h2>
                        <img src={userIcon} alt="person icon"/>
                        <p className="people-amount">8/10</p>
                    </div>
                    <div className="room-right">
                        <p className="room-status occupied">Occupied</p>
                        <p className="until">Until 10:30</p>
                    </div>
                </div>
            </div>
        </div>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent}/>
    );
}

export default AllRoomsPage;