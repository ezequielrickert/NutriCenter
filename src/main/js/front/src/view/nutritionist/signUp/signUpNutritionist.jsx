import React, { useState } from "react";
import axios from 'axios';

const SignUpNutritionist = () => {
    const [username, setUsername] = useState('');
    const [mail, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [diploma, setDiploma] = useState('');

    const signUpData = {
        nutritionistName: username,
        nutritionistEmail: mail,
        nutritionistPassword: password,
        educationDiploma: diploma
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        await axios.post("http://localhost:8080/createNutritionist", signUpData)
            .then(async res => {
                await axios.post("http://localhost:8080/authenticateUser", { username, password })
                    .then(res => {
                        if (res.status === 200) {
                            localStorage.setItem('token', res.data.token);
                            localStorage.setItem('username', res.data.username);
                            localStorage.setItem('role', res.data.role);
                            window.location.href = '/dashboardNutritionist';
                        }
                    })
                    .catch(err => {
                        console.log(err);
                        alert('SignUp failed, please try again.')
                    });
            })
            .catch(err => {
                console.log(err);
                alert('Username already used.')
            });
    }

    const handleGoBack = () => {
        window.location.href = '/defaultSignUp';
    };

    return (
        <div className="container">
            <h1 className="text-center mb-4">Nutritionist SignUp</h1>
            <form onSubmit={handleSubmit} className="mx-auto" style={{ maxWidth: "400px" }}>
                <div className="mb-3">
                    <label htmlFor="username" className="form-label">Username:</label>
                    <input type="text" id="username" className="form-control" value={username} onChange={e => setUsername(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">Enter eMail:</label>
                    <input type="email" id="email" className="form-control" value={mail} onChange={e => setEmail(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">Enter password:</label>
                    <input type="password" id="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label htmlFor="diploma" className="form-label">Education Diploma:</label>
                    <input type="text" id="diploma" className="form-control" value={diploma} onChange={e => setDiploma(e.target.value)} />
                </div>
                <div className="d-flex justify-content-between">
                    <input type="submit" className="btn btn-primary flex-fill me-1" value="Sign Up"/>
                    <button type="button" className="btn btn-secondary flex-fill ms-1" onClick={handleGoBack}>Go Back
                    </button>
                </div>
            </form>
        </div>
    );
}

export default SignUpNutritionist;
