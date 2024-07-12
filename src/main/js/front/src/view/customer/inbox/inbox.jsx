import React, { useEffect, useState } from 'react';
import Footer from "../../components/footer";
import { Button, ListGroup, Container, Pagination, Row, Col } from 'react-bootstrap';
import { FaCheckCircle } from 'react-icons/fa';
import { CSSTransition, TransitionGroup } from 'react-transition-group';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './Inbox.css';

const Inbox = ({ onMessagesRead }) => {
    const [messages, setMessages] = useState([]);
    const [markAllReadAnimation, setMarkAllReadAnimation] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const messagesPerPage = 6;
    const username = localStorage.getItem("username");

    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/message/unread/${username}`);
                const data = response.data;
                if (Array.isArray(data)) {
                    setMessages(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('Error fetching messages', error);
            }
        };

        fetchMessages();
    }, [username, markAllReadAnimation]);

    const handleMarkAsRead = async (messageId) => {
        try {
            await axios.put(`http://localhost:8080/message/read/${messageId}`);
            setMessages(prevMessages => prevMessages.filter(message => message.id !== messageId));
            onMessagesRead();
        } catch (error) {
            console.error('Error marking message as read', error);
        }
    };

    const handleMarkAllAsRead = async () => {
        try {
            await axios.put(`http://localhost:8080/message/readAll/${username}`);
            setMarkAllReadAnimation(true);
            setTimeout(() => {
                setMessages([]);
                setMarkAllReadAnimation(false);
            }, 1000);
            onMessagesRead();
        } catch (error) {
            console.error('Error marking all messages as read', error);
        }
    };

    const formatMessage = (message) => {
        const { storeName, ingredientName, quantity } = message;
        const camelCaseStoreName = storeName.replace(/\b\w/g, (char) => char.toUpperCase());
        const formattedStoreName = storeName.endsWith('s') ? `${camelCaseStoreName}' ` : `${camelCaseStoreName}'s `;
        const formattedIngredientName = ingredientName.replace(/\b\w/g, (char) => char.toUpperCase()); // Convert to Camel Case

        if (quantity !== -1) {
            return (
                <span>
                    Stock updated at <Link to={`/storeProfile/${storeName}`} className="store-link">{formattedStoreName}</Link>store: <span className="quantity">{quantity}</span> of  <Link to={`/ingredientInfo/${ingredientName}`} className="ingredient-link">{formattedIngredientName}</Link> added.
                </span>
            );
        } else {
            return (
                <span>
                    New stock created at <Link to={`/storeProfile/${storeName}`} className="store-link">{formattedStoreName}</Link>store: <Link to={`/ingredientInfo/${ingredientName}`} className="ingredient-link">{formattedIngredientName}</Link>.
                </span>
            );
        }
    };

    const totalPages = Math.ceil(messages.length / messagesPerPage);
    const currentMessages = messages.slice((currentPage - 1) * messagesPerPage, currentPage * messagesPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    return (
        <Container className="my-5">
            <header className="text-center">
                <h1>Inbox</h1>
                {messages.length > 0 && (
                    <Button variant="success" onClick={handleMarkAllAsRead} className="my-3">
                        Mark All as Read
                    </Button>
                )}
            </header>
            <Row className="g-3">
                <TransitionGroup component={null}>
                    {currentMessages.length > 0 ? (
                        currentMessages.map((message) => (
                            <CSSTransition key={message.id} timeout={500} classNames="fade">
                                <Col key={message.id} xs={12} md={6}>
                                    <ListGroup.Item className="message-item">
                                        <span className="message-text">{formatMessage(message)}</span>
                                        {!message.isRead && (
                                            <Button variant="outline-success" onClick={(e) => { e.stopPropagation(); handleMarkAsRead(message.id); }}>
                                                <FaCheckCircle />
                                            </Button>
                                        )}
                                    </ListGroup.Item>
                                </Col>
                            </CSSTransition>
                        ))
                    ) : (
                        <CSSTransition key="no-messages" timeout={500} classNames="fade">
                            <Col xs={12}>
                                <p className="text-center">No messages found.</p>
                            </Col>
                        </CSSTransition>
                    )}
                </TransitionGroup>
            </Row>
            <Footer />
            {totalPages > 1 && (
                <Pagination className="justify-content-center mt-4">
                    {[...Array(totalPages)].map((_, index) => (
                        <Pagination.Item
                            key={index + 1}
                            active={index + 1 === currentPage}
                            onClick={() => handlePageChange(index + 1)}
                        >
                            {index + 1}
                        </Pagination.Item>
                    ))}
                </Pagination>
            )}
        </Container>
    );
}

export default Inbox;
