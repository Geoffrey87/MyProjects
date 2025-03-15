import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function CreatePartyPage() {
  const [name, setName] = useState("");
  const [nrOfDeputies, setNrOfDeputies] = useState(1);
  const userId = sessionStorage.getItem("userId");
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    const newParty = { name: name, nrOfDeputies };

    axios
      .post(`${process.env.REACT_APP_API_URL}/api/parties`, newParty, {
        headers: {
          "X-User-Id": userId,
          "Content-Type": "application/json",
        },
      })
      .then(() => navigate("/simulator"))
      .catch((error) => console.error("Error creating party:", error));
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-200 flex justify-center items-center">
      <div className="max-w-xl bg-white shadow-xl rounded-xl p-8">
        <h2 className="text-4xl font-bold text-center text-blue-700 mb-6">Create New Party</h2>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-gray-700 font-semibold mb-2">Party Name:</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              className="w-full border border-gray-300 p-2 rounded"
            />
          </div>

          <div>
            <label className="block text-gray-700 font-semibold mb-2">Number of Deputies:</label>
            <input
              type="number"
              value={nrOfDeputies}
              onChange={(e) => setNrOfDeputies(e.target.value)}
              min="1"
              required
              className="w-full border border-gray-300 p-2 rounded"
            />
          </div>

          <button
            type="submit"
            className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
          >
            Create Party
          </button>

          <button
            type="button"
            onClick={() => navigate("/simulator")}
            className="ml-4 px-6 py-2 bg-gray-400 text-white rounded-lg hover:bg-gray-500 transition"
          >
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
}

export default CreatePartyPage;