import React, { useState } from 'react';
import Footer from '../footer';

const LogoutPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Aquí iría la lógica para eliminar la cuenta
        console.log(`Email: ${email}, Password: ${password}`);
    }

    return (
        <div>
            <h1>Logout Page</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Email:
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} required />
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