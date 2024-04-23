import React, { useState } from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";

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
                    // If the login is successful, store the token, username and rol
                    localStorage.setItem('token', res.data.token);
                    localStorage.setItem('username', res.data.username);
                    localStorage.setItem('role', res.data.role);
                    if(res.data.role === 'customer') {
                        window.location.href = '/dashboardCustomer';
                    }
                    else if(res.data.role === 'nutritionist'){
                        window.location.href = '/dashboardNutritionist';
                    }
                    else if(res.data.role === 'store'){
                        window.location.href = '/dashboardStore';
                    }
                    else if(res.data.role === 'superAdmin'){
                        window.location.href = '/initialEditor';
                    }
                    else {
                        alert('Invalid role: ' + res.data.role);
                        window.location.href = '/universalLogin.jsx';
                    }
                }
            })
            .catch(err => {
                console.log(err);
                alert('Login failed, please try again.')
            })
    }

    return (
        <div className="container">
            <h1 className="text-center my-5">Universal Login</h1>
            <form onSubmit={handleSubmit} className="mx-auto" style={{maxWidth: "300px"}}>
                <div className="mb-3">
                    <label className="form-label">Username:</label>
                    <input type="text" className="form-control" value={username} onChange={e => setUsername(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label className="form-label">Password:</label>
                    <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
                </div>
                <input type="submit" className="btn btn-primary" value="Login" />
            </form>
            <div className="text-center mt-3">
                <Link to="/" className="btn btn-secondary">Go Back</Link> {/* Botón de regreso */}
            </div>
        </div>
    );
}

export default UniversalLogin;