import React from 'react';
import { useNavigate } from 'react-router-dom';

function generateRandomId() {
  return Math.random().toString(36).substring(2, 10);
}

function HomePage() {
  const navigate = useNavigate();

  const handleStart = () => {
    let userId = sessionStorage.getItem("userId");
    if (!userId) {
      userId = generateRandomId();
      sessionStorage.setItem("userId", userId);
    }

    navigate("/simulator");
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Welcome to Government Simulator</h1>
      <button onClick={handleStart}>Let's get started</button>
    </div>
  );
}

export default HomePage;
