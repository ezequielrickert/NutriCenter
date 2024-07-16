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
    const [isLoading, setIsLoading] = useState(false);
    const [chartData, setChartData] = useState({ weights: [], dates: [] });

    const chartRef = useRef(null);
    const chartInstance = useRef(null);

    useEffect(() => {
        fetchWeightData();
    }, [selectedDate]);

    const handleDateChange = (event) => {
        setSelectedDate(event.target.value);
    };

    const buildChart = (weights, dates) => {
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
                        text: 'Weight History',
                    }
                }
            }
        };

        if (chartRef.current) {
            const myChartRef = chartRef.current.getContext('2d');

            if (chartInstance.current) {
                chartInstance.current.destroy();
            }

            chartInstance.current = new Chart(myChartRef, config);
        }
    };

    const fetchWeightData = async () => {
        setIsLoading(true);
        try {
            const response = await axios.get(`http://localhost:8080/getWeight/${customerName}/${selectedDate}`);
            let entries = response.data;

            if (typeof entries === 'string') {
                entries = JSON.parse(entries);
            }

            if (!Array.isArray(entries)) {
                entries = [entries];
            }

            if (entries.length > 0) {
                const weights = entries.map(entry => entry.weight);
                const dates = entries.map(entry => entry.date);
                setChartData({ weights, dates });
            } else {
                setChartData({ weights: [], dates: [] });
                if (chartInstance.current) {
                    chartInstance.current.destroy();
                }
            }
        } catch (error) {
            console.error("Error fetching weight data", error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        if (chartData.weights.length > 0 && chartData.dates.length > 0) {
            buildChart(chartData.weights, chartData.dates);
        }
    }, [chartData]);

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
                {isLoading ? (
                    <p>Loading...</p>
                ) : (
                    <canvas ref={chartRef} style={{ width: '100%', height: '100%' }} />
                )}
            </div>
        </div>
    );
};

export default WeightHistory;
