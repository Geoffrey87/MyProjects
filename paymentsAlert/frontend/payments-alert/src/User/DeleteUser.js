// DeleteUser.js
import React, { useState } from 'react';
import axios from 'axios';

const DeleteUser = ({ match }) => {
  const [message, setMessage] = useState('');
  const userId = match.params.id;

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/users/${userId}`);
      setMessage('User deleted successfully');
    } catch (error) {
      setMessage('Error deleting user');
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
