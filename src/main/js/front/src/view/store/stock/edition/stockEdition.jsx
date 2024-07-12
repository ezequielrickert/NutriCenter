import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Table, Modal, Form } from 'react-bootstrap';
import Footer from '../../../components/footer';
import ReactSelect from 'react-select';

const StockEdition = () => {
    // Validation
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    // Message
    const [showMessage, setShowMessage] = useState(false);
    const [messageContent, setMessageContent] = useState('');

    // Stocks and Ingredients
    const [stocks, setStocks] = useState([]);
    const [ingredients, setIngredients] = useState([]);
    const [selectedIngredient, setSelectedIngredient] = useState(null);

    // Modal
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState('Create Stock');
    const [buttonText, setButtonText] = useState('Create');

    // Form Data
    const [quantity, setQuantity] = useState('');
    const [brand, setBrand] = useState('');
    const [price, setPrice] = useState(''); // Add price state
    const [editingStock, setEditingStock] = useState(null);
    const [formChanged, setFormChanged] = useState(false);

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
    }, [token, username, userRole]);

    useEffect(() => {
        const fetchStocks = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/stock/${username}`);
                if (Array.isArray(response.data)) {
                    setStocks(response.data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('There was an error fetching Stock!', error);
            }
        };

        const fetchIngredients = async () => {
            try {
                const response = await axios.get('http://localhost:8080/ingredients');
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setIngredients(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('There was an error fetching Ingredients!', error);
            }
        };

        if (isValidUser) {
            fetchStocks();
            fetchIngredients();
        }
    }, [isValidUser, username]);

    useEffect(() => {
        if (editingStock) {
            setSelectedIngredient(editingStock.ingredient);
            setQuantity(editingStock.quantity);
            setBrand(editingStock.brand);
            setPrice(editingStock.price); // Set price when editing
            setBrand(editingStock.id.brand);
        } else {
            setSelectedIngredient(null);
            setQuantity('');
            setBrand('');
            setPrice('');
        }
    }, [editingStock]);

    const isFormComplete = () => {
        return selectedIngredient && quantity && brand && price;
    };

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
        const timer = setTimeout(() => {
            setShowMessage(false);
        }, 5000);
        return () => clearTimeout(timer);
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
        setSelectedIngredient(null);
        setQuantity('');
        setBrand('');
        setPrice('');
        setEditingStock(null);
    };

    const handleCreate = async () => {
        const stockData = {
            storeName: username,
            ingredientId: selectedIngredient.ingredientId,
            quantity: quantity,
            brand: brand,
            price: price // Include price in stock data
        };

        try {
            const response = await axios.post('http://localhost:8080/addStock', stockData);
            if (response.data === "Stock created successfully") {
                displayMessage('Stock created successfully');
                const updatedStocks = await axios.get(`http://localhost:8080/stock/${username}`);
                setStocks(updatedStocks.data);
                closeModal();
                const broadcast =
                    await axios.post(`http://localhost:8080/message/${username}/${selectedIngredient.ingredientName}`);
                closeModal();
                if (broadcast.data === "Message sent successfully") {
                    console.log("Message sent successfully");
                } else {
                    console.error("Error sending message");
                }
            } else {
                displayMessage(response.data);
            }
        } catch (error) {
            if (error.response && error.response.data) {
                displayMessage(error.response.data);
            } else {
                displayMessage('Error creating stock');
            }
            console.error("Error creating stock", error);
        }
    };

    const handleUpdate = async () => {
        if (editingStock) {
            let previousQuantity = editingStock.quantity;
            const stockData = {
                stockId: editingStock.id,
                storeName: username,
                quantity: quantity,
                price: price // Include price in stock data
            };

            try {
                const response = await axios.post('http://localhost:8080/updateStock', stockData);
                if (response.data === "Stock updated successfully") {
                    displayMessage('Stock updated successfully');
                    const updatedStocks = await axios.get(`http://localhost:8080/stock/${username}`);
                    setStocks(updatedStocks.data);
                    closeModal();
                    if (previousQuantity === 0 && quantity > 0) {
                        const broadcast =
                            await axios.post(`http://localhost:8080/message/${username}/${selectedIngredient.ingredientName}/${quantity}`);
                        if (broadcast.data === "Message sent successfully") {
                            console.log("Message sent successfully");
                        } else {
                            console.error("Error sending message");
                        }
                    }
                } else {
                    displayMessage(response.data);
                }
            } catch (error) {
                if (error.response && error.response.data) {
                    displayMessage(error.response.data);
                } else {
                    displayMessage('Error updating stock');
                }
                console.error("Error updating stock", error);
            }
        }
    };

    const handleDelete = async (stock) => {
        const stockData = {
            stockId: stock.id
        };

        if (window.confirm('Are you sure you want to delete this stock?')) {
            try {
                const response = await axios.post('http://localhost:8080/deleteStock', stockData);
                if (response.data === "Stock deleted successfully") {
                    displayMessage('Stock deleted successfully');
                    setStocks(stocks.filter(s => s.id !== stock.id));
                } else {
                    displayMessage(response.data);
                }
            } catch (error) {
                if (error.response && error.response.data) {
                    displayMessage(error.response.data);
                } else {
                    displayMessage('Error deleting stock');
                }
                console.error("Error deleting stock", error);
            }
        }
    };

    const setShowModalCreation = () => {
        setShowModal(true);
    };

    let ingredientOptions = ingredients.map(ingredient => ({ value: ingredient, label: ingredient.ingredientName }));

    return (
        <div className="container my-3">
            <h1 className="text-center">Stocks</h1>
            <Button variant="success" onClick={setShowModalCreation} style={{ marginBottom: '20px' }}>Create Stock</Button>
            {showMessage && <div className="alert alert-success">{messageContent}</div>}
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Ingredient Name</th>
                    <th>Quantity</th>
                    <th>Brand</th>
                    <th>Price</th> {/* Add price column */}
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {stocks.map((stock, index) => (
                    <tr key={index}>
                        <td>{stock.ingredient.ingredientName}</td>
                        <td>{stock.quantity}</td>
                        <td>{stock.id.brand}</td>
                        <td>{stock.price}</td> {/* Display price */}
                        <td>
                            <Button variant="warning" onClick={() => prepareForEdit(stock)} style={{ marginRight: '10px' }}>
                                Edit
                            </Button>
                            <Button variant="danger" onClick={() => handleDelete(stock)}>
                                Delete
                            </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <Modal show={showModal} onHide={closeModal}>
                <Modal.Header>
                    <Modal.Title>{modalTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formIngredient">
                            <Form.Label>Ingredient</Form.Label>
                            <ReactSelect
                                options={ingredientOptions}
                                value={selectedIngredient ? ingredientOptions.find(option => option.value.ingredientId === selectedIngredient.ingredientId) : null}
                                onChange={(option) => setSelectedIngredient(option.value)}
                                isDisabled={!!editingStock}
                                isClearable={true}
                            />
                        </Form.Group>
                        <Form.Group controlId="formQuantity">
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter quantity"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formBrand">
                            <Form.Label>Brand</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter brand"
                                value={brand}
                                onChange={(e) => setBrand(e.target.value)}
                                disabled={!!editingStock}
                            />
                        </Form.Group>
                        <Form.Group controlId="formPrice">
                            <Form.Label>Price</Form.Label>
                            <Form.Control type="number" value={price} onChange={(e) => { setPrice(e.target.value); setFormChanged(true); }} min="0" step="0.01" />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeModal}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={editingStock ? handleUpdate : handleCreate} disabled={!isFormComplete()}>
                        {buttonText}
                    </Button>
                </Modal.Footer>
            </Modal>
            <Footer />
        </div>
    );
};

export default StockEdition;