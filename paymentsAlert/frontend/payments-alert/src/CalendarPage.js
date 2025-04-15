import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import axios from 'axios';
import './CalendarPage.css';

function CalendarPage() {
  const [date, setDate] = useState(new Date());
  const [showModal, setShowModal] = useState(false);
  const [selectedDate, setSelectedDate] = useState(null);
  const [formData, setFormData] = useState({
    description: '',
    amount: '',
    dueDate: '',
    recurrencePeriod: '',
  });
  const [errors, setErrors] = useState({});

  const handleDateClick = (date) => {
    setSelectedDate(date);
    setShowModal(true);
    setFormData(prev => ({
      ...prev,
      dueDate: date.toISOString().slice(0, 16), // Format to YYYY-MM-DDTHH:MM
    }));
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.description) newErrors.description = 'Description is required';
    if (formData.amount < 1) newErrors.amount = 'Amount must be ≥ 1';
    if (!formData.dueDate) newErrors.dueDate = 'Date is required';
    if (!['MONTHLY', 'QUARTERLY', 'SEMI_ANNUALLY', 'YEARLY'].includes(formData.recurrencePeriod)) {
      newErrors.recurrencePeriod = 'Invalid frequency';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = localStorage.getItem('userId');
    const authToken = localStorage.getItem('authToken');

      if (!userId || !authToken) {
          setErrors({ submit: 'User not authenticated' });
          return;
        }

    try {
    console.log("userId:", userId);
    console.log("authToken:", authToken);
    console.log("formData:", formData);

      const response = await axios.post('http://localhost:8080/payments', {
        ...formData,
        userId: parseInt(userId),
        amount: parseFloat(formData.amount),
      },
      {
        headers: {
          'Authorization': `Bearer ${authToken}`,
          'Content-Type': 'application/json'
        }
      });

      if (response.status === 201) {
        setShowModal(false);
        setFormData({
                  description: '',
                  amount: '',
                  dueDate: '',
                  recurrencePeriod: ''
                });
      }
    } catch (error) {
      console.error('Error creating payment:', error);
      setErrors({ submit: error.response?.data?.message || 'Error creating payment' });
    }
  };

  return (
    <div className="calendar-container">
      <h1>Payment Calendar</h1>
      <div className="calendar-wrapper">
        <Calendar
          onChange={setDate}
          onClickDay={handleDateClick}
          value={date}
          className="react-calendar"
        />
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="payment-modal">
            <h2>New Payment</h2>
            <button
              className="close-btn"
              onClick={() => {
                setShowModal(false);
                setFormData({
                  description: '',
                  amount: '',
                  dueDate: '',
                  recurrencePeriod: ''
                });
              }}
            >×</button>

               <form onSubmit={handleSubmit}>

              <div className="form-group">
                  <label>Description:</label>
                  <input
                      type="text"
                      value={formData.description}
                      onChange={(e) => setFormData({...formData, description: e.target.value})}
                      required
                  />
                  {errors.description && <span className="error">{errors.description}</span>}
              </div>

              <div className="form-group">
                <label>Amount:</label>
                <input
                  type="number"
                  step="0.01"
                  value={formData.amount}
                  onChange={(e) => setFormData({...formData, amount: e.target.value})}
                  required
                />
                {errors.amount && <span className="error">{errors.amount}</span>}
              </div>

              <div className="form-group">
                <label>Due Date:</label>
                <input
                  type="datetime-local"
                  value={formData.dueDate}
                  onChange={(e) => setFormData({...formData, dueDate: e.target.value})}
                  required
                />
                {errors.dueDate && <span className="error">{errors.dueDate}</span>}
              </div>

              <div className="form-group">
                <label>Recurrence:</label>
                <select
                  value={formData.recurrencePeriod}
                  onChange={(e) => setFormData({...formData, recurrencePeriod: e.target.value})}
                  required
                >
                  <option value="">Select Frequency</option>
                  <option value="MONTHLY">Monthly</option>
                  <option value="QUARTERLY">Quarterly</option>
                  <option value="SEMI_ANNUALLY">Semi-Annually</option>
                  <option value="YEARLY">Yearly</option>
                </select>
                {errors.recurrencePeriod && <span className="error">{errors.recurrencePeriod}</span>}
              </div>

              {errors.submit && <div className="error-message">{errors.submit}</div>}

              <button type="submit" className="submit-btn">Create Payment</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default CalendarPage;