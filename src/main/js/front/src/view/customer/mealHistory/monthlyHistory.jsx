import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto';

const MonthlyHistory = () => {
    const [isValidUser, setIsValidUser] = useState(false);
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    const userRole = localStorage.getItem('role');

    const date = new Date();
    date.setDate(date.getDate() - 6);
    const initialDate = date.toISOString().split('T')[0];
    const [selectedDate, setSelectedDate] = useState(initialDate);
    const [calories, setCalories] = useState(0);
    const [cholesterol, setCholesterol] = useState(0);
    const [proteins, setProteins] = useState(0);
    const [sodium, setSodium] = useState(0);
    const [totalCarbohydrates, setTotalCarbohydrates] = useState(0);
    const [totalFats, setTotalFats] = useState(0);

    const chartRef = useRef(null);
    const chartInstance = useRef(null);

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
    }, [token, username, userRole]);

    useEffect(() => {
        if (isValidUser) {
            createPieChart();
        }
    }, [isValidUser, selectedDate]);

    useEffect(() => {
        if (chartInstance.current) {
            chartInstance.current.data.datasets[0].data = [calories, cholesterol, proteins, sodium, totalCarbohydrates, totalFats];
            chartInstance.current.update();
        }
    }, [calories, cholesterol, proteins, sodium, totalCarbohydrates, totalFats]);

    const processDays = (days) => {
        return new Promise((resolve) => {
            let totalCalories = 0;
            let totalCholesterol = 0;
            let totalProteins = 0;
            let totalSodium = 0;
            let totalCarbohydrates = 0;
            let totalFats = 0;

            days.forEach(day => {
                if (day.breakfast && day.breakfast.ingredientList) {
                    day.breakfast.ingredientList.forEach(ingredient => {
                        totalCalories += ingredient.calories;
                        totalCholesterol += ingredient.cholesterol;
                        totalProteins += ingredient.proteins;
                        totalSodium += ingredient.sodium;
                        totalCarbohydrates += ingredient.totalCarbohydrate;
                        totalFats += ingredient.totalFat;
                    });
                }

                if (day.lunch && day.lunch.ingredientList) {
                    day.lunch.ingredientList.forEach(ingredient => {
                        totalCalories += ingredient.calories;
                        totalCholesterol += ingredient.cholesterol;
                        totalProteins += ingredient.proteins;
                        totalSodium += ingredient.sodium;
                        totalCarbohydrates += ingredient.totalCarbohydrate;
                        totalFats += ingredient.totalFat;
                    });
                }

                if (day.dinner && day.dinner.ingredientList) {
                    day.dinner.ingredientList.forEach(ingredient => {
                        totalCalories += ingredient.calories;
                        totalCholesterol += ingredient.cholesterol;
                        totalProteins += ingredient.proteins;
                        totalSodium += ingredient.sodium;
                        totalCarbohydrates += ingredient.totalCarbohydrate;
                        totalFats += ingredient.totalFat;
                    });
                }
            });

            setCalories(totalCalories);
            setCholesterol(totalCholesterol);
            setProteins(totalProteins);
            setSodium(totalSodium);
            setTotalCarbohydrates(totalCarbohydrates);
            setTotalFats(totalFats);

            resolve();
        });
    };

    const handleDateChange = (event) => {
        setSelectedDate(event.target.value);
    };

    const createPieChart = async () => {
        try {
            resetValues();
            const response = await axios.get(`http://localhost:8080/getDaysByDate/${username}/${selectedDate}`);
            console.log('Type of response.data:', typeof response.data);
            let days = JSON.parse(response.data);

            if (days && typeof days === 'object' && !Array.isArray(days)) {
                days = [days];
            }

            if (Array.isArray(days)) {
                await processDays(days);
            } else {
                console.error("Error: Expected 'days' to be an array but received:", days);
            }

            if (chartInstance.current) {
                chartInstance.current.destroy();
            }

            if (chartRef.current) {
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
                        aspectRatio: 1.23,
                        legend: {
                            display: true,
                            position: 'right',
                            align: 'center',
                            fullWidth: true,
                            reverse: false,
                            labels: {
                                fontSize: 90,
                                boxWidth: 50,
                                fontStyle: 'bold',
                                fontColor: 'black',
                                fontFamily: 'Arial',
                                usePointStyle: true,
                                padding: 20,
                            }
                        }
                    }
                });
            }

        } catch (error) {
            console.error("Error fetching customer", error);
        }
    };

    const resetValues = () => {
        setCalories(0);
        setCholesterol(0);
        setProteins(0);
        setSodium(0);
        setTotalCarbohydrates(0);
        setTotalFats(0);
    };

    const handleSubmit = () => {
        createPieChart();
    };

    if (!isValidUser) {
        return null;
    }

    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
    const minDate = oneYearAgo.toISOString().split('T')[0];
    const currentDate = new Date();
    const maxDate = currentDate.toISOString().split('T')[0];

    return (
        <div style={{ width: "100vw", height: "100vh" }}>
            <input
                type="date"
                min={minDate}
                max={maxDate}
                value={selectedDate}
                onChange={handleDateChange}
            />
            <button onClick={handleSubmit} disabled={!selectedDate}>
                Submit
            </button>
            <canvas ref={chartRef} />
        </div>
    );
};

export default MonthlyHistory;
