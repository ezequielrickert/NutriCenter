import React, {useState} from "react";
import axios from 'axios';
import { Link } from 'react-router-dom';



const SignUp =  () => {


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
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    return (
        <div className="App">
            <header className="App-header">
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

export default SignUp;