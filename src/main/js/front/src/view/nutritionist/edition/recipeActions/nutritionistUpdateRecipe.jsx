import React, {useEffect, useState} from "react";
import axios from 'axios';
import ReactSelect from 'react-select';

const NutritionistUpdateRecipe = () => {

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [categories, setCategories] = useState([])
    const [ingredients, setIngredients] = useState([])
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [selectedIngredients, setSelectedIngredients] = useState([]);
    const [selectedRecipe, setSelectedRecipe] = useState(null);
    const [recipes, setRecipes] = useState([]);
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const [isPublic, setIsPublic] = useState(true);
    const userRole = localStorage.getItem('role');

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

        axios.get(`http://localhost:8080/recipes/${username}`)
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setRecipes(data.map(recipe => ({ value: recipe, label: recipe.recipeName })));
                } else {
                    console.error('Data received from server is not an array');
                }

            })
    }, [isValidUser]);

    useEffect(() => {
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setIngredients(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, []);

    useEffect(() => {
        axios.get('http://localhost:8080/categories')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setCategories(data);
                } else {
                    console.error('Data received from server is not an array');
                }

            })
    }, []);

    const handleRecipeSelect = (selectedOption) => {
        setSelectedRecipe(selectedOption.value);
        setName(selectedOption.value.recipeName);
        setDescription(selectedOption.value.recipeDescription);
        setSelectedCategories(selectedOption.value.categoryList);
        setSelectedIngredients(selectedOption.value.ingredientList);
        setIsPublic(selectedOption.value.isPublic);
    };

    const handleSubmit = async (event) => {
        event.preventDefault()

        const ingredientData = {
            recipe: selectedRecipe,
            recipeName: name,
            recipeDescription: description,
            categoryList: selectedCategories,
            ingredientList: selectedIngredients,
            username: username,
            isPublic: isPublic
        }
        await axios.post("http://localhost:8080/updateRecipe",  ingredientData )
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    const isFormValid = name && description && selectedCategories.length != 0 && selectedIngredients.length != 0;

    let ingredientOptions = ingredients.map(ingredient => ({ value: ingredient, label: ingredient.ingredientName }));
    let categoryOptions = categories.map(category => ({ value: category, label: category.categoryName }));

    return (
        <div className="container my-5">
            <header className="text-center">
                <h1>Update Recipe</h1>
                <form onSubmit={handleSubmit} className="mt-4">

                    <ReactSelect
                        options={recipes}
                        onChange={handleRecipeSelect}
                        className="basic-single"
                        classNamePrefix="select"
                    />

                    {selectedRecipe && (
                        <>
                            <label htmlFor="ingredientName">Recipe Name:</label><br/>
                            <input type="text" id="ingredientName" name="ingredientName" value={name}
                                   onChange={e => setName(e.target.value)} className="form-control"/><br/>

                            <label htmlFor="description">Description:</label><br/>
                            <input type="text" id="description" name="description" value={description}
                                   onChange={e => setDescription(e.target.value)} className="form-control"/>
                            <br/>
                            <h2>Choose categories:</h2>
                            <ReactSelect
                                isMulti
                                options={categoryOptions}
                                value={selectedCategories.map(category => ({value: category, label: category.categoryName}))}
                                onChange={selectedOptions => setSelectedCategories(selectedOptions.map(option => option.value))}
                                className="basic-multi-select"
                                classNamePrefix="select"
                            />

                            <h2>Choose ingredients:</h2>
                            <ReactSelect
                                isMulti
                                options={ingredientOptions}
                                value={selectedIngredients.map(ingredient => ({
                                    value: ingredient,
                                    label: ingredient.ingredientName
                                }))}
                                onChange={selectedOptions => setSelectedIngredients(selectedOptions.map(option => option.value))}
                                className="basic-multi-select"
                                classNamePrefix="select"
                            />
                            <label htmlFor="isPublic">Visibility:</label><br/>
                            <select id="isPublic" name="isPublic" value={isPublic}
                                    onChange={e => setIsPublic(e.target.value === 'true')} className="form-control">
                                <option value={true}>Public</option>
                                <option value={false}>Private</option>
                            </select><br/>
                            <input type="submit" disabled={!isFormValid} className="btn btn-primary mt-3"/>
                        </>
                    )}
                </form>
            </header>
        </div>
    );
}


export default NutritionistUpdateRecipe;