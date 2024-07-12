import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto';

const MonthlyHistory = ({ customerName }) => {

    // Verificar que customerName no es undefined
    useEffect(() => {
        console.log("Customer Name in MonthlyHistory:", customerName);
    }, [customerName]);

    const today = new Date();
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(today.getFullYear() - 1);

    const initialDate = new Date();
    initialDate.setDate(initialDate.getDate() - 6);
    const [selectedDate, setSelectedDate] = useState(initialDate.toISOString().split('T')[0]);

    const [calories, setCalories] = useState(0);
    const [cholesterol, setCholesterol] = useState(0);
    const [proteins, setProteins] = useState(0);
    const [sodium, setSodium] = useState(0);
    const [totalCarbohydrates, setTotalCarbohydrates] = useState(0);
    const [totalFats, setTotalFats] = useState(0);

    const chartRef = useRef(null);
    const chartInstance = useRef(null);


    // Puede ser que haya que cambiar esto para que se cree el chart apenas se entra
    // Ya que este useEffect se ejecuta cuando se cambia la fecha
    useEffect(() => {
            createPieChart();
    }, [selectedDate]);

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
            const response = await axios.get(`http://localhost:8080/getDaysByDate/${customerName}/${selectedDate}`);
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
                        responsive: true,
                        maintainAspectRatio: false, // To fit within parent container
                        plugins: {
                            legend: {
                                position: 'bottom',
                            },
                        },
                    },
                });
            }
        } catch (error) {
            console.error("Error creating chart:", error);
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


    return (
        <div style={{ width: '100%', height: '100%', padding: '10px', boxSizing: 'border-box' }}>
            <div style={{ width: '100%', textAlign: 'center', marginBottom: '10px' }}>
                <label htmlFor="date">Select date : </label>
                <input
                    type="date"
                    id="date"
                    name="date"
                    value={selectedDate}
                    onChange={handleDateChange}
                    min={oneYearAgo.toISOString().split('T')[0]}
                    max={today.toISOString().split('T')[0]}
                />
            </div>
            <div style={{ flexGrow: 1, width: '100%', height: '100%', position: 'relative' }}>
                <canvas ref={chartRef} style={{ width: '100%', height: '100%' }} />
            </div>
        </div>
    );
};

export default MonthlyHistory;

