import Layout from '../Layout';
import '../../assets/css/roominfo.css';
import logo from '../../assets/img/IO_Logo.png';
import userIcon from '../../assets/img/user.png';
import serachIcon from '../../assets/img/search.png';

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
const sidebarContent = (
    <div className="sidebar">
        <div className="img-div">
            <img src={logo} className="logo" alt="IO_Logo" />
        </div>
        <h2>Room Name</h2>
        <div className="filter">
            <h1 className="status">Status: <span className="text-occupied">Occupied</span></h1>
        </div>
        <br />
        <div className="room-right-image">
            <PeopleAmount label="8/10" />
        </div>
    </div>
);

function Appointment({ appointment, nthPerson }) {
    const { name, from, to } = appointment;
    return <div aria-label="Appointment" className='appointment-card'>
        <span aria-label="Time span">
            {formatDate(from)} - {formatDate(to)}
        </span>
        <div className="appointment-person">
            <span>{name}</span>
            <PeopleAmount label={String(nthPerson)} />
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
    const roomStatus = 'occupied';

    const statusElem = roomStatus == 'occupied'
        ? <span className="text-occupied">Occupied</span>
        : (roomStatus === 'available'
            ? <span className="text-available">Available</span>
            : (roomStatus === 'meeting_soon'
                ? <span className="text-meeting_soon">Meeting soon</span>
                : <span>Unknown</span>));

    const sidebarContent = (
        <div className="sidebar">
            <div className="img-div">
                <img src={logo} className="logo" alt="IO_Logo" />
            </div>
            <h2>Room Name</h2>
            <div className="filter">
                <h1 className="status">Status: &nbsp; {statusElem}</h1>
            </div>
            <br />
            <div className="room-right-image">
                <PeopleAmount label="8/10" />
            </div>
            <div className="sidebar-buttons">
                <button className="btn btn-delete" onClick={() => handleDeleteRoom()}>
                    Delete Room
                </button>
                <button className="btn btn-update" onClick={() => handleUpdateRoom()}>
                    Update Room
                </button>
            </div>
        </div >
    );


    const appointments = [
        {
            id: 1,
            name: 'Mark Fishbark',
            from: new Date('2024-11-12 09:00'), // 2024-11-12 09:00
            to: new Date('2024-11-12 10:30'), // 2024-11-12 10:30
        },
        {
            id: 2,

            name: 'Justin Case',
            from: new Date('2024-11-12 11:00'), // 2024-11-12 11:00
            to: new Date('2024-11-12 11:30'), // 2024-11-12 11:30
        },
        {
            id: 3,
            name: 'Dave Jobs',
            from: new Date('2024-11-12 13:00'), // 2024-11-12 13:00
            to: new Date('2024-11-12 14:00'), // 2024-11-12 14:00
        },
    ];

    const mainContent = (
        <div className="main-content">
            <div className="search-bar">
                <input type="text" placeholder="Search Rooms" />
                <button className="btn" type="submit"><img src={serachIcon} alt="search icon" /></button>
            </div>

            <div className="appointments-list">
                {appointments.map((appointment, idx) => (
                    <Appointment appointment={appointment} nthPerson={idx + 1} key={appointment.id} />
                ))}
            </div>
        </div>
    );
    return (
        <Layout sidebarContent={sidebarContent} rightSideContent={mainContent} />
    );
}

export default RoomInfoPage;