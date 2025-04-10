// GetUserById.js
import React, { useState } from 'react';
import axios from 'axios';

const GetUserById = ({ match }) => {
  const [user, setUser] = useState(null);
  const userId = match.params.id;

  const fetchUser = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/users/${userId}`);
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  React.useEffect(() => {
    fetchUser();
  }, [userId]);

  return (
    <div>
      <h2>User Info</h2>
      {user ? (
        <div>
          <p>ID: {user.id}</p>
          <p>Username: {user.username}</p>
          <p>Email: {user.email}</p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default GetUserById;
