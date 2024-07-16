import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Chart from 'chart.js/auto';

const WeightHistory = ({ customerName }) => {
    const today = new Date();
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(today.getFullYear() - 1);

    const date = new Date();
    date.setDate(date.getDate() - 6);
    const initialDate = date.toISOString().split('T')[0];
    const [selectedDate, setSelectedDate] = useState(initialDate);
    const [isLoading, setIsLoading] = useState(false); // State to control loading

    const chartRef = useRef(null);
    const chartInstance = useRef(null);

    useEffect(() => {
        createWeightChart();
    }, [selectedDate]);

    const handleDateChange = (event) => {
        setSelectedDate(event.target.value);
    };

    const buildChart = (weights, dates) => {
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const data = {
            labels: dates,
            datasets: [
                {
                    label: 'Weight',
                    data: weights,
                    borderColor: 'blue',
                    backgroundColor: 'rgba(0, 0, 255, 0.5)',
                    pointStyle: 'circle',
                    pointRadius: 10,
                    pointHoverRadius: 15
                }
            ]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true,
                plugins: {
                    title: {
                        display: true,
                        text: (ctx) => 'Point Style: ' + ctx.chart.data.datasets[0].pointStyle,
                    }
                }
            }
        };

        if (chartRef.current) {
            const myChartRef = chartRef.current.getContext('2d');
            chartInstance.current = new Chart(myChartRef, config);
        }
    };

    const getWeights = (entries) => {
        return entries.map(entry => entry.weight);
    };

    const getDates = (entries) => {
        return entries.map(entry => entry.date);
    };

    const createWeightChart = async () => {
        setIsLoading(true); // Set loading state when fetching starts
        try {
            const response = await axios.get(`http://localhost:8080/getWeight/${customerName}/${selectedDate}`);
            let entries = JSON.parse(response.data);

            if (entries && typeof entries === 'object' && !Array.isArray(entries)) {
                entries = [entries];
            }

            if (Array.isArray(entries)) {
                const weights = getWeights(entries);
                const dates = getDates(entries);

                // Destroy the old chart instance before creating a new one
                if (chartInstance.current) {
                    chartInstance.current.destroy();
                }

                buildChart(weights, dates);
            } else {
                console.error("Error: Expected 'entries' to be an array but received:", entries);
            }
        } catch (error) {
            console.error("Error creating weight chart", error);
        } finally {
            setIsLoading(false); // Always set loading to false when request completes
        }
    };

    // Display loading message or component based on loading state
    if (isLoading) {
        return <p>Loading...</p>;
    }

    return (
        <div style={{ width: '100%', height: '100%', padding: '10px', boxSizing: 'border-box' }}>
            <div style={{ width: '100%', textAlign: 'center', marginBottom: '10px' }}>
                <label htmlFor="date">Select date:</label>
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

export default WeightHistory;
