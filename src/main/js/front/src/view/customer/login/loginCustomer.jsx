import React, {useState} from "react";
import axios from 'axios';

const LoginCustomer =  () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('')

    const loginData = {
        username: username,
        password: password
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/authenticateUser",  loginData )
            .then(res => {
                if (res.status === 200) {
                    // If the login is successful, store the token and redirect to the dashboard
                    // The token is stored as an Auth class in the local storage
                    localStorage.setItem('token', res.data);
                    localStorage.setItem('username', username);
                    window.location.href = '/dashboardCustomer';
                } else {
                    // If the login is not successful, handle the error
                    console.log('Login failed in customer login page');
                }
            })
            .catch(err => console.log(err))
    }

    return (
        <div className="App">
            <header className="App-header">
                <h1>Customer Login</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username"> Username:</label><br/>
                    <input type="text" id="username" name="username" value={username}
                           onChange={e => setUsername(e.target.value)}/><br/>
                    <label htmlFor="password">Enter password:</label><br/>
                    <input type="password" id="password" name="password" value={password}
                           onChange={e => setPassword(e.target.value)}/><br/>
                    <input type="submit"/>
                </form>
            </header>
        </div>
    );
}

export default LoginCustomer;
