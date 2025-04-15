import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RegisterUser from './User/RegisterUser';
import GetUserById from './User/GetUserById';
import GetUserByEmail from './User/GetUserByEmail';
import UpdateUser from './User/UpdateUser';
import DeleteUser from './User/DeleteUser';
import LoginPage from './User/LoginPage';
import CalendarPage from './CalendarPage';



function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterUser />} />
        <Route path="/users/:id" element={<GetUserById />} />
        <Route path="/users/email/:email" element={<GetUserByEmail />} />
        <Route path="/update/:id" element={<UpdateUser />} />
        <Route path="/delete/:id" element={<DeleteUser />} />
        <Route path="/calendar" element={<CalendarPage />} />
      </Routes>
    </Router>
  );
}

export default App;


