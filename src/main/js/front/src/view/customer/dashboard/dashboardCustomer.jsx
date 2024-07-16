import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from '../../components/footer';
import { Link, useNavigate } from "react-router-dom";
import { Dropdown, Badge } from 'react-bootstrap';
import ringBell from '../images/ring_bell.png';
import bell from '../images/bell.png';
import './dashboardCustomer.css';

const DashboardCustomer = ({ handleMessagesRead }) => {
    const [isValidUser, setIsValidUser] = useState(false);
    const [messages, setMessages] = useState([]);
    const [unreadCount, setUnreadCount] = useState(0);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const navigate = useNavigate();

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

    const fetchMessages = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/message/unread/${username}`);
            const data = response.data;
            if (Array.isArray(data)) {
                setUnreadCount(data.length);
                setMessages(data);
            } else {
                console.error('Data received from server is not an array');
            }
        } catch (error) {
            console.error('Error fetching messages', error);
        }
    };

    useEffect(() => {
        fetchMessages();
    }, [username]);

    if (!isValidUser) {
        return null;
    }

    const formatMessage = (message) => {
        const { storeName, ingredientName, quantity } = message;
        if (quantity !== -1) {
            return `Stock updated at ${storeName}: ${quantity} of ${ingredientName} added.`;
        } else {
            return `New stock created at ${storeName}: ${ingredientName}.`;
        }
    };

    const recentMessages = messages.slice(0, 5);

    return (
        <div className="container">
            <header className="text-center my-5">
                <h1>Welcome to the Customer Dashboard</h1>
                <div className="header-buttons d-flex justify-content-center align-items-center my-4">
                    <Link to="/mealTable">
                        <button className="btn btn-primary mt-3">Add Meal</button>
                    </Link>
                    <Link to="/customer-subscriptions">
                        <button className="btn btn-primary mt-3">View Subscriptions</button>
                    </Link>
                    <Link to={`/clientHistory/${username}`}>
                        <button className="btn btn-primary mt-3">My History</button>
                    </Link>
                    <Link to="/addWeight">
                        <button className="btn btn-primary mt-3">Add Weight History</button>
                    </Link>
                    <div className="notification-dropdown ms-3">
                        <Dropdown>
                            <Dropdown.Toggle variant="light" id="dropdown-basic">
                                <img src={unreadCount > 0 ? ringBell : bell} alt="Notification Bell"/>
                                {unreadCount > 0 && (
                                    <Badge pill bg="danger" className="badge">
                                        {unreadCount}
                                    </Badge>
                                )}
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                {messages.length > 0 ? (
                                    <>
                                        {recentMessages.map((message, index) => (
                                            <Dropdown.Item key={index}>{formatMessage(message)}</Dropdown.Item>
                                        ))}
                                        <Dropdown.Item onClick={() => navigate('/inbox')}>View all</Dropdown.Item>
                                    </>
                                ) : (
                                    <Dropdown.Item>No new messages</Dropdown.Item>
                                )}
                            </Dropdown.Menu>
                        </Dropdown>
                    </div>
                </div>
            </header>
            <Footer/>
        </div>
    );
}

export default DashboardCustomer;
