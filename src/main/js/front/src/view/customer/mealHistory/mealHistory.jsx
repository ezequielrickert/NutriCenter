import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Footer from "../../components/footer";
import MonthlyHistory from './monthlyHistory'; // Import the MonthlyHistory component


const MealHistory = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [monday, setMonday] = useState(null);
    const [tuesday, setTuesday] = useState(null);
    const [wednesday, setWednesday] = useState(null);
    const [thursday, setThursday] = useState(null);
    const [friday, setFriday] = useState(null);
    const [saturday, setSaturday] = useState(null);
    const [sunday, setSunday] = useState(null);

    useEffect(() => {
        console.log(monday);
    }, [monday]);

    useEffect(() => {
        console.log(tuesday);
    }, [tuesday]);

    useEffect(() => {
        console.log(wednesday);
    }, [wednesday]);

    useEffect(() => {
        console.log(thursday);
    }, [thursday]);

    useEffect(() => {
        console.log(friday);
    }, [friday]);

    useEffect(() => {
        console.log(saturday);
    }, [saturday]);

    useEffect(() => {
        console.log(sunday);
    }, [sunday]);


    const processDays = (days) => {
        setMonday(days.find(day => day.dayName === 'MONDAY')); // Monday
        setTuesday(days.find(day => day.dayName === 'TUESDAY')); // Tuesday
        setWednesday(days.find(day => day.dayName === 'WEDNESDAY')); // Wednesday
        setThursday(days.find(day => day.dayName === 'THURSDAY')); // Thursday
        setFriday(days.find(day => day.dayName === 'FRIDAY')); // Friday
        setSaturday(days.find(day => day.dayName === 'SATURDAY')); // Saturday
        setSunday(days.find(day => day.dayName === 'SUNDAY')); // Sunday
    }

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axios.post("http://localhost:8080/validateUser", {username, token});
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



        const fetchCustomerDays = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/getDays/${username}`);
                console.log('Type of response.data:', typeof response.data);
                let days = JSON.parse(response.data); // Parse the JSON string into an object


                console.log('Initial value of days:', days);
                // Check if response is not empty and is a valid JSON string
                if (days && typeof days === 'object' && !Array.isArray(days)) {
                    days = [days];
                }
                console.log('Value of days after checking if it\'s an object and not an array:', days);
                //checks if array
                if (Array.isArray(days)) {
                    console.log('Value of days before passing to processDays:', days);
                    processDays(days);
                } else {
                    console.error("Error: Expected 'days' to be an array but received:", days);
                }
                console.log('Final value of days:', days);
            } catch (error) {
                console.error("Error fetching customer", error);
            }
        };

        validateUser();
        fetchCustomerDays();
    }, [token, username]);

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    return (
        <div className="d-flex justify-content-center">
            <MonthlyHistory /> {/* Include the MonthlyHistory component */}
            <table className="table table-striped table-hover">
                <thead className="table-dark">
                <tr>
                    <th scope="col">Meal</th>
                    <th scope="col">Monday</th>
                    <th scope="col">Tuesday</th>
                    <th scope="col">Wednesday</th>
                    <th scope="col">Thursday</th>
                    <th scope="col">Friday</th>
                    <th scope="col">Saturday</th>
                    <th scope="col">Sunday</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">Breakfast</th>
                    <td>{monday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{tuesday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{wednesday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{thursday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{friday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{saturday?.breakfast?.recipeName || "No meal"}</td>
                    <td>{sunday?.breakfast?.recipeName || "No meal"}</td>
                </tr>
                <tr>
                    <th scope="row">Lunch</th>
                    <td>{monday?.lunch?.recipeName || "No meal"}</td>
                    <td>{tuesday?.lunch?.recipeName || "No meal"}</td>
                    <td>{wednesday?.lunch?.recipeName || "No meal"}</td>
                    <td>{thursday?.lunch?.recipeName || "No meal"}</td>
                    <td>{friday?.lunch?.recipeName || "No meal"}</td>
                    <td>{saturday?.lunch?.recipeName || "No meal"}</td>
                    <td>{sunday?.lunch?.recipeName || "No meal"}</td>
                </tr>
                <tr>
                    <th scope="row">Dinner</th>
                    <td>{monday?.dinner?.recipeName || "No meal"}</td>
                    <td>{tuesday?.dinner?.recipeName || "No meal"}</td>
                    <td>{wednesday?.dinner?.recipeName || "No meal"}</td>
                    <td>{thursday?.dinner?.recipeName || "No meal"}</td>
                    <td>{friday?.dinner?.recipeName || "No meal"}</td>
                    <td>{saturday?.dinner?.recipeName || "No meal"}</td>
                    <td>{sunday?.dinner?.recipeName || "No meal"}</td>
                </tr>
                </tbody>
            </table>
            <Footer />
        </div>
    );
}

export default MealHistory;