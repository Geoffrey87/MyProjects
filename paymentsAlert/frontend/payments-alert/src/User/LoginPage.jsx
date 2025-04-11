import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './LoginPage.css';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/users/login', {
                email,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {

                localStorage.setItem('authToken', response.data.token);
                navigate('/service-type');
            }
        } catch (error) {
            setError(error.response?.data?.message || 'Login failed. Please check your credentials.');
        }
    };

    return (
        <div className="login-container">
            <h2>Payments Alert</h2>
            <h3>Sign In</h3>

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
                <a href="/forgot-password" className="forgot-password">Forgot password?</a>
            </div>
        </div>
    );
}

export default LoginPage;