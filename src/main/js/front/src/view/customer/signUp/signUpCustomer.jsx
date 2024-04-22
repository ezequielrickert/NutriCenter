import React, {useState} from "react";
import axios from 'axios';

const SignUpCustomer =  () => {

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const signUpData = {
        customerName: username,
        customerEmail: email,
        customerPassword: password
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createCustomer",  signUpData )
            .then(async res => {
                await axios.post("http://localhost:8080/authenticateUser", {username, password})
                    .then(res => {
                        if (res.status === 200) {
                            // If the login is successful, store the token, username and rol
                            localStorage.setItem('token', res.data.token);
                            localStorage.setItem('username', res.data.username);
                            localStorage.setItem('role', res.data.role);
                            window.location.href = '/dashboardCustomer';
                        }
                    })
                    .catch(err => {
                        console.log(err);
                        alert('SignUp failed, please try again.')
                    })
            })
            .catch(err => {
                console.log(err);
                alert('Username already used.')
            })
    }

    return (
        <div className="App">
            <header className="App-header">
                <h1>Customer SignUp</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username"> Username:</label><br/>
                    <input type="text" id="username" name="username" value={username}
                           onChange={e => setUsername(e.target.value)}/><br/>
                    <label htmlFor="email">Enter eMail:</label><br/>
                    <input type="text" id="email" name="email" value={email}
                           onChange={e => setEmail(e.target.value)}/><br/>
                    <label htmlFor="email">Enter password:</label><br/>
                    <input type="text" id="password" name="password" value={password}
                           onChange={e => setPassword(e.target.value)}/><br/>
                    <input type="submit"/>
                </form>
            </header>
        </div>
    );
}

export default SignUpCustomer;