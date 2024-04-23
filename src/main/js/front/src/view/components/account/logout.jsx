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
        <div className="container">
            <h1 className="text-center my-5">Do you want to log out?</h1>
            <div className="d-flex justify-content-center">
                <button onClick={handleLogout} className="btn btn-primary me-2">Logout</button>
                <button onClick={handleCancel} className="btn btn-secondary">Cancel</button>
            </div>
            <Footer />
        </div>
    );
}

export default LogoutPage;