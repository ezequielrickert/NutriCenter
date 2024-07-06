import React, { useEffect, useState } from 'react';
import Footer from "../../components/footer";
import axios from "axios";
import { Button, ListGroup, Container, Modal } from 'react-bootstrap';
import { FaCheckCircle } from 'react-icons/fa'; // Importar FaCheckCircle
import { CSSTransition, TransitionGroup } from 'react-transition-group';
import './Inbox.css'; // Archivo CSS para estilos adicionales

const Inbox = ({ onMessagesRead }) => {
    const [messages, setMessages] = useState([]);
    const [selectedMessage, setSelectedMessage] = useState(null);
    const [markAllReadAnimation, setMarkAllReadAnimation] = useState(false); // Estado para la animación
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
    }, [username, markAllReadAnimation]); // Añade markAllReadAnimation como dependencia

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
            setMarkAllReadAnimation(true); // Activar animación al marcar todos como leídos
            setTimeout(() => {
                setMessages([]);
                setMarkAllReadAnimation(false); // Desactivar animación después de un tiempo
            }, 1000); // Tiempo suficiente para que termine la animación CSS
            onMessagesRead(); // Actualizar mensajes una vez marcados como leídos
        } catch (error) {
            console.error('Error marking all messages as read', error);
        }
    };

    const handleShowMessage = (message) => {
        setSelectedMessage(message);
    };

    const handleCloseModal = () => {
        setSelectedMessage(null);
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
            {/* Aplicar CSSTransition al contenedor principal de la lista */}
            <TransitionGroup component={ListGroup}>
                {messages.length > 0 ? (
                    messages.map((message) => (
                        <CSSTransition key={message.id} timeout={500} classNames="fade">
                            <ListGroup.Item className="d-flex justify-content-between align-items-center message-item">
                                <span className="message-text" onClick={() => handleShowMessage(message)}>
                                    {message.message}
                                </span>
                                {!message.isRead && (
                                    <Button variant="outline-success" onClick={() => handleMarkAsRead(message.id)} className="read-button">
                                        <FaCheckCircle /> {/* Utilizar FaCheckCircle aquí */}
                                    </Button>
                                )}
                            </ListGroup.Item>
                        </CSSTransition>
                    ))
                ) : (
                    <CSSTransition key="no-messages" timeout={500} classNames="fade">
                        <p className="text-center">No messages found.</p>
                    </CSSTransition>
                )}
            </TransitionGroup>
            <Footer />
            {selectedMessage && (
                <Modal show={true} onHide={handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Message Details</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p>{selectedMessage.message}</p>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            )}
        </Container>
    );
}

export default Inbox;
