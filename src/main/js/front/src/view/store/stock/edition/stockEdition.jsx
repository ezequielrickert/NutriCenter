import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Table, Modal, Form } from 'react-bootstrap';
import Footer from "../../../components/footer";

const StockEditor = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [showMessage, setShowMessage] = useState(false);
    const [messageContent, setMessageContent] = useState('');
    const [ingredients, setIngredientOptions] = useState([]);
    const [stocks, setStocks] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState('Create Stock');
    const [buttonText, setButtonText] = useState('Create');
    const [editingStock, setEditingStock] = useState(null);
    const [selectedIngredient, setSelectedIngredient] = useState('');
    const [quantity, setSelectedQuantity] = useState('');
    const [brand, setSelectedBrand] = useState('');
    const [newQuantity, setNewQuantity] = useState(0);
    const [newBrandName, setNewBrandName] = useState('');
    const [isEditing, setIsEditing] = useState(false); // Agregado para manejar si se está editando

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "store") {
                    setIsValidUser(true);
                } else {
                    console.error("User validation failed");
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
            setStocks(response.data);
        } catch (error) {
            console.error('There was an error fetching stocks', error);
        }
    };

    useEffect(() => {
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                setIngredientOptions(JSON.parse(response.data));
            })
            .catch(error => console.error('Error fetching ingredients', error));
    }, []);

    useEffect(() => {
        if (editingStock) {
            setNewQuantity(editingStock.quantity);
            setNewBrandName(editingStock.brand);
            setIsEditing(true); // Ajuste para manejar la edición
        }
    }, [editingStock]);

    const isFormComplete = () => {
        return newQuantity && newBrandName;
    };

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
        setTimeout(() => setShowMessage(false), 5000);
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
        resetModal();
    };

    const resetModal = () => {
        setNewQuantity(0);
        setNewBrandName('');
        setIsEditing(false); // Resetear el estado de edición
    };

    const handleSubmit = async () => {
        if (isEditing) {
            await handleUpdate();
        } else {
            await handleCreate();
        }
    };

    const handleCreate = async () => {
        const stockData = {
            storeName: username,
            ingredientId: selectedIngredient,
            quantity: quantity,
            brand: brand
        };

        try {
            await axios.post("http://localhost:8080/addStock", stockData);
            displayMessage('Stock added successfully');
            fetchStocks();
        } catch (error) {
            console.error('Error adding stock', error);
        } finally {
            closeModal();
        }
    };

    const handleUpdate = async () => {
        if (editingStock) {
            const stockData = {
                storeName: username,
                ingredientId: editingStock.ingredientId, // Asumir que editingStock tiene ingredientId
                quantity: newQuantity,
                brand: newBrandName
            };

            try {
                await axios.post(`http://localhost:8080/updateStock`, stockData);
                displayMessage('Stock updated successfully');
                fetchStocks();
            } catch (error) {
                console.error('Error updating stock', error);
            } finally {
                closeModal();
            }
        }
    };

    const handleDelete = async (stock) => {
        if (window.confirm('Are you sure you want to delete this stock?')) {
            try {
                await axios.post('http://localhost:8080/deleteStock', { storeName: username, ingredientId: stock.ingredientId });
                displayMessage('Stock deleted successfully');
                setStocks(stocks.filter(s => s.id !== stock.id));
            } catch (error) {
                console.error('Error deleting stock', error);
            }
        }
    };

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
                        <td>{stock.ingredient}</td>
                        <td>{stock.quantity}</td>
                        <td>{stock.brand}</td>
                        <td>
                            <Button variant="warning" onClick={() => prepareForEdit(stock)}
                                    style={{ marginRight: '10px' }}>
                                Edit
                            </Button>
                            <Button variant="danger" onClick={() => handleDelete(stock.id)}>
                                Delete
                            </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{isEditing ? 'Edit Stock' : 'Create Stock'}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {!isEditing && (
                        <Form.Group controlId="formIngredientSelect">
                            <Form.Label>Ingredient</Form.Label>
                            {/* Componente de selección de ingredientes */}
                        </Form.Group>
                    )}
                    <Form.Group controlId="formQuantity">
                        <Form.Label>Quantity</Form.Label>
                        <Form.Control type="number" value={selectedQuantity} onChange={(e) => setSelectedQuantity(e.target.value)} />
                    </Form.Group>
                    <Form.Group controlId="formBrand">
                        <Form.Label>Brand</Form.Label>
                        <Form.Control type="text" value={selectedBrand} onChange={(e) => setSelectedBrand(e.target.value)} />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>Close</Button>
                    <Button variant="primary" onClick={handleSubmit}>{isEditing ? 'Update' : 'Create'}</Button>
                </Modal.Footer>
            </Modal>;
            <Footer />
        </div>
    );
};

export default StockEditor;