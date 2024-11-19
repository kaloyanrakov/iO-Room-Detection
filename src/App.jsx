import { useState } from 'react'
import LogInPage from './LogInPage.jsx'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LogInPage />} />
      </Routes>
    </Router>
  )
}

export default App
