import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";

const StoreSubscribers = () => {
    const [customers, setCustomers] = useState([]);
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && (userRole === "store")) {
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

    useEffect(() => {
        const fetchSubscriptions = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/subscription/store/${username}`);
                setCustomers(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchSubscriptions();
    }, []);

    return (
        <div>
            <h1>Subscribed Customers</h1>
            <ul>
                {customers.map((customer) => (
                    <li key={customer.customerName}>{customer}</li>
                ))}
            </ul>
            <Footer />

        </div>
    );
};

export default StoreSubscribers;