import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function generateRandomId() {
  return Math.random().toString(36).substring(2, 10);
}

function HomePage() {
  const navigate = useNavigate();

  useEffect(() => {
    // Check if a user ID exists in localStorage
    let userId = localStorage.getItem("userId");
    if (!userId) {
      userId = generateRandomId();
      localStorage.setItem("userId", userId);
    }

    // Ensure sessionStorage also has the userId
    sessionStorage.setItem("userId", userId);
  }, []);

  const handleStart = () => {
    navigate("/simulator");
  };

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>Welcome to Government Simulator</h1>
      <button onClick={handleStart}>Let's get started</button>
    </div>
  );
}

export default HomePage;
