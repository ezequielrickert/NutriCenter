import React, {useState} from "react";
import axios from 'axios';

const SignUpNutritionist =  () => {

    const [username, setUsername] = useState('');
    const [mail, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [diploma, setDiploma] = useState('')

    const signUpData = {
        nutritionistName: username,
        nutritionistEmail: mail,
        nutritionistPassword: password,
        educationDiploma: diploma
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createNutritionist", signUpData)
            .then(async res => {
                await axios.post("http://localhost:8080/authenticateUser", {username, password})
                    .then(res => {
                        if (res.status === 200) {
                            // If the login is successful, store the token, username and rol
                            localStorage.setItem('token', res.data.token);
                            localStorage.setItem('username', res.data.username);
                            localStorage.setItem('role', res.data.role);
                            window.location.href = '/dashboardNutritionist';
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
        <div className="container">
            <h1 className="text-center my-5">Nutritionist SignUp</h1>
            <form onSubmit={handleSubmit} className="mx-auto" style={{maxWidth: "300px"}}>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Username:</label>
                    <input type="text" id="username" name="username" value={username} className="form-control"
                           onChange={e => setUsername(e.target.value)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">Enter eMail:</label>
                    <input type="email" id="email" name="email" value={mail} className="form-control"
                           onChange={e => setEmail(e.target.value)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Enter password:</label>
                    <input type="password" id="password" name="password" value={password} className="form-control"
                           onChange={e => setPassword(e.target.value)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="diploma" className="form-label">Enter diploma:</label>
                    <input type="text" id="diploma" name="diploma" value={diploma} className="form-control"
                           onChange={e => setDiploma(e.target.value)}/>
                </div>
                <input type="submit" className="btn btn-primary" value="Sign Up" />
            </form>
        </div>
    );
}

export default SignUpNutritionist;