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

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid") {
                    setIsValidUser(true);
                } else {
                    window.location.href = '/loginNutritionist';
                }
            } catch (error) {
                console.error("Error validating user", error);
                window.location.href = '/loginNutritionist';
            }
        };

        validateUser();
    }, [token, username]);

    useEffect(() => {
        if (!isValidUser) {
            return;
        }
        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setIngredients(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, [isValidUser]);

    useEffect(() => {
        if (!isValidUser) {
            return;
        }
        axios.get('http://localhost:8080/categories')
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setCategories(data);
                } else {
                    console.error('Data received from server is not an array');
                }

            })
    }, [isValidUser]);

    useEffect(() => {
        if (!isValidUser) {
            return;
        }

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
        <div className="App">
            <header className="App-header">
                <h1>Update Recipe</h1>
                <form onSubmit={handleSubmit}>

                    <ReactSelect
                        options={recipes}
                        onChange={handleRecipeSelect}
                        styles={{
                            control: (base) => ({
                                ...base,
                                width: '500px' // Adjust this value as needed
                            }),
                            multiValue: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            multiValueLabel: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            option: (base) => ({
                                ...base,
                                color: 'black',
                                fontSize: 'small'
                            })
                        }
                        }
                    />

                    <label htmlFor="ingredientName">Recipe Name:</label><br/>
                    <input type="text" id="ingredientName" name="ingredientName" value={name}
                           onChange={e => setName(e.target.value)}/><br/>

                    <label htmlFor="description">Description:</label><br/>
                    <input type="text" id="description" name="description" value={description}
                           onChange={e => setDescription(e.target.value)}/>
                    <br/>
                    <h2>Choose categories:</h2>
                    <ReactSelect
                        isMulti
                        options={categoryOptions}
                        value={selectedCategories.map(category => ({value: category, label: category.categoryName}))}
                        onChange={selectedOptions => setSelectedCategories(selectedOptions.map(option => option.value))}
                        styles={{
                            control: (base) => ({
                                ...base,
                                width: '500px' // Adjust this value as needed
                            }),
                            multiValue: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            multiValueLabel: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            option: (base) => ({
                                ...base,
                                color: 'black',
                                fontSize: 'small'
                            })
                        }}
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
                        styles={{
                            control: (base) => ({
                                ...base,
                                width: '500px' // Adjust this value as needed
                            }),
                            multiValue: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            multiValueLabel: (base) => ({
                                ...base,
                                backgroundColor: 'white',
                                color: 'black',
                                fontSize: 'small'
                            }),
                            option: (base) => ({
                                ...base,
                                color: 'black',
                                fontSize: 'small'
                            })
                        }}
                    />
                    <label htmlFor="isPublic">Visibility:</label><br/>
                    <select id="isPublic" name="isPublic" value={isPublic}
                            onChange={e => setIsPublic(e.target.value === 'true')}>
                        <option value={true}>Public</option>
                        <option value={false}>Private</option>
                    </select><br/>
                    <input type="submit" disabled={!isFormValid}/>
                </form>
            </header>
        </div>
    );
}


export default NutritionistUpdateRecipe;