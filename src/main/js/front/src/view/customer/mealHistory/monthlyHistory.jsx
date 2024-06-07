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
    const [calories, setCalories] = useState(0);
    const [cholesterol, setCholesterol] = useState(0);
    const [proteins, setProteins] = useState(0);
    const [sodium, setSodium] = useState(0);
    const [totalCarbohydrates, setTotalCarbohydrates] = useState(0);
    const [totalFats, setTotalFats] = useState(0);


    const processDays = (days) => {
        days.forEach(day => {
            if (day.breakfast && day.breakfast.ingredientList) {
                day.breakfast.ingredientList.forEach(ingredient => {
                    setCalories(prevCalories => prevCalories + ingredient.calories);
                    setCholesterol(prevCholesterol => prevCholesterol + ingredient.cholesterol);
                    setProteins(prevProteins => prevProteins + ingredient.proteins);
                    setSodium(prevSodium => prevSodium + ingredient.sodium);
                    setTotalCarbohydrates(prevCarbs => prevCarbs + ingredient.totalCarbohydrate);
                    setTotalFats(prevFats => prevFats + ingredient.totalFat);
                });
            }
            if (day.lunch && day.lunch.ingredientList) {
                day.lunch.ingredientList.forEach(ingredient => {
                    setCalories(prevCalories => prevCalories + ingredient.calories);
                    setCholesterol(prevCholesterol => prevCholesterol + ingredient.cholesterol);
                    setProteins(prevProteins => prevProteins + ingredient.proteins);
                    setSodium(prevSodium => prevSodium + ingredient.sodium);
                    setTotalCarbohydrates(prevCarbs => prevCarbs + ingredient.totalCarbohydrate);
                    setTotalFats(prevFats => prevFats + ingredient.totalFat);
                });
            }
            if (day.dinner && day.dinner.ingredientList) {
                day.dinner.ingredientList.forEach(ingredient => {
                    setCalories(prevCalories => prevCalories + ingredient.calories);
                    setCholesterol(prevCholesterol => prevCholesterol + ingredient.cholesterol);
                    setProteins(prevProteins => prevProteins + ingredient.proteins);
                    setSodium(prevSodium => prevSodium + ingredient.sodium);
                    setTotalCarbohydrates(prevCarbs => prevCarbs + ingredient.totalCarbohydrate);
                    setTotalFats(prevFats => prevFats + ingredient.totalFat);
                });
            }
        });
        console.log('Calories:', calories)
        console.log('Cholesterol:', cholesterol)
        console.log('Proteins:', proteins)
        console.log('Sodium:', sodium)
        console.log('Total Carbohydrates:', totalCarbohydrates)
        console.log('Total Fats:', totalFats)
    }

    const handleDateChange = (date) => {
        setSelectedDate(date);
    }

    const fetchCustomerDays = async () => {
        try {
            resetValues();
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
                        labels: ['Calories', 'Cholesterol', 'Proteins', 'Sodium', 'Total Carbohydrates', 'Total Fats'],
                        datasets: [
                            {
                                label: 'Value',
                                data: [calories, cholesterol, proteins, sodium, totalCarbohydrates, totalFats],
                                backgroundColor: [
                                    "rgb(255, 99, 132)",
                                    "rgb(54, 162, 235)",
                                    "rgb(255, 205, 86)",
                                    "rgb(75, 192, 192)",
                                    "rgb(153, 102, 255)",
                                    "rgb(255, 159, 64)"
                                ],
                            }
                        ]
                    },
                    options: {
                        aspectRatio: 1.23, // Increase this value to make the pie chart smaller
                        legend: {
                            display: true,
                            position: 'right',
                            align: 'center',
                            fullWidth: true,
                            reverse: false,
                            labels: {
                                fontSize: 90, // Increase this value to make the legend text bigger
                                boxWidth: 50,
                                fontStyle: 'bold',
                                fontColor: 'black',
                                fontFamily: 'Arial',
                                usePointStyle: true,
                                padding: 20,
                            }
                        }
                    }
                })
            }

        } catch (error) {
            console.error("Error fetching customer", error);
        }
    };

    const resetValues = () => {
        // Reset the values
        setCalories(0);
        setCholesterol(0);
        setProteins(0);
        setSodium(0);
        setTotalCarbohydrates(0);
        setTotalFats(0);

    }

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
        <div style={{width: "100vw", height: "100vh"}}>
            <input type="date" min={minDate} max={maxDate} onChange={(event) => handleDateChange(event.target.value)}/>
            <button onClick={handleSubmit}>Submit</button>
            <canvas ref={chartRef}/>
        </div>
    );
}

export default MonthlyHistory;