import React, { useEffect, useState } from 'react';
import axios from "axios";
import { Button, Table, Modal, Form } from 'react-bootstrap';
import ReactSelect from 'react-select';
import Footer from "../../../components/footer";
import './Board.css'

const SuperAminRecipeEdition = () => {
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
    const [recipeToDelete, setRecipeToDelete] = useState(null);
    //recipes/categories/ingredients
    const [recipes, setRecipes] = useState([]);
    const [ingredients, setIngredientOptions] = useState([]);
    const [categories, setCategoryOptions] = useState([]);
    //Modal
    const [showModal, setShowModal] = useState(false);
    const [modalTitle, setModalTitle] = useState('Create Recipe');
    const [buttonText, setButtonText] = useState('Create');
    //create/update
    const [isPublic, setIsPublic] = useState('');
    const [newRecipeName, setNewRecipeName] = useState('');
    const [newRecipeDescription, setNewRecipeDescription] = useState('');
    const [selectedIngredients, setSelectedIngredients] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [editingRecipe, setEditingRecipe] = useState(null);
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
        const fetchRecipes = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/recipes`);
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

        axios.get('http://localhost:8080/categories')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setCategoryOptions(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
    }, []);

    useEffect(() => {
        if (editingRecipe) {
            setNewRecipeName(editingRecipe.recipeName);
            setNewRecipeDescription(editingRecipe.recipeDescription);
            setSelectedIngredients(editingRecipe.ingredientList);
            setSelectedCategories(editingRecipe.categoryList);
            setIsPublic(editingRecipe.isPublic ? 'public' : 'private');
        }
    }, [editingRecipe]);

    const isFormComplete = () => {
        return newRecipeName
            && newRecipeDescription
            && selectedIngredients.length > 0
            && selectedCategories.length > 0
            && (isPublic === "public" || isPublic === "private");
    };

    const displayMessage = (message) => {
        setMessageContent(message);
        setShowMessage(true);
        const timer = setTimeout(() => {
            setShowMessage(false);
        }, 7000);
        return () => clearTimeout(timer);
    };

    const prepareForEdit = (recipe) => {
        setEditingRecipe(recipe);
        setModalTitle('Edit Recipe');
        setButtonText('Update');
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setModalTitle('Create Recipe');
        setButtonText('Create');
        resetModal();
    };

    const resetModal = () => {
        setNewRecipeName('');
        setNewRecipeDescription('');
        setSelectedIngredients([]);
        setSelectedCategories([]);
        setIsPublic('');
        setEditingRecipe(null);
        setSearchTerm('')
    };

    const openDeleteModal = (ingredient) => {
        setRecipeToDelete(ingredient);
        setShowDeleteModal(true);
    };

    const closeDeleteModal = () => {
        setRecipeToDelete(null);
        setShowDeleteModal(false);
        setSearchTerm('')
    };

    const handleCreate = async () => {
        const recipeData = {
            recipeName: newRecipeName,
            recipeDescription: newRecipeDescription,
            categoryList: selectedCategories,
            ingredientList: selectedIngredients,
            recipeUsername: username,
            isPublic: true
        };

        await axios.post("http://localhost:8080/recipe", recipeData)
            .then(async res => {
                console.log(res);
                // Close the modal
                closeModal();
                // Show success message
                displayMessage('Recipe created successfully');
                // Fetch the updated recipe list
                const response = await axios.get(`http://localhost:8080/recipes`);
                if (Array.isArray(response.data)) {
                    setRecipes(response.data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(err => console.log(err));
    };

    const handleUpdate = async () => {
        if (editingRecipe) {
            const recipeData = {
                recipe: editingRecipe,
                recipeName: newRecipeName,
                recipeDescription: newRecipeDescription,
                categoryList: selectedCategories,
                ingredientList: selectedIngredients,
                recipeUsername: username,
                isPublic: isPublic === 'public'
            };

            await axios.put(`http://localhost:8080/recipe`, recipeData)
                .then(async res => {
                    console.log(res);
                    // Close the modal
                    closeModal();
                    // Show success message
                    displayMessage('Recipe updated successfully');
                    // Fetch the updated recipe list
                    const response = await axios.get(`http://localhost:8080/recipes`);
                    if (Array.isArray(response.data)) {
                        setRecipes(response.data);
                    } else {
                        console.error('Data received from server is not an array');
                    }
                })
                .catch(err => console.log(err));
        }
    };

    const confirmDelete = async () => {
        if (recipeToDelete) {
            const recipeData = {
                recipe: recipeToDelete
            };

            axios.delete('http://localhost:8080/recipe', { data: recipeData})
                .then(res => {
                    console.log(res);
                    setRecipes(recipes.filter(r => r.recipeName !== recipeToDelete.recipeName));
                    displayMessage('Recipe deleted successfully');
                    closeDeleteModal();
                })
                .catch(err => console.log(err));
        }
    };

    let ingredientOptions = ingredients
        .filter(ingredient => !selectedIngredients.some(selectedIngredient => selectedIngredient.ingredientId === ingredient.ingredientId))
        .map(ingredient => ({ value: ingredient, label: ingredient.ingredientName }));

    let categoryOptions = categories
        .filter(category => !selectedCategories.some(selectedCategory => selectedCategory.id === category.id))
        .map(category => ({ value: category, label: category.categoryName }));

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value.trimStart());
    };

    // Filtrar recetas según el término de búsqueda
    const filteredRecipes = recipes.filter(recipe =>
        recipe.recipeName.toLowerCase().includes(searchTerm.toLowerCase().trim())
    );

    return (
        <div className="container my-3">
            <h1 className="text-center">Recipes</h1>
            <Button variant="success" onClick={() => {
                resetModal();
                setIsPublic("public");
                setModalTitle('Create Recipe');
                setButtonText('Create');
                setShowModal(true);
            }} style={{ marginBottom: '20px' }}>
                Create Recipe
            </Button>
            {showMessage && <div className="alert alert-success">{messageContent}</div>}
            <input
                className="form-control"
                type="text"
                placeholder="Search by recipe name..."
                value={searchTerm}
                onChange={handleSearchChange}
                style={{ marginBottom: '20px' }}
            />
            <div className="table-container">
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
                    {filteredRecipes.map((recipe, index) => (
                        <tr key={index}>
                            <td>{recipe.recipeName}</td>
                            <td>{recipe.recipeDescription}</td>
                            <td>{recipe.isPublic ? 'Yes' : 'No'}</td>
                            <td>
                                <Button variant="warning" onClick={() => prepareForEdit(recipe)}
                                        style={{ marginRight: '10px' }}>
                                    Edit
                                </Button>
                                <Button variant="danger" onClick={() => openDeleteModal(recipe)}>
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
                        <Form.Group controlId="formRecipeName">
                            <Form.Label>Recipe Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter recipe name" value={newRecipeName}
                                          onChange={e => {
                                              setNewRecipeName(e.target.value);
                                          }} />
                        </Form.Group>
                        <Form.Group controlId="formRecipeDescription">
                            <Form.Label>Description</Form.Label>
                            <Form.Control type="text" placeholder="Enter description" value={newRecipeDescription}
                                          onChange={e => {
                                              setNewRecipeDescription(e.target.value);
                                          }} />
                        </Form.Group>
                        <Form.Label>Choose Ingredients</Form.Label>
                        <ReactSelect
                            isMulti
                            options={ingredientOptions}
                            value={selectedIngredients.map(ingredient => ({
                                value: ingredient,
                                label: ingredient.ingredientName
                            }))}
                            onChange={selectedOptions => {
                                setSelectedIngredients(selectedOptions.map(option => option.value));
                            }}
                            className="basic-multi-select"
                            classNamePrefix="select"
                        />
                        <Form.Label>Choose Categories</Form.Label>
                        <ReactSelect
                            isMulti
                            options={categoryOptions}
                            value={selectedCategories.map(category => ({
                                value: category,
                                label: category.categoryName
                            }))}
                            onChange={selectedOptions => setSelectedCategories(selectedOptions ? selectedOptions.map(option => option.value) : [])}
                            className="basic-multi-select"
                            classNamePrefix="select"
                        />
                        <Form.Group controlId="formIsPublic">
                            <Form.Label>Visibility</Form.Label>
                            <input
                                type="text"
                                value={editingRecipe ? (editingRecipe.isPublic ? 'public' : 'private') : 'public'}
                                readOnly
                                style={{
                                    display: 'block',
                                    width: '100%',
                                    padding: '.375rem .75rem',
                                    fontSize: '1rem',
                                    lineHeight: '1.5',
                                    color: '#495057',
                                    backgroundColor: '#e9ecef',
                                    border: '1px solid #ced4da',
                                    borderRadius: '.25rem',
                                    transition: 'border-color .15s ease-in-out,box-shadow .15s ease-in-out'
                                }}
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeModal}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={editingRecipe ? handleUpdate : handleCreate}
                            disabled={!isFormComplete()}>
                        {buttonText}
                    </Button>
                </Modal.Footer>
            </Modal>
            <Modal show={showDeleteModal} onHide={closeDeleteModal}>
                <Modal.Header>
                    <Modal.Title>Confirm Deletion</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Do you want to delete the recipe?
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
            <Footer />
        </div>
    );
}

export default SuperAminRecipeEdition;
