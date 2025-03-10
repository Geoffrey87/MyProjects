import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function UpdatePartyPage() {
  const { id } = useParams(); // Get party ID from URL
  const navigate = useNavigate();
  const [party, setParty] = useState({ name: '', nrOfDeputies: 0 });

  useEffect(() => {
    axios.get(`http://localhost:8080/api/parties/${id}`, {
      headers: { "X-User-Id": sessionStorage.getItem("userId") }
    })
    .then(response => {
      setParty(response.data);
    })
    .catch(error => {
      console.error("Error fetching party details:", error);
    });
  }, [id]);

  const handleChange = (e) => {
    setParty({ ...party, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.put(`http://localhost:8080/api/parties/${id}`, party, {
      headers: { "X-User-Id": sessionStorage.getItem("userId") }
    })
    .then(() => {
      alert('Party updated successfully!');
      navigate('/simulator'); // Go back to the list after update
    })
    .catch(error => {
      console.error("Error updating party:", error);
    });
  };

  return (
    <div>
      <h2>Update Party</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input type="text" name="name" value={party.name} onChange={handleChange} required />
        </label>
        <br />
        <label>
          Nº of Deputies:
          <input type="number" name="nrOfDeputies" value={party.nrOfDeputies} onChange={handleChange} required />
        </label>
        <br />
        <button type="submit">Update</button>
      </form>
    </div>
  );
}

export default UpdatePartyPage;
