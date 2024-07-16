import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Footer from "../../components/footer";
import './customerSubscriptionList.css'; // Asegúrate de crear este archivo CSS

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
                if (response.data === "User is valid" && userRole === "nutritionist") {
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
                const response = await axios.get(`http://localhost:8080/subscription/nutritionist/${username}`);
                setCustomers(response.data);
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        fetchSubscriptions();
    }, []);

    if (!isValidUser) {
        return null; // Opcional: puedes mostrar un loader aquí
    }

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Subscribed Customers</h1>
            <ul className="list-group">
                {customers.map((customer) => (
                    <li className="list-group-item" key={customer.customerId}>
                        <Link to={`/clientHistory/${customer.customerName}`} className="text-decoration-none text-primary">
                            {customer.customerName}
                        </Link>
                    </li>
                ))}
            </ul>
            <Footer />
        </div>
    );
};

export default CustomerSubscriptionList;
