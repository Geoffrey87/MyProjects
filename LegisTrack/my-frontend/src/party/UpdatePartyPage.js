import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";

function UpdatePartyPage() {
  const { id } = useParams();
  const [party, setParty] = useState({ name: "", nrOfDeputies: 1 });
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_URL}/api/parties/${id}`, {
        headers: { "X-User-Id": userId },
      })
      .then((response) => setParty(response.data))
      .catch((error) => console.error("Error fetching party:", error));
  }, [id, userId]);

  const handleSubmit = (event) => {
    event.preventDefault();

    axios
      .put(`${process.env.REACT_APP_API_URL}/api/parties/${id}`, party, {
        headers: { "X-User-Id": userId },
      })
      .then(() => {
        alert("Party updated successfully!");
        navigate("/simulator");
      })
      .catch((error) => console.error("Error updating party:", error));
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 py-10">
      <div className="max-w-4xl mx-auto bg-white shadow-xl rounded-xl p-8">
        <h2 className="text-4xl font-bold text-center text-blue-700 mb-6">Update Party</h2>
        <h3 className="text-2xl font-bold text-center text-gray-600 mb-4">Modify party details</h3>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-lg font-semibold text-gray-700">Party Name:</label>
            <input
              type="text"
              value={party.name}
              onChange={(e) => setParty({ ...party, name: e.target.value })}
              required
              className="w-full p-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-lg font-semibold text-gray-700">Number of Deputies:</label>
            <input
              type="number"
              value={party.nrOfDeputies}
              onChange={(e) => setParty({ ...party, nrOfDeputies: e.target.value })}
              min="1"
              required
              className="w-full p-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="flex justify-center gap-4 mt-6">
            <button
              type="submit"
              className="px-4 py-2 bg-blue-500 text-white rounded-lg shadow hover:bg-blue-600 transition"
            >
              ✅ Update Party
            </button>
            <button
              type="button"
              onClick={() => navigate("/simulator")}
              className="px-4 py-2 bg-gray-400 text-white rounded-lg shadow hover:bg-gray-500 transition"
            >
              ❌ Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default UpdatePartyPage;
