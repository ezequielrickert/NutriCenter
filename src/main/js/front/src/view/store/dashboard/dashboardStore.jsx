import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import { Link } from 'react-router-dom';

const DashboardStore = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const [storeName, setStoreName] = useState('');
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "store") {
                    setIsValidUser(true);
                    setStoreName(username); // Asumiendo que el username es el nombre de la tienda
                } else {
                    window.location.href = '/universalLogin';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/universalLogin';
            }
        };

        validateUser();
    }, [token, username, userRole]);

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container my-5">
            <header className="text-center">
                <h1>Welcome to the Store Dashboard</h1>
                <Link to="/stockEdition">
                    <button>Create Stock</button>
                </Link>
                <Link to="/storeSubscribers">
                    <button>View Subscriptions</button>
                </Link>
                <Footer />
            </header>
        </div>
    );
};

export default DashboardStore;
