import React, { useState } from 'react';
import axios from 'axios';

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
                        window.location.href = '/dashboardAdmin';
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
        <div>
            <h1>Universal Login</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" value={username} onChange={e => setUsername(e.target.value)} required />
                </label>
                <label>
                    Password:
                    <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
                </label>
                <input type="submit" value="Login" />
            </form>
        </div>
    );
}

export default UniversalLogin;