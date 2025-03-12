import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function generateRandomId() {
  return Math.random().toString(36).substring(2, 10);
}

function HomePage() {
  const navigate = useNavigate();

  useEffect(() => {
    let userId = localStorage.getItem("userId");
    if (!userId) {
      userId = generateRandomId();
      localStorage.setItem("userId", userId);
    }
    sessionStorage.setItem("userId", userId);
  }, []);

  const handleStart = () => {
    navigate("/simulator");
  };

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gradient-to-br from-blue-100 to-blue-200">
      <div className="bg-white shadow-xl rounded-xl px-8 py-10 text-center">
        <h1 className="text-5xl font-bold text-blue-700 mb-6">
          Welcome to Government Simulator
        </h1>
        <button
          onClick={handleStart}
          className="mt-4 px-6 py-3 bg-blue-600 text-white text-lg rounded-lg shadow hover:bg-blue-700 transition"
        >
          Let's get started
        </button>
      </div>
    </div>
  );
}

export default HomePage;
