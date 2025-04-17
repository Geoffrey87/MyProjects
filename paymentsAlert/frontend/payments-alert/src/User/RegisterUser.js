import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './RegisterUser.css';


const RegisterUser = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userInputDto = {
      username,
      password,
      email
    };

    try {
      const response = await axios.post('http://localhost:8080/users', userInputDto, {
        headers: {
          'Content-Type': 'application/json',
        }
      });

      setMessage('User registered successfully!');
      localStorage.setItem('username', username);
      setUsername('');
      setPassword('');
      setEmail('');


      setTimeout(() => {
        navigate('/');
      }, 1000);

    } catch (error) {
      let errorMessage = 'Error registering user. Please try again.';
      if (error.response?.data?.message) {
        errorMessage = error.response.data.message;
      }
      setMessage(errorMessage);
    }
  };

  return (
    <div className="register-container">
      <h2>Register User</h2>

      {message && (
        <p className={`message ${message.includes('success') ? 'success' : 'error'}`}>
          {message}
        </p>
      )}

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit">Register</button>
      </form>

      <p className="login-link">
        Already have an account? <a href="/login">Login here</a>
      </p>

      <p className="academic-note">
         This is an academic app — feel free to use a fictional email and any password.
      </p>
    </div>
  );
};

export default RegisterUser;