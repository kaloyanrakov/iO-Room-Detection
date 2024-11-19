import Layout from '../Layout';
import '../../assets/css/allRooms.css';

import logo from '../../assets/img/IO_Logo.png';

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
            <form onClick="clearfilters()">
                <button className="btn" type="submit">Clear Filters</button>
            </form>
        </div>
    );

    const mainContent = (
        <p>This is the content on the right</p>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent}/>
    );
}

export default AllRoomsPage;