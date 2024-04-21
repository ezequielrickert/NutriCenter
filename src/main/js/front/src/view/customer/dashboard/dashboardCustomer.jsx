import React, { useEffect, useState } from 'react';
import axios from 'axios';
import React from 'react';
import Footer from '../../components/footer';

const DashboardCustomer = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid") {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/loginCustomer';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/loginCustomer';
            }
        };

        validateUser();
    }, [token, username]);

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    return (
        <div className="App">
            <header className="App-header">
                <h1>Welcome to the Customer Dashboard</h1>
                { /*add dashboard content here*/ }
                <Footer />
            </header>
        </div>
    );
}

export default DashboardCustomer;