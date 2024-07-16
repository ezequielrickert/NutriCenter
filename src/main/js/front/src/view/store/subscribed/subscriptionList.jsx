import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import './customerSubscriptionList.css';

const CustomerSubscriptionList = () => {
    const [customers, setCustomers] = useState([]);
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

    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Subscribed Customers</h1>
            <div className="card">
                <div className="card-body">
                    <ul className="list-group">
                        {customers.length > 0 ? (
                            customers.map((customer) => (
                                <li className="list-group-item" key={customer.customerId}>
                                    {customer.customerName}
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No subscribed customers found.</li>
                        )}
                    </ul>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default CustomerSubscriptionList;
