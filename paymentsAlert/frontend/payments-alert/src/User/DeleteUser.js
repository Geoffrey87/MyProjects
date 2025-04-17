import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import API from './api';

const DeleteUser = () => {
  const [message, setMessage] = useState('');
  const { id: userId } = useParams();

  const handleDelete = async () => {
    try {
      await API.delete(`/users/${userId}`);
      setMessage('User deleted successfully');
    } catch (error) {
      setMessage('Error deleting user');
      console.error('Delete error:', error);
    }
  };

  return (
    <div>
      <h2>Delete User</h2>
      <button onClick={handleDelete}>Delete User</button>
      {message && <p>{message}</p>}
    </div>
  );
};

export default DeleteUser;
