import React, {useState, useEffect} from "react";
import axios from 'axios';

const CreateStock =  () => {
    const [ingredients, setIngredients] = useState([]);
    const [ingredient, setIngredient] = useState();
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [quantity, setQuantity] = useState(0)
    const [brand, setBrand] = useState('')

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
        if (!isValidUser) {
            return;
        }

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
    }, [isValidUser]);

    const handleSubmit = async (event) => {
        event.preventDefault()

        const stockData = {
            storeName: username,
            ingredientId: ingredient,
            quantity: quantity,
            brand: brand
        }
        try {
            const response = await axios.post('http://localhost:8080/addStock', stockData);
            if (response.data === "Stock created successfully") {
                alert("Stock created successfully");
            } else {
                alert("Error creating stock");
            }
        } catch (error) {
            console.error("Error creating stock", error);
        }
    }


    return (
        <div>
            <h1>Stock</h1>
            <form onSubmit={handleSubmit}>
                <h2>Ingredient:</h2>
                <label>
                    <select id="ingredient" value={ingredient ? ingredient.ingredientName : ''}
                            onChange={e => {
                                const selectedIngredient = ingredients.find(a => a.ingredientName === e.target.value);
                                setIngredient(selectedIngredient);
                            }}
                            style={{width: '200px'}}>
                        <option value={null}>Select an ingredient</option>
                        {ingredients.map((ingredient) => (
                            <option key={ingredient.ingredientName}
                                    value={ingredient.ingredientName}>{ingredient.ingredientName}</option>
                        ))}
                    </select>
                </label><br/>
                <h2>Quantity:</h2>
                <label>
                    <input type="number" value={quantity} onChange={(e) => setQuantity(e.target.value)} min="0"/>
                </label>
                <br/>
                <h2>Brand:</h2>
                <label>
                    <input type="text" value={brand} onChange={(e) => setBrand(e.target.value)}/>
                </label>
                <br/>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default CreateStock;