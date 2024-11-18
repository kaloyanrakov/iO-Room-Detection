import {Route, Routes, BrowserRouter as Router} from "react-router-dom";
import AllRoomsPage from './components/pages/AllRoomsPage';


import './App.css'

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/rooms" element={<AllRoomsPage />} />
          </Routes>
      </Router>
  )
}

export default App
