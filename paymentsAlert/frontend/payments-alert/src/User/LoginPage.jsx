import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './LoginPage.css';
import API from '../api'

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await API.post('/login', {
                email,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            const { token, userId } = response.data;

            if (token) {
                localStorage.setItem('authToken', token);
                localStorage.setItem('userId', userId);
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                navigate('/calendar');
            } else {
                setError("Login succeeded but token is missing.");
                console.warn("Login token missing:", response.data);
            }

        } catch (error) {
            console.error("Error:", error.response || error);
            setError('Invalid username or password');
        }
    };

    return (
        <div className="login-container">
            <h2>Payments Alert</h2>

            {error && <p className="error-message">{error}</p>}

            <form onSubmit={handleLogin}>
                <div className="input-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Enter your email"
                        required
                        autoComplete="username"
                    />
                </div>

                <div className="input-group">
                    <label htmlFor="password">Password:</label>
                    <div className="password-input">
                        <input
                            type={showPassword ? "text" : "password"}
                            id="password"
                            name="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                            required
                            autoComplete="current-password"
                        />
                        <button
                            type="button"
                            className="toggle-password"
                            onClick={() => setShowPassword(!showPassword)}
                        >
                            {showPassword ? '🙈' : '👁️'}
                        </button>
                    </div>
                </div>

                <button type="submit" className="login-button">Log In</button>
            </form>

            <div className="additional-options">
                <p>
                    Don't have an account?
                    <a href="/register" className="register-link"> Create account</a>
                </p>

            </div>
        </div>
    );
}

export default LoginPage;
