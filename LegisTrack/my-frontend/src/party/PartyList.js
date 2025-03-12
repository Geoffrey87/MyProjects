import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

function PartyList() {
  const [parties, setParties] = useState([]);
  const [message, setMessage] = useState("");
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/parties", {
        headers: { "X-User-Id": userId },
        withCredentials: true,
      })
      .then((response) => setParties(response.data))
      .catch((error) => console.error("Error fetching parties:", error));

    if (location.state?.lawCreated) {
      setMessage("Law successfully created!");
      setTimeout(() => setMessage(""), 3000);
    }
  }, [userId, location.state]);

  const sortAscending = () => setParties([...parties].sort((a, b) => a.nrOfDeputies - b.nrOfDeputies));
  const sortDescending = () => setParties([...parties].sort((a, b) => b.nrOfDeputies - a.nrOfDeputies));

  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8080/api/parties/${id}`, { headers: { "X-User-Id": userId } })
      .then(() => setParties(parties.filter((party) => party.id !== id)))
      .catch((error) => console.error("Error deleting party:", error));
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 py-10">
      <div className="max-w-4xl mx-auto bg-white shadow-xl rounded-xl p-8">
        <h2 className="text-4xl font-bold text-center text-blue-700 mb-6">Parties List</h2>

        {message && (
          <div className="bg-green-200 text-green-800 text-center p-3 rounded mb-4">
            {message}
          </div>
        )}

        <div className="flex justify-center gap-4 mb-6">
          <button
            onClick={sortAscending}
            className="px-4 py-2 bg-blue-500 text-white rounded-lg shadow hover:bg-blue-600 transition"
          >
            🔼 Ascending
          </button>
          <button
            onClick={sortDescending}
            className="px-4 py-2 bg-blue-500 text-white rounded-lg shadow hover:bg-blue-600 transition"
          >
            🔽 Descending
          </button>
        </div>

        <ul className="space-y-4">
          {parties.map((party) => (
            <li key={party.id} className="flex justify-between items-center p-4 bg-gray-50 shadow-sm rounded-lg">
              <span className="font-semibold text-gray-800">
                {party.name} - <span className="text-gray-600">Deputies: {party.nrOfDeputies}</span>
              </span>
              <div className="flex gap-2">
                <button
                  onClick={() => navigate(`/update-party/${party.id}`)}
                  className="px-3 py-1 bg-yellow-400 text-white rounded-md hover:bg-yellow-500 transition"
                >
                  ✏️ Update
                </button>
                <button
                  onClick={() => handleDelete(party.id)}
                  className="px-3 py-1 bg-red-400 text-white rounded-md hover:bg-red-500 transition"
                >
                  🗑 Delete
                </button>
              </div>
            </li>
          ))}
        </ul>

        <div className="flex justify-center gap-4 mt-8">
          <button
            onClick={() => navigate("/create-party")}
            className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition"
          >
            ➕ Create Party
          </button>
          <button
            onClick={() => navigate("/create-law")}
            className="px-4 py-2 bg-indigo-500 text-white rounded-md hover:bg-indigo-600 transition"
          >
            📑 Create Law
          </button>
          <button
            onClick={() => navigate("/view-laws")}
            className="px-4 py-2 bg-purple-500 text-white rounded-md hover:bg-purple-600 transition"
          >
            📖 View Laws
          </button>
        </div>
      </div>
    </div>
  );
}

export default PartyList;