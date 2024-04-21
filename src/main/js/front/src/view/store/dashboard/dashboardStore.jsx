import React, { useEffect, useState } from 'react';
import axios from 'axios';

const DashboardStore = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "store") {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/loginStore';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/loginStore';
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
                <h1>Welcome to the Store Dashboard</h1>
                { /*add dashboard content here*/ }
            </header>
        </div>
    );
}

export default DashboardStore;