import React from 'react';
import Footer from '../footer';
import axios from 'axios';

const LogoutPage = () => {

    const handleLogout = () => {
        const username = localStorage.getItem("username");
        const token = localStorage.getItem("token");

        axios.post("/logout", { username, token })
            .then(response => {
                // Clear local storage and redirect on successful logout
                localStorage.setItem("token", "");
                localStorage.setItem("username", "");
                localStorage.setItem("role", "");
                window.location.href = '/universalLogin';
            })
            .catch(error => {
                console.error("Logout failed:", error);
                // Handle logout failure (optional)
            });
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