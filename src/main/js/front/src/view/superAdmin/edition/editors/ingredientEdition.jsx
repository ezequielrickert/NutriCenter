import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Button, Table, Modal, Form } from 'react-bootstrap';
import Footer from "../../../components/footer";
import './Board.css'

const SuperAdminIngredientEdition = () => {
    //validation
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    //message
    const [showMessage, setShowMessage] = useState(false);
    const [messageContent, setMessageContent] = useState('');
    // Modal de confirmación
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [ingredientToDelete, setIngredientToDelete] = useState(null);
    //categories/ingredients
    const [ingredients, setIngredients] = useState([]);
    const [allergies, setAllergyOptions] = useState([]);
    //Modal
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState('Create Ingredient');
    const [buttonText, setButtonText] = useState('Create');
    //create/update
    const [newIngredientName, setNewIngredientName] = useState('');
    const [newAllergy, setNewAllergy] = useState(null);
    const [newProteins, setNewProteins] = useState('');
    const [newSodium, setNewSodium] = useState('');
    const [newCalories, setNewCalories] = useState('');
    const [newTotalFat, setNewTotalFat] = useState('');
    const [newCholesterol, setNewCholesterol] = useState('');
    const [newTotalCarbohydrate, setNewTotalCarbohydrate] = useState('');
    const [editingIngredient, setEditingIngredient] = useState(null);
    // Búsqueda
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "superAdmin") {
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
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                try {
                    const data = JSON.parse(response.data);
                    if (Array.isArray(data)) {
                        setIngredients(data);
                    } else {
                        console.error('Data received from server is not an array');
                    }
                } catch (error) {
                    console.error('Error parsing ingredients data:', error);
                }
            })
            .catch(error => {
                console.error('Error fetching ingredients:', error);
            });
    }, []);

    useEffect(() => {
        axios.get('http://localhost:8080/allergies')
            .then(response => {
                try {
                    const data = JSON.parse(response.data);
                    if (Array.isArray(data)) {
                        setAllergyOptions(data);
                    } else {
                        console.error('Data received from server is not an array');
                    }
                } catch (error) {
                    console.error('Error parsing allergies data:', error);
                }
            })
            .catch(error => {
                console.error('Error fetching allergies:', error);
            });
    }, []);


    useEffect(() => {
        if (editingIngredient) {
            setNewIngredientName(editingIngredient.ingredientName);
            setNewAllergy(editingIngredient.allergy)
            setNewProteins(editingIngredient.proteins);
            setNewSodium(editingIngredient.sodium);
            setNewCalories(editingIngredient.calories);
            setNewTotalFat(editingIngredient.totalFat);
            setNewCholesterol(editingIngredient.cholesterol);
            setNewTotalCarbohydrate(editingIngredient.totalCarbohydrate);
        }
    }, [editingIngredient]);

    const isFormComplete = () => {
        return newIngredientName
            && newAllergy
            && newProteins
            && newSodium
            && newCalories
            && newTotalFat
            && newCholesterol
            && newTotalCarbohydrate;
    };

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
        const timer = setTimeout(() => {
            setShowMessage(false);
        }, 5000);
        return () => clearTimeout(timer);
    };

    const prepareForEdit = (ingredient) => {
        setEditingIngredient(ingredient);
        setModalTitle('Edit Ingredient');
        setButtonText('Update');
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setModalTitle('Create Ingredient');
        setButtonText('Create');
        resetModal();
    };

    const resetModal = () => {
        setNewIngredientName('');
        setNewAllergy('');
        setNewProteins('');
        setNewSodium('');
        setNewCalories('');
        setNewTotalFat('');
        setNewCholesterol('');
        setNewTotalCarbohydrate('');
        setEditingIngredient(null);
        setSearchTerm('')
    };

    const openDeleteModal = (ingredient) => {
        setIngredientToDelete(ingredient);
        setShowDeleteModal(true);
    };

    const closeDeleteModal = () => {
        setIngredientToDelete(null);
        setShowDeleteModal(false);
        setSearchTerm('')
    };

    const handleCreate = async () => {
        const ingredientData = {
            ingredientName: newIngredientName,
            allergy: newAllergy,
            proteins: newProteins,
            sodium: newSodium,
            calories: newCalories,
            totalFat: newTotalFat,
            cholesterol: newCholesterol,
            totalCarbohydrate: newTotalCarbohydrate
        };

        try {
            const res = await axios.post("http://localhost:8080/createIngredient", ingredientData);
            console.log(res);
            // Close the modal
            closeModal();
            // Show success message
            displayMessage('Ingredient created successfully');
            // Fetch the updated ingredient list
            const response = await axios.get('http://localhost:8080/ingredients');
            const data = JSON.parse(response.data);
            if (Array.isArray(data)) {
                setIngredients(data);
            } else {
                console.error('Data received from server is not an array');
            }
        } catch (err) {
            console.log(err);
            closeModal();
            displayMessage('Ingredient already exist');
        }
    };


    const handleUpdate = async () => {
        if (editingIngredient) {
            const ingredientData = {
                ingredient: editingIngredient,
                ingredientName: newIngredientName,
                allergy: newAllergy,
                proteins: newProteins,
                sodium: newSodium,
                calories: newCalories,
                totalFat: newTotalFat,
                cholesterol: newCholesterol,
                totalCarbohydrate: newTotalCarbohydrate
            };

            try {
                const res = await axios.post(`http://localhost:8080/updateIngredient`, ingredientData);
                console.log(res);
                // Close the modal
                closeModal();
                // Show success message
                displayMessage('Ingredient updated successfully');
                // Fetch the updated ingredient list
                const response = await axios.get('http://localhost:8080/ingredients');
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setIngredients(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (err) {
                console.log(err);
            }
        }
    };

    const confirmDelete = async () => {
        if (ingredientToDelete) {
            const ingredientData = {
                ingredient: ingredientToDelete
            };

            axios.post('http://localhost:8080/deleteIngredient', ingredientData)
                .then(res => {
                    console.log(res);
                    setIngredients(ingredients.filter(r => r.ingredientId !== ingredientToDelete.ingredientId));
                    displayMessage('Ingredient deleted successfully');
                    closeDeleteModal();
                })
                .catch(err => console.log(err));
        }
    };


    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value.trimStart());
    };

    // Filtrar ingredientes según el término de búsqueda
    const filteredIngredients = ingredients.filter(ingredient =>
        ingredient.ingredientName.toLowerCase().includes(searchTerm.toLowerCase().trim())
    );

    return (
        <div className="container my-3">
            <h1 className="text-center">Ingredients</h1>
            <Button variant="success" onClick={() => {
                resetModal();
                setModalTitle('Create Ingredient');
                setButtonText('Create');
                setShowModal(true);
            }} style={{marginBottom: '20px'}}>
                Create Ingredient
            </Button>
            {showMessage && <div className="alert alert-success">{messageContent}</div>}
            <input
                className="form-control"
                type="text"
                placeholder="Search by ingredient name..."
                value={searchTerm}
                onChange={handleSearchChange}
                style={{marginBottom: '20px'}}
            />
            <div className="table-container">
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Ingredient Name</th>
                            <th>Allergy</th>
                            <th>Proteins</th>
                            <th>Sodium</th>
                            <th>Calories</th>
                            <th>Total Fats</th>
                            <th>Cholesterol</th>
                            <th>Total Carbohydrates</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                    {filteredIngredients.map((ingredient, index) => (
                        <tr key={index}>
                            <td>{ingredient.ingredientName}</td>
                            <td>{ingredient.allergy.allergyName}</td>
                            <td>{ingredient.proteins}</td>
                            <td>{ingredient.sodium}</td>
                            <td>{ingredient.calories}</td>
                            <td>{ingredient.totalFat}</td>
                            <td>{ingredient.cholesterol}</td>
                            <td>{ingredient.totalCarbohydrate}</td>
                            <td>
                                <Button variant="warning" onClick={() => prepareForEdit(ingredient)}
                                        style={{marginRight: '10px'}}>
                                    Edit
                                </Button>
                                <Button variant="danger" onClick={() => openDeleteModal(ingredient)}>
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>
            <Modal show={showModal} onHide={closeModal}>
                <Modal.Header>
                    <Modal.Title>{modalTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formIngredientName">
                            <Form.Label>Ingredient Name</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter ingredient name"
                                value={newIngredientName}
                                onChange={e => setNewIngredientName(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formAllergy">
                            <Form.Label>Allergy</Form.Label>
                            <Form.Control
                                as="select"
                                value={newAllergy ? newAllergy.allergyName : ''}
                                onChange={e => setNewAllergy(allergies.find(a => a.allergyName === e.target.value))}
                            >
                                <option value="">Select allergy</option>
                                {allergies.map((allergy, index) => (
                                    <option key={index} value={allergy.allergyName}>
                                        {allergy.allergyName}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group>
                        <Form.Group controlId="formProteins">
                            <Form.Label>Proteins</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter proteins"
                                value={newProteins}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewProteins(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                        <Form.Group controlId="formSodium">
                            <Form.Label>Sodium</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter sodium"
                                value={newSodium}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewSodium(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                        <Form.Group controlId="formCalories">
                            <Form.Label>Calories</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter calories"
                                value={newCalories}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewCalories(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                        <Form.Group controlId="formTotalFat">
                            <Form.Label>Total Fat</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter total fat"
                                value={newTotalFat}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewTotalFat(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                        <Form.Group controlId="formCholesterol">
                            <Form.Label>Cholesterol</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter cholesterol"
                                value={newCholesterol}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewCholesterol(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                        <Form.Group controlId="formTotalCarbohydrate">
                            <Form.Label>Total Carbohydrate</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter total carbohydrate"
                                value={newTotalCarbohydrate}
                                onChange={e => {
                                    const value = Math.max(0, e.target.value);
                                    setNewTotalCarbohydrate(value);
                                }}
                                min="0"
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeModal}>
                        Close
                    </Button>
                    <Button
                        variant="primary"
                        onClick={editingIngredient ? handleUpdate : handleCreate}
                        disabled={!isFormComplete()}
                    >
                        {buttonText}
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showDeleteModal} onHide={closeDeleteModal}>
                <Modal.Header>
                    <Modal.Title>Confirm Deletion</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Do you want to delete the ingredient?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeDeleteModal}>
                        No
                    </Button>
                    <Button variant="danger" onClick={confirmDelete}>
                        Yes
                    </Button>
                </Modal.Footer>
            </Modal>
            <Footer/>
        </div>
    );
}

export default SuperAdminIngredientEdition;
