import React, {useState} from "react";
import axios from 'axios';


const Login =  () => {


    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('')

    const loginData = {
        clientName: username,
        clientEmail: email,
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createCustomer", { loginData })
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
                    <input type="submit"/>
                </form>
            </header>
        </div>
    );
}

export default Login;