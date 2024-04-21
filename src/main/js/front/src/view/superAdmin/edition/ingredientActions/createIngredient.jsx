import React, {useEffect, useState} from "react";
import axios from 'axios';

const CreateIngredient = () => {

    const [name, setName] = useState('');
    const [allergy, setAllergy] = useState(null);
    const [allergies, setAllergies] = useState([])
    const [proteins, setProteins] = useState('');
    const [sodium, setSodium] = useState('')
    const [calories, setCalories] = useState('');
    const [totalFat, setTotalFat] = useState('')
    const [cholesterol, setCholesterol] = useState('');
    const [totalCarbohydrate, setTotalCarbohydrate] = useState('')

    useEffect(() => {
        axios.get('http://localhost:8080/allergies')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setAllergies(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault()

        const ingredientData = {
            ingredientName: name,
            allergy: allergy,
            proteins: proteins,
            sodium: sodium,
            calories: calories,
            totalFat: totalFat,
            cholesterol: cholesterol,
            totalCarbohydrate: totalCarbohydrate
        }

        await axios.post("http://localhost:8080/createIngredient",  ingredientData )
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    const isFormValid = name && allergy && proteins && sodium &&
                                    calories && totalFat && cholesterol && totalCarbohydrate;

    // dejar como allergy.allergyName aunque esté subrayado, así funciona

    return (
        <div className="App">
            <header className="App-header">
                <h1>Create Ingredient</h1>
                <form onSubmit={handleSubmit}>

                    <label htmlFor="ingredientName">Ingredient Name:</label><br/>
                    <input type="text" id="ingredientName" name="ingredientName" value={name}
                           onChange={e => setName(e.target.value)}/><br/>

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
                           onChange={e => setProteins(e.target.value)} min="0"/><br/>

                    <label htmlFor="sodium">Sodium:</label><br/>
                    <input type="number" id="sodium" name="sodium" value={sodium}
                           onChange={e => setSodium(e.target.value)} min="0"/><br/>

                    <label htmlFor="calories">Calories:</label><br/>
                    <input type="number" id="calories" name="calories" value={calories}
                           onChange={e => setCalories(e.target.value)} min="0"/><br/>

                    <label htmlFor="totalFat">Total Fat:</label><br/>
                    <input type="number" id="totalFat" name="totalFat" value={totalFat}
                           onChange={e => setTotalFat(e.target.value)} min="0"/><br/>

                    <label htmlFor="cholesterol">Cholesterol:</label><br/>
                    <input type="number" id="cholesterol" name="cholesterol" value={cholesterol}
                           onChange={e => setCholesterol(e.target.value)} min="0"/><br/>

                    <label htmlFor="totalCarbohydrate">Total Carbohydrate:</label><br/>
                    <input type="number" id="totalCarbohydrate" name="totalCarbohydrate" value={totalCarbohydrate}
                           onChange={e => setTotalCarbohydrate(e.target.value)} min="0"/><br/>

                    <input type="submit" disabled={!isFormValid}/>
                </form>
            </header>
        </div>
    );
}

export default CreateIngredient;