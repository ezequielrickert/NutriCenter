import React, { useEffect, useState } from 'react';
import Footer from "../../components/footer";
import axios from "axios";

const Inbox = ({ onMessagesRead }) => {
    const [messages, setMessages] = useState([]);
    const username = localStorage.getItem("username");

    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/message/unread/${username}`);
                const data = response.data;
                if (Array.isArray(data)) {
                    setMessages(data.reverse());
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('Error fetching messages', error);
            }
        };

        fetchMessages();
    }, [username]);

    const handleMarkAsRead = async (messageId) => {
        try {
            await axios.put(`http://localhost:8080/message/read/${messageId}`);
            setMessages(prevMessages => prevMessages.filter(message => message.id !== messageId));
            onMessagesRead();
        } catch (error) {
            console.error('Error marking message as read', error);
        }
    };

    return (
        <div className="container">
            <header className="text-center my-5">
                <h1>Inbox</h1>
                {messages.length > 0 ? (
                    <ul>
                        {messages.map((message) => (
                            <li key={message.id} style={{ display: 'flex', alignItems: 'center' }}>
                                <span>{message.message}</span>
                                {!message.isRead && (
                                    <button
                                        onClick={() => handleMarkAsRead(message.id)}
                                        style={{ marginLeft: '10px' }}
                                    >
                                        ✓
                                    </button>
                                )}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No messages found.</p>
                )}
                <Footer />
            </header>
        </div>
    );
}

export default Inbox;
