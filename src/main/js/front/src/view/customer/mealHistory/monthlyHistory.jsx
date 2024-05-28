import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto'

const MonthlyHistory = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');
    const [selectedDate, setSelectedDate] = useState('2022-12-31'); // Initialize with a default date
    const [isSubmitted, setIsSubmitted] = useState(false);

    const processDays = (days) => {

    }

    const handleDateChange = (date) => {
        setSelectedDate(date);
    }

    const fetchCustomerDays = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getDaysByDate/${username}/${selectedDate}`);
            console.log('Type of response.data:', typeof response.data);
            let days = JSON.parse(response.data); // Parse the JSON string into an object

            // Check if response is not empty and is a valid JSON string
            if (days && typeof days === 'object' && !Array.isArray(days)) {
                days = [days];
            }

            //checks if array
            if (Array.isArray(days)) {
                processDays(days);
            } else {
                console.error("Error: Expected 'days' to be an array but received:", days);
            }

            if (chartInstance.current) {
                chartInstance.current.destroy()
            }

            if (chartRef.current) { // Check if the canvas element is available
                const myChartRef = chartRef.current.getContext('2d');

                chartInstance.current = new Chart(myChartRef, {
                    type: "pie",
                    data: {
                        datasets: [
                            {
                                label: 'Sample Data',
                                data: [300, 50, 100],
                                backgroundColor: [
                                    "rgb(255, 99, 132)",
                                    "rgb(54, 162, 235)",
                                    "rgb(255, 205, 86)",
                                ],
                            }
                        ]
                    }
                })
            }

        } catch (error) {
            console.error("Error fetching customer", error);
        }
    };

    const handleSubmit = () => {
        setIsSubmitted(true);
    }

    const chartRef = useRef(null);
    const chartInstance = useRef(null);

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

        validateUser();

        if (isSubmitted) {
            fetchCustomerDays();
            setIsSubmitted(false);
        }

        return () => {
            if(chartInstance.current){
                chartInstance.current.destroy()
            }
        }


    }, [token, username, isSubmitted]);

    // if necesario!! para que React no devuelva la pagina al no estar validado
    if (!isValidUser) {
        return null;
    }

    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
    const minDate = oneYearAgo.toISOString().split('T')[0];
    const currentDate = new Date();
    const maxDate = currentDate.toISOString().split('T')[0];


    return (
        <div>
            <input type="date" min={minDate} max={maxDate} onChange={(event) => handleDateChange(event.target.value)}/>
            <button onClick={handleSubmit}>Submit</button>
            <canvas  ref={chartRef} style={{width: "300px", height: "200px" }}/>
        </div>
    );
}

export default MonthlyHistory;