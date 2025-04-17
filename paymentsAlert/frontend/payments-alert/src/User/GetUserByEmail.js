import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import API from './api';

const GetUserByEmail = () => {
  const [user, setUser] = useState(null);
  const { email } = useParams();

  const fetchUserByEmail = async () => {
    try {
      const response = await API.get(`/users/email/${email}`);
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user by email:', error);
    }
  };

  useEffect(() => {
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
