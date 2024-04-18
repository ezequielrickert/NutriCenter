import React, {useState} from "react";
import axios from 'axios';

const SignUpStore =  () => {

    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const signUpData = {
        storeName: username,
        storeEmail: email,
        storePassword: password
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createStore",  signUpData )
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    return (
        <div className="App">
            <header className="App-header">
                <h1>Store SignUp</h1>
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

export default SignUpStore;