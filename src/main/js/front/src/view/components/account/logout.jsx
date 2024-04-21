import React, { useState } from 'react';
import Footer from '../footer';

const LogoutPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Aquí iría la lógica para eliminar la cuenta
        console.log(`Username: ${username}, Password: ${password}`);
    }

    return (
        <div>
            <h1>Logout Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Email:
                    <input type="username" value={username} onChange={e => setUsername(e.target.value)} required />
                </label>
                <label>
                    Password:
                    <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
                </label>
                <input type="submit" value="Logout" />
            </form>
            <Footer />
        </div>
    );
}

export default LogoutPage;