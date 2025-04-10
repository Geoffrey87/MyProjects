// UpdateUser.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UpdateUser = ({ match }) => {
  const [user, setUser] = useState({ username: '', password: '', email: '' });
  const userId = match.params.id;

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/${userId}`);
        setUser(response.data);
      } catch (error) {
        console.error('Error fetching user:', error);
      }
    };
    fetchUser();
  }, [userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(`http://localhost:8080/users/${userId}`, user);
      console.log('User updated:', response.data);
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  return (
    <div>
      <h2>Update User</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={user.username}
          onChange={(e) => setUser({ ...user, username: e.target.value })}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={user.password}
          onChange={(e) => setUser({ ...user, password: e.target.value })}
        />
        <input
          type="email"
          placeholder="Email"
          value={user.email}
          onChange={(e) => setUser({ ...user, email: e.target.value })}
          required
        />
        <button type="submit">Update</button>
      </form>
    </div>
  );
};

export default UpdateUser;
