import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Footer from "../../components/footer";

const AddWeight = () => {
    const [weight, setWeight] = useState('');
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", { username, token });
                if (response.data === "User is valid" && userRole === "customer") {
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

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post('/addWeight', {
                weight: parseFloat(weight),
                customer: username
            });

            if (response.status === 200) {
                alert('Weight added successfully!');
            } else {
                alert('Failed adding weight!');
            }
        } catch (error) {
            console.error('Error adding weight:', error);
            alert('Failed adding weight!');
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Weight:
                    <input
                        type="number"
                        step="0.01"
                        value={weight}
                        onChange={(event) => setWeight(event.target.value)}
                        required
                    />
                </label>
                <button type="submit">Add Weight</button>
            </form>
            <Footer />
        </div>
    );
};

export default AddWeight;