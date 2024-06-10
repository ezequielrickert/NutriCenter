import React, { useEffect, useState } from 'react';
import Footer from "../../components/footer";
import axios from "axios";

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const username = localStorage.getItem("username");

    useEffect(() => {
        axios.get(`http://localhost:8080/message/${username}`)
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setMessages(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
    }, []);

    return (
        <div>
            <h1>Messages</h1>
            {messages.map((message, index) => (
                <p key={index}>{message.message}</p>
            ))}
            <Footer />
        </div>
    );
}

export default Inbox;