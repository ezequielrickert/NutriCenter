import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from '../../components/footer';
import {Link} from "react-router-dom";

const DashboardCustomer = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "customer") {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/universalLogin';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/universalLogin';
            }
        };

        validateUser();
    }, [token, username]);

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container">
            <header className="text-center my-5">
                <h1>Welcome to the Customer Dashboard</h1>
                <Link to="/mealTable">
                    <button style={{ marginRight: '10px' }} className="btn btn-primary mt-3">Add Meal</button>
                </Link>
                <Link to="/customer-subscriptions">
                    <button style={{ marginRight: '10px' }} className="btn btn-primary mt-3">View Subscriptions</button>
                </Link>
                <Link to="/clientHistory">
                    <button style={{ marginRight: '10px' }} className="btn btn-primary mt-3">My History</button>
                </Link>
                <Link to="/addWeight">
                    <button style={{ marginRight: '10px' }} className="btn btn-primary mt-3">Add Weight History</button>
                </Link>
                <Footer />
            </header>
        </div>
    );
}

export default DashboardCustomer;