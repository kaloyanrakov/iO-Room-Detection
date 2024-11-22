import {Route, Routes, BrowserRouter as Router} from "react-router-dom";
import AllRoomsPage from './components/Pages/AllRoomsPage';
import RoomInfoPage from './components/Pages/RoomInfoPage';
import AddRoomPage from './components/Pages/AddRoomPage';
import UpdateRoomPage from "./components/Pages/UpdateRoomPage"

import './App.css'

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/rooms" element={<AllRoomsPage />} />
              <Route path="/roominfo" element={<RoomInfoPage />} />
              <Route path="/add-room" element={<AddRoomPage />} />
              <Route path="/update-room" element={<UpdateRoomPage />} />
          </Routes>
      </Router>
  )
}

export default App
