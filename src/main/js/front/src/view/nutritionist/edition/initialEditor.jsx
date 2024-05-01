import React, {useEffect, useState} from 'react';
import axios from "axios";
import { Button, Table, Modal, Form } from 'react-bootstrap';
import Footer from '../../components/footer';

const NutritionistRecipeEditor = () => {
    const [operation, setOperation] = useState('');
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const [isPublic, setIsPublic] = useState(true);
    const userRole = localStorage.getItem('role');
    const [recipes, setRecipes] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [newRecipeName, setNewRecipeName] = useState('');
    const [newRecipeDescription, setNewRecipeDescription] = useState('');
    const [showMessage, setShowMessage] = useState(false);
    const [messageContent, setMessageContent] = useState('');

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
        const fetchRecipes = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/recipes/${username}`);
                if (Array.isArray(response.data)) {
                    setRecipes(response.data);
                } else {
                    console.error('Data received from server is not an array');
                }
            } catch (error) {
                console.error('There was an error!', error);
            }
        };

        if (isValidUser) {
            fetchRecipes();
        }
    }, [isValidUser, username]);

    const handleSelectChange = (event) => {
        setOperation(event.target.value);
    };

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
    };

    const handleCreate = async () => {
        alert("Not implemented yet");
    };

    const handleUpdate = async (recipe) => {
        // Logic for updating a recipe
        const ingredientData = {
            recipe: recipe,
            recipeName: recipe.name,
            recipeDescription: recipe.description,
            categoryList: recipe.categories,
            ingredientList: recipe.ingredients,
            username: username,
            isPublic: isPublic
        }
        await axios.post("http://localhost:8080/updateRecipe",  ingredientData )
            .then(res => console.log(res))
            .catch(err => console.log(err))
    };

    const handleDelete = async (recipe) => {
        // Logic for deleting a recipe
        const recipeData = {
            recipe: recipe
        }
        // Display a confirmation popup
        if (window.confirm('Are you sure you want to delete this recipe?')) {
            axios.post('http://localhost:8080/deleteRecipe', recipeData)
                .then(res => {
                    console.log(res);
                    setRecipes(recipes.filter(r => r.recipeId !== recipe.recipeId));
                    displayMessage('Recipe deleted successfully');
                })
                .catch(err => console.log(err))
        }
    };

    useEffect(() => {
        if (showMessage) {
            const timer = setTimeout(() => {
                setShowMessage(false);
            }, 5000);
            return () => clearTimeout(timer);
        }
    }, [showMessage]);

    return (
        <div className="container my-3">
            <h1 className="text-center">Recipes</h1>
            <Button variant="success" onClick={() => setShowModal(true)} style={{ marginBottom: '20px' }}>Create Recipe</Button>
            {showMessage && <div className="alert alert-success">{messageContent}</div>}
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Recipe Name</th>
                    <th>Description</th>
                    <th>Is Public</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {recipes.map((recipe, index) => (
                    <tr key={index}>
                        <td>{recipe.recipeName}</td>
                        <td>{recipe.recipeDescription}</td>
                        <td>{recipe.isPublic ? 'Yes' : 'No'}</td>
                        <td>
                            <Button variant="warning" onClick={() => handleUpdate(recipe)}
                                    style={{marginRight: '10px'}}>Edit</Button>
                            <Button variant="danger" onClick={() => handleDelete(recipe)}>Delete</Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Create Recipe</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formRecipeName">
                        <Form.Label>Recipe Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter recipe name" value={newRecipeName} onChange={e => setNewRecipeName(e.target.value)} />
                        </Form.Group>
                        <Form.Group controlId="formRecipeDescription">
                            <Form.Label>Description</Form.Label>
                            <Form.Control type="text" placeholder="Enter description" value={newRecipeDescription} onChange={e => setNewRecipeDescription(e.target.value)} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleCreate}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
            <Footer/>
        </div>
    );
}

export default NutritionistRecipeEditor;