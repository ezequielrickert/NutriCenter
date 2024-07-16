import React, { useState } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";

const UniversalLogin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const loginData = {
        username: username,
        password: password
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        await axios.post("http://localhost:8080/authenticateUser", loginData)
            .then(res => {
                if (res.status === 200) {
                    localStorage.setItem('token', res.data.token);
                    localStorage.setItem('username', res.data.username);
                    localStorage.setItem('role', res.data.role);
                    const redirectPath = res.data.role === 'customer' ? '/dashboardCustomer' :
                        res.data.role === 'nutritionist' ? '/dashboardNutritionist' :
                            res.data.role === 'store' ? '/dashboardStore' :
                                res.data.role === 'superAdmin' ? '/initialEditor' :
                                    '/universalLogin';
                    window.location.href = redirectPath;
                }
            })
            .catch(err => {
                console.log(err);
                alert('Login failed, please try again.');
            });
    }

    return (
        <div className="container" style={{ maxWidth: "400px", marginTop: "50px" }}>
            <h1 className="text-center mb-4">Login</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label className="form-label">Username:</label>
                    <input type="text" className="form-control" value={username} onChange={e => setUsername(e.target.value)} required />
                </div>
                <div className="mb-4">
                    <label className="form-label">Password:</label>
                    <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
                </div>
                <div className="d-flex justify-content-between">
                    <button type="submit" className="btn btn-primary flex-fill me-2" style={{ minWidth: "120px" }}>Login</button>
                    <Link to="/" className="btn btn-secondary flex-fill" style={{ minWidth: "120px" }}>Go Back</Link>
                </div>
            </form>
            <p className="text-center mt-3">
                Don't have an account? <Link to="/defaultSignUp">Sign Up</Link>
            </p>
        </div>
    );
}

export default UniversalLogin;
