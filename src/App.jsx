import {Route, Routes, BrowserRouter as Router} from "react-router-dom";
import AllRoomsPage from './components/Pages/AllRoomsPage';
import RoomInfoPage from './components/Pages/RoomInfoPage';
import AddRoomPage from './components/Pages/AddRoomPage';
import UpdateRoomPage from "./components/Pages/UpdateRoomPage"
import { useState } from 'react'
import LogInPage from './LogInPage.jsx'

import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
      <Router>
          <Routes>
              <Route path="/rooms" element={<AllRoomsPage />} />
              <Route path="/roominfo" element={<RoomInfoPage />} />
              <Route path="/add-room" element={<AddRoomPage />} />
              <Route path="/update-room" element={<UpdateRoomPage />} />
              <Route path="/login" element={<LogInPage />} />
          </Routes>
      </Router>
  )
}

export default App
