// GetUserByEmail.js
import React, { useState } from 'react';
import axios from 'axios';

const GetUserByEmail = ({ match }) => {
  const [user, setUser] = useState(null);
  const email = match.params.email;

  const fetchUserByEmail = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/users/email/${email}`);
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user by email:', error);
    }
  };

  React.useEffect(() => {
    fetchUserByEmail();
  }, [email]);

  return (
    <div>
      <h2>User Info by Email</h2>
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

export default GetUserByEmail;
