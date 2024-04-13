import React, {useState} from "react";
import axios from 'axios';

const SignUpNutritionist =  () => {

    const [name, setUsername] = useState('');
    const [mail, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [diploma, setDiploma] = useState('')

    const signUpData = {
        nutritionistName: name,
        nutritionistEmail: mail,
        nutritionistPassword: password,
        educationDiploma: diploma
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createNutritionist", signUpData)
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    return (
        <div className="App">
            <header className="App-header">
                <h1>Nutritionist SignUp</h1>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="username"> Username:</label><br/>
                    <input type="text" id="username" name="username" value={name}
                           onChange={e => setUsername(e.target.value)}/><br/>
                    <label htmlFor="email">Enter eMail:</label><br/>
                    <input type="text" id="email" name="email" value={mail}
                           onChange={e => setEmail(e.target.value)}/><br/>
                    <label htmlFor="email">Enter password:</label><br/>
                    <input type="text" id="password" name="password" value={password}
                           onChange={e => setPassword(e.target.value)}/><br/>
                    <label htmlFor="diploma">Enter diploma:</label><br/>
                    <input type="text" id="diploma" name="diploma" value={diploma}
                           onChange={e => setDiploma(e.target.value)}/><br/>
                    <input type="submit"/>
                </form>
            </header>
        </div>
    );
}

export default SignUpNutritionist;