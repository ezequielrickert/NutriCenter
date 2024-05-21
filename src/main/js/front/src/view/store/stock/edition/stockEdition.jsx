import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Table, Modal, Form } from 'react-bootstrap';

const StockEditor = () => {
    //validation
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    //message
    const [showMessage, setShowMessage] = useState(false);
    const [messageContent, setMessageContent] = useState('');
    //ingredients/stocks
    const [ingredients, setIngredientOptions] = useState([]);
    const [stocks, setStocks] = useState([]);
    //Modal
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState('Create Stock');
    const [buttonText, setButtonText] = useState('Create');
    //create/update
    const [editingStock, setEditingStock] = useState(null);
    const [newQuantity, setNewQuantity] = useState(0);
    const [newBrandName, setNewBrandName] = useState('');

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
        if (isValidUser) {
            fetchStocks();
        }
    }, [isValidUser]);

    const fetchStocks = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/stock/${username}`);
            if (Array.isArray(response.data)) {
                setStocks(response.data);
            } else {
                console.error('Data received from server is not an array');
            }
        } catch (error) {
            console.error('There was an error fetching stocks', error);
        }
    };

    useEffect(() => {
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setIngredientOptions(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
    }, []);

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
        setTimeout(() => {
            setShowMessage(false);
        }, 5000);
    };

    const handleCreate = async () => {
        const stockData = {
            storeName: username,
            ingredientId: ingredient,
            quantity: quantity,
            brand: brand
        };

        await axios.post("http://localhost:8080/addStock", stockData)
            .then(async res => {
                console.log(res);
                // Close the modal
                setShowModal(false);
                // Reset the modal
                resetModal();
                // Show success message
                displayMessage('Recipe created successfully');
                // Fetch the updated recipe list
                const response = await axios.get(`http://localhost:8080/recipes/${username}`);
                if (Array.isArray(response.data)) {
                    setRecipes(response.data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(err => console.log(err));
    };

    const prepareForEdit = (stock) => {
        setEditingStock(stock);
        setModalTitle('Edit Stock');
        setButtonText('Update');
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setModalTitle('Create Stock');
        setButtonText('Create');
        setEditingStock(null);
    };

    if (!isValidUser) {
        return null;
    }

    return (
        <div className="container my-3">
            <h1>Stock Management</h1>
            <Button variant="success" onClick={() => setShowModal(true)} style={{ marginBottom: '20px' }}>Create Stock</Button>
            {showMessage && <div className="alert alert-success">{messageContent}</div>}
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Ingredient</th>
                    <th>Quantity</th>
                    <th>Brand</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {stocks.map((stock, index) => (
                    <tr key={index}>
                        <td>{stock.ingredientName}</td>
                        <td>{stock.quantity}</td>
                        <td>{stock.brand}</td>
                        <td>
                            <Button variant="warning" onClick={() => prepareForEdit(stock)} style={{ marginRight: '10px' }}>Edit</Button>
                            <Button variant="danger" onClick={() => handleDelete(stock.id)}>Delete</Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            {/* Modal for creating/updating stock */}
            {/* The form inside the modal should be implemented to handle stock data */}
        </div>
    );
};

export default StockEditor;