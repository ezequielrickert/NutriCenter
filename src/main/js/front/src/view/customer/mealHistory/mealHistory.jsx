import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MealHistory = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const [days, setDays] = useState([]); // New state variable for the days
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

    const processDays = (days) => {
        setMonday(days.find(day => day.dayName === 'MONDAY'));
        setTuesday(days.find(day => day.dayName === 'TUESDAY'));
        setWednesday(days.find(day => day.dayName === 'WEDNESDAY'));
        setThursday(days.find(day => day.dayName === 'THURSDAY'));
        setFriday(days.find(day => day.dayName === 'FRIDAY'));
        setSaturday(days.find(day => day.dayName === 'SATURDAY'));
        setSunday(days.find(day => day.dayName === 'SUNDAY'));
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
                const days = response.data;
                processDays(days);

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
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                </tr>
                <tr>
                    <th scope="row">Lunch</th>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                </tr>
                <tr>
                    <th scope="row">Dinner</th>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                    <td>"No meal"</td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}

export default MealHistory;