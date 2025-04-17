import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import API from './api';

const GetUserById = () => {
  const [user, setUser] = useState(null);
  const { id } = useParams();

  const fetchUser = async () => {
    try {
      const response = await API.get(`/users/${id}`);
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user:', error);
    }
  };

  useEffect(() => {
    fetchUser();
  }, [id]);

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
