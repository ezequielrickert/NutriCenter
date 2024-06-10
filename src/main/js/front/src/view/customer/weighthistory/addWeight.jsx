import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AddWeight = () => {
    const [weight, setWeight] = useState('');
    const [isValidUser, setIsValidUser] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
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

        // Check if weight is greater than 0
        if (parseFloat(weight) <= 0) {
            toast.error('Weight must be greater than 0!');
            return;
        }

        setIsLoading(true);

        try {
            const response = await axios.post('/addWeight', {
                weight: parseFloat(weight),
                customer: username
            });

            if (response.status === 200) {
                toast.success('Weight added successfully!');
            } else {
                toast.error('Failed adding weight!');
            }
        } catch (error) {
            console.error('Error adding weight:', error);
            toast.error('Failed adding weight!');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit} className="form-group">
                <label>
                    Weight:
                    <input
                        type="number"
                        step="0.01"
                        value={weight}
                        onChange={(event) => setWeight(event.target.value)}
                        required
                        className="form-control"
                    />
                </label>
                <button type="submit" className="btn btn-primary" disabled={isLoading}>
                    {isLoading ? 'Loading...' : 'Add Weight'}
                </button>
            </form>
            <Footer />
            <ToastContainer />
        </div>
    );
};

export default AddWeight;