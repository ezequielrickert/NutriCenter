import React, { useState } from 'react';
import './login.css'; // Reusing the same CSS file

function SignUp() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission here
        console.log(`Name: ${name}, Email: ${email}, Password: ${password}`);
    };

    return (
        <div className="login-container"> {/* Reusing the same CSS class */}
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input type="text" value={name} onChange={e => setName(e.target.value)} />
                </label>
                <label>
                    Email:
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} />
                </label>
                <label>
                    Password:
                    <input type="password" value={password} onChange={e => setPassword(e.target.value)} />
                </label>
                <input type="submit" value="Sign Up" />
            </form>
        </div>
    );
}

export default SignUp;