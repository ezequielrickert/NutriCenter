import React, { useEffect, useState } from "react";
import axios from 'axios';

const UpdateIngredient = () => {
    const [name, setName] = useState('');
    const [allergy, setAllergy] = useState(null);
    const [allergies, setAllergies] = useState([])
    const [proteins, setProteins] = useState('');
    const [sodium, setSodium] = useState('')
    const [calories, setCalories] = useState('');
    const [totalFat, setTotalFat] = useState('')
    const [cholesterol, setCholesterol] = useState('');
    const [totalCarbohydrate, setTotalCarbohydrate] = useState('')
    const [ingredient, setIngredient] = useState(null);
    const [ingredients, setIngredients] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/allergies')
            .then(response => {
                setAllergies(response.data);
            })
            .catch(error => {
                console.error('There was an error!', error);
            });

        axios.get('http://localhost:8080/ingredients')
            .then(response => {
                setIngredients(response.data);

                // Set the first ingredient as default
                if (response.data.length > 0) {
                    setIngredient(response.data[0]);
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, []);

    useEffect(() => {
        // Set ingredient values when ingredient changes
        if (ingredient) {
            setName(ingredient.name);
            setProteins(ingredient.proteins);
            setSodium(ingredient.sodium);
            setCalories(ingredient.calories);
            setTotalFat(ingredient.totalFat);
            setCholesterol(ingredient.cholesterol);
            setTotalCarbohydrate(ingredient.totalCarbohydrate);
        }
    }, [ingredient]);

    const handleSubmit = async (event) => {
        event.preventDefault()

        if (window.confirm('Are you sure you want to delete this ingredient?')) {

            const ingredientData = {
                name: ingredient.ingredientName,
                allergy: allergy,
                proteins: proteins,
                sodium: sodium,
                calories: calories,
                totalFat: totalFat,
                cholesterol: cholesterol,
                totalCarbohydrate: totalCarbohydrate
            }

            await axios.post("http://localhost:8080/updateIngredient", ingredientData)
                .then(res => console.log(res))
                .catch(err => console.log(err))
        }
    }

    const isFormValid = name && allergy && proteins && sodium &&
        calories && totalFat && cholesterol && totalCarbohydrate;

    return (
        <div className="App">
            <header className="App-header">
                <h1>Create Ingredient</h1>
                <form onSubmit={handleSubmit}>

                    <label htmlFor="ingredient">Ingredient:</label><br />
                    <select id="ingredient" value={ingredient ? ingredient.ingredientName : ''}
                            onChange={e => setIngredient(ingredients.find(a => a.ingredientName === e.target.value))}
                            style={{ width: '200px' }}>
                        <option value="">Select an ingredient</option>
                        {ingredients.map((ingredient, index) => (
                            <option key={index} value={ingredient.ingredientName}>{ingredient.ingredientName}</option>
                        ))}
                    </select><br />

                    <label htmlFor="allergy">Allergy:</label><br/>
                    <select id="allergy" value={allergy ? allergy.allergyName : ''}
                            onChange={e => setAllergy(allergies.find(a => a.allergyName === e.target.value))}
                            style={{width: '200px'}}>
                        <option value="">Select an allergy</option>
                        {allergies.map((allergy, index) => (
                            <option key={index} value={allergy.allergyName}>{allergy.allergyName}</option>
                        ))}
                    </select><br/>

                    <label htmlFor="proteins">Proteins:</label><br/>
                    <input type="number" id="proteins" name="proteins" value={proteins}
                           onChange={e => setProteins(e.target.value)}/><br/>

                    <label htmlFor="sodium">Sodium:</label><br/>
                    <input type="number" id="sodium" name="sodium" value={sodium}
                           onChange={e => setSodium(e.target.value)}/><br/>

                    <label htmlFor="calories">Calories:</label><br/>
                    <input type="number" id="calories" name="calories" value={calories}
                           onChange={e => setCalories(e.target.value)}/><br/>

                    <label htmlFor="totalFat">Total Fat:</label><br/>
                    <input type="text" id="totalFat" name="totalFat" value={totalFat}
                           onChange={e => setTotalFat(e.target.value)}/><br/>

                    <label htmlFor="cholesterol">Cholesterol:</label><br/>
                    <input type="number" id="cholesterol" name="cholesterol" value={cholesterol}
                           onChange={e => setCholesterol(e.target.value)}/><br/>

                    <label htmlFor="totalCarbohydrate">Total Carbohydrate:</label><br/>
                    <input type="numebr" id="totalCarbohydrate" name="totalCarbohydrate" value={totalCarbohydrate}
                           onChange={e => setTotalCarbohydrate(e.target.value)}/><br/>

                    <input type="submit" disabled={!isFormValid}/>
                </form>
            </header>
        </div>
    );
}

export default UpdateIngredient;