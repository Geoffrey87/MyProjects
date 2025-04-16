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
  const [dailyPayments, setDailyPayments] = useState([]);

  const handleDateClick = async (date) => {
    setSelectedDate(date);
    setShowModal(true);
    setFormData(prev => ({
      ...prev,
      dueDate: date.toISOString().slice(0, 16),
    }));
    await fetchPaymentsForDate(date);
  };

  const fetchPaymentsForDate = async (date) => {
    const userId = localStorage.getItem('userId');
    const authToken = localStorage.getItem('authToken');
    if (!userId || !authToken) return;

    const pad = (n) => String(n).padStart(2, '0');
    const dateString = `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;

    try {
      const res = await axios.get(`http://localhost:8080/payments/by-date`, {
        params: { userId, date: dateString },
        headers: { Authorization: `Bearer ${authToken}` },
      });
      setDailyPayments(res.data);
    } catch (error) {
      console.error('Error getting payments:', error);
      setDailyPayments([]);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.description) newErrors.description = 'Description is required';
    if (formData.amount < 1) newErrors.amount = 'Amount must be ≥ 1';
    if (!formData.dueDate) newErrors.dueDate = 'Date is required';
    if (
      formData.recurrencePeriod &&
      !['NONE','MONTHLY', 'QUARTERLY', 'SEMI_ANNUALLY', 'ANNUALLY'].includes(formData.recurrencePeriod)
    ) {
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

    if (!validateForm()) return;

    const recurrence =
        !formData.recurrencePeriod || formData.recurrencePeriod === 'None'
          ? 'NONE'
          : formData.recurrencePeriod;

      const data = {
        ...formData,
        userId: parseInt(userId),
        amount: parseFloat(formData.amount),
        recurrencePeriod: recurrence,
      };

      try {
        const response = await axios.post('http://localhost:8080/payments', data, {
          headers: {
            'Authorization': `Bearer ${authToken}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.status === 201) {
          setShowModal(false);
          setFormData({
            description: '',
            amount: '',
            dueDate: '',
            recurrencePeriod: '',
          });
          fetchPaymentsForDate(selectedDate);
        }
      } catch (error) {
        console.error('Error creating payment:', error);
        setErrors({ submit: error.response?.data?.message || 'Error creating payment' });
      }
    };

  const handleTogglePaid = async (paymentId) => {
    const authToken = localStorage.getItem('authToken');
    const payment = dailyPayments.find(p => p.id === paymentId);
    const newPaidStatus = !payment.paid;

    try {
      await axios.patch(`http://localhost:8080/payments/${paymentId}/paid`, {
        paid: newPaidStatus
      }, {
        headers: {
          Authorization: `Bearer ${authToken}`
        }
      });
      fetchPaymentsForDate(selectedDate); // Refresh list
    } catch (error) {
      console.error("Error updating paid status:", error);
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
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
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
                  onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                  required
                />
                {errors.amount && <span className="error">{errors.amount}</span>}
              </div>

              <div className="form-group">
                <label>Due Date:</label>
                <input
                  type="datetime-local"
                  value={formData.dueDate}
                  onChange={(e) => setFormData({ ...formData, dueDate: e.target.value })}
                  required
                />
                {errors.dueDate && <span className="error">{errors.dueDate}</span>}
              </div>

              <div className="form-group">
                <label>Recurrence:</label>
                <select
                  value={formData.recurrencePeriod}
                  onChange={(e) => setFormData({ ...formData, recurrencePeriod: e.target.value })}
                >
                  <option value="NONE">None</option>
                  <option value="MONTHLY">Monthly</option>
                  <option value="QUARTERLY">Quarterly</option>
                  <option value="SEMI_ANNUALLY">Semi-Annually</option>
                  <option value="ANNUALLY">Annually</option>
                </select>
                {errors.recurrencePeriod && <span className="error">{errors.recurrencePeriod}</span>}
              </div>

              {errors.submit && <div className="error-message">{errors.submit}</div>}

              <button type="submit" className="submit-btn">Create Payment</button>
            </form>


            <div className="daily-payments">
              <h3>Payments on {selectedDate?.toLocaleDateString()}</h3>
              {dailyPayments.length === 0 ? (
                <p>No payments found for this day.</p>
              ) : (
                <ul>
                  {dailyPayments.map((payment) => (
                    <li key={payment.id}>
                      {payment.description} - €{payment.amount.toFixed(2)} -
                      <label style={{ marginLeft: '8px' }}>
                        <input
                          type="checkbox"
                          checked={payment.paid}
                          onChange={() => handleTogglePaid(payment.id)}
                        /> {payment.paid ? "Paid" : "Unpaid"}
                      </label>
                    </li>
                  ))}
                </ul>
              )}
            </div>

          </div>
        </div>
      )}
    </div>
  );
}

export default CalendarPage;
