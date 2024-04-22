import React from 'react';
import Footer from '../footer';

const LogoutPage = () => {

    const handleLogout = () => {
        localStorage.setItem("token", "");
        localStorage.setItem("username", "");
        localStorage.setItem("role", "");
        window.location.href = '/universalLogin';
    }

    const handleCancel = () => {
        window.location.href = '/accountSelection';
    }

    return (
        <div>
            <h1>Do you want to log out?</h1>
            <button onClick={handleLogout}>Logout</button>
            <button onClick={handleCancel}>Cancel</button>
            <Footer />
        </div>
    );
}

export default LogoutPage;