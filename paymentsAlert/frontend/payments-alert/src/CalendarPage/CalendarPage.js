import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './CalendarPage.css';
import LogoutButton from './LogoutButton';
import API from './api';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function toLocalDateOnlyString(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return date.toISOString().split('T')[0];
}

function CalendarPage() {
  const [date, setDate] = useState(new Date());
  const [username, setUsername] = useState('User');
  const [userId, setUserId] = useState(null);
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

  useEffect(() => {
    const fetchUserData = async () => {
      const authToken = localStorage.getItem('authToken');
      if (!authToken) return;

      try {
        const res = await API.get('/users/me', {
          headers: {
            Authorization: `Bearer ${authToken}`,
          },
        });

        const user = res.data;
        setUsername(user.username);
        setUserId(user.userId);
        localStorage.setItem('userId', user.id);

        console.log("Fetched user:", user);
      } catch (error) {
        console.error('Error getting username:', error);
      }
    };

    fetchUserData();
  }, []);

  const handleDateClick = async (date) => {
    setSelectedDate(date);
    setShowModal(true);
    setFormData(prev => ({ ...prev, dueDate: toLocalDateOnlyString(date) }));

    if (userId) {
      await fetchPaymentsForDate(date, userId);
    } else {
      const waitForUserId = () => {
        if (userId) {
          fetchPaymentsForDate(date, userId);
        } else {
          setTimeout(waitForUserId, 100);
        }
      };
      waitForUserId();
    }
  };

  const fetchPaymentsForDate = async (date, userId) => {
    const authToken = localStorage.getItem('authToken');
    if (!userId || !authToken) return;

    const dateString = date.toISOString().split('T')[0];

    try {
      const res = await API.get('/payments/by-date', {
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
      !['NONE', 'MONTHLY', 'QUARTERLY', 'SEMI_ANNUALLY', 'ANNUALLY'].includes(formData.recurrencePeriod)
    ) {
      newErrors.recurrencePeriod = 'Invalid frequency';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
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
      userId,
      amount: parseFloat(formData.amount),
      recurrencePeriod: recurrence,
    };

    console.log("Payload send:", data);

    try {
      const response = await API.post('/payments', data, {
        headers: {
          'Authorization': `Bearer ${authToken}`,
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 201) {
        setFormData({ description: '', amount: '', dueDate: '', recurrencePeriod: '' });
        setShowModal(false);

        toast.success('Payment created successfully!')

        const dateToFetch = new Date(formData.dueDate);

        if (userId) {
          await fetchPaymentsForDate(dateToFetch, userId);
        } else {
          const waitForUserId = async () => {
            return new Promise((resolve) => {
              const check = () => {
                if (userId) {
                  resolve();
                } else {
                  setTimeout(check, 100);
                }
              };
              check();
            });
          };

          await waitForUserId();
          await fetchPaymentsForDate(dateToFetch, userId);
        }
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
      await API.patch(`/payments/${paymentId}/paid`, {
        paid: newPaidStatus
      }, {
        headers: {
          Authorization: `Bearer ${authToken}`
        }
      });
      await fetchPaymentsForDate(selectedDate, userId);
    } catch (error) {
      console.error("Error updating paid status:", error);
    }
  };

  const handleDeletePayment = async (paymentId) => {
    const authToken = localStorage.getItem('authToken');
    try {
      await API.delete(`/payments/${paymentId}`, {
        headers: { Authorization: `Bearer ${authToken}` },
      });
      await fetchPaymentsForDate(selectedDate, userId);
    } catch (error) {
      console.error('Error deleting payment:', error);
    }
  };

  return (
    <div className="max-w-4xl mx-auto mt-10 p-6 bg-white rounded-2xl shadow-md">
    <ToastContainer position="top-center" autoClose={3000} hideProgressBar={false} />
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Hello {username} 👋</h1>
        <LogoutButton />
      </div>

      <Calendar
        onChange={setDate}
        onClickDay={handleDateClick}
        value={date}
        className="react-calendar"
      />

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-xl shadow-lg w-full max-w-md relative">
            <button
              className="absolute top-2 right-3 text-2xl font-bold text-gray-500 hover:text-gray-700"
              onClick={() => {
                setShowModal(false);
                setFormData({ description: '', amount: '', dueDate: '', recurrencePeriod: '' });
              }}
            >×</button>

            <h2 className="text-xl font-semibold mb-4">New Payment</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium">Description</label>
                <input
                  type="text"
                  className="w-full border rounded p-2"
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                  required
                />
                {errors.description && <p className="text-red-500 text-sm">{errors.description}</p>}
              </div>

              <div>
                <label className="block text-sm font-medium">Amount</label>
                <input
                  type="number"
                  step="0.01"
                  className="w-full border rounded p-2"
                  value={formData.amount}
                  onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                  required
                />
                {errors.amount && <p className="text-red-500 text-sm">{errors.amount}</p>}
              </div>

              <div>
                <label className="block text-sm font-medium">Recurrence</label>
                <select
                  className="w-full border rounded p-2"
                  value={formData.recurrencePeriod}
                  onChange={(e) => setFormData({ ...formData, recurrencePeriod: e.target.value })}
                >
                  <option value="NONE">None</option>
                  <option value="MONTHLY">Monthly</option>
                  <option value="QUARTERLY">Quarterly</option>
                  <option value="SEMI_ANNUALLY">Semi-Annually</option>
                  <option value="ANNUALLY">Annually</option>
                </select>
                {errors.recurrencePeriod && <p className="text-red-500 text-sm">{errors.recurrencePeriod}</p>}
              </div>

              {errors.submit && <p className="text-red-500 text-sm">{errors.submit}</p>}

              <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
                Create Payment
              </button>
            </form>

            <div className="mt-6">
              <h3 className="text-lg font-medium mb-2">
                Payments on {selectedDate?.toLocaleDateString()}
              </h3>
              {dailyPayments.length === 0 ? (
                <p className="text-gray-500 italic">No payments found for this day.</p>
              ) : (
                <ul className="space-y-2">
                  {dailyPayments.map((payment) => (
                    <li key={payment.id} className="p-3 bg-gray-100 rounded">
                      <div className="flex justify-between items-center">
                        <div>
                          <p className="font-semibold">{payment.description}</p>
                          <div className="flex items-center space-x-2">
                            <p>€{payment.amount.toFixed(2)} - {payment.paid ? 'Paid' : 'Unpaid'}</p>
                            <input
                              type="checkbox"
                              checked={payment.paid}
                              onChange={() => handleTogglePaid(payment.id)}
                            />
                          </div>
                        </div>
                        <button
                          className="text-red-500 hover:underline text-sm"
                          onClick={() => handleDeletePayment(payment.id)}
                        >
                          Delete
                        </button>
                      </div>
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
