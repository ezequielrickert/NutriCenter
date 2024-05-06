import React, {useState, useEffect} from "react";
import axios from 'axios';

const DeleteStock =  () => {
    const [ingredients, setIngredients] = useState([]);
    const [ingredient, setIngredient] = useState();
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [quantity, setQuantity] = useState(0)
    const [brand, setBrand] = useState('')
    const [stock, setStock] = useState([])

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", {username, token});
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
        if (!isValidUser) {
            return;
        }

        axios.get(`http://localhost:8080/stock/${username}`)
            .then(response => {
                const data = response.data;
                if (Array.isArray(data)) {
                    setStock(data);

                    // Get the ingredient list from the stock
                    const ingredientList = data.map(stockItem => stockItem.ingredient);
                    setIngredients(ingredientList);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, [isValidUser]);


    const handleSubmit = async (event) => {
        event.preventDefault();

        const stockData = {
            storeName: username,
            ingredientId: ingredient
        }

        try {
            const response = await axios.post('http://localhost:8080/deleteStock', stockData);
            if (response.data === "Stock deleted successfully") {
                alert("Stock deleted successfully");
            } else {
                alert("Error deleting stock");
            }
        } catch (error) {
            console.error("Error deleting stock", error);
        }
    }

    return (
        <div>
            <h1>Stock</h1>
            <form onSubmit={handleSubmit}>
                <h2>Ingredient:</h2>
                <label>
                    <select id="ingredient" value={ingredient ? ingredient : ''}
                            onChange={e => setIngredient(ingredients.find(a => a.ingredientName === e.target.value))}
                            style={{width: '200px'}}>
                        <option value={null}>Select an ingredient</option>
                        {ingredients.map((ingredient) => (
                            <option key={ingredient}
                                    value={ingredient.ingredientName}>{ingredient.ingredientName}</option>
                        ))}
                    </select>
                </label>
                <br/>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default DeleteStock;