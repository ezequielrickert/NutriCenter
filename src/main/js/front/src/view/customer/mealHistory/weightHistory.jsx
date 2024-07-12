import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import './styles.css';
import Chart from 'chart.js/auto';
import config from "bootstrap/js/src/util/config";


const WeightHistory = ({ customerName }) => {

    // Verificar que customerName no es undefined
    useEffect(() => {
        console.log("Customer Name in WeightHistory:", customerName);
    }, [customerName]);

    const today = new Date();
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(today.getFullYear() - 1);

    const date = new Date();
    date.setDate(date.getDate() - 6);
    const initialDate = date.toISOString().split('T')[0];
    const [selectedDate, setSelectedDate] = useState(initialDate);

    const chartRef = useRef(null);
    const chartInstance = useRef(null);


    useEffect(() => {
            createWeightChart();
    }, [selectedDate]);

    const handleDateChange = (event) => {
        setSelectedDate(event.target.value);
    };

    function buildChart(weights, dates) {
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const data = {
            labels: dates,
            datasets: [
                {
                    label: 'Dataset',
                    data: weights,
                    borderColor: 'red', // define the color directly
                    backgroundColor: 'rgba(255, 0, 0, 0.5)', // define the color directly with transparency
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
    }


    function getWeights(entries) {
        return entries.map(entry => entry.weight);
        //return [1.2, 2.0, 3.9, 4.5, 5.5, 6.9, 7.7, 8.1, 9.1, 10.1];
    }

    //return ['2021-09-01', '2021-09-02', '2021-09-03', '2021-09-04', '2021-09-05', '2021-09-06', '2021-09-07', '2021-09-08', '2021-09-09', '2021-09-10'];
    function getDates(entries) {

        return entries.map(entry => entry.date);
    }

    const createWeightChart = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/getWeight/${customerName}/${selectedDate}`);
            console.log("Raw response data:", response.data);
            let entries = JSON.parse(response.data);
            console.log("Parsed entries:", entries);


            if (entries && typeof entries === 'object' && !Array.isArray(entries)) {
                entries = [entries];
            }

            if (Array.isArray(entries)) {
                console.log("Before separating entries; " + entries);
                const weights = getWeights(entries);
                const dates = getDates(entries);

                console.log("weights ; " + weights);
                console.log("dates ; " + dates);

                // Destroy the old chart instance before creating a new one
                if (chartInstance.current) {
                    chartInstance.current.destroy();
                }

                buildChart(weights, dates);
            } else {
                console.error("Error: Expected 'days' to be an array but received:", entries);
            }
        } catch (error) {
            console.error("Error creating weight chart", error);
        }
    };



    return (
        <div style={{width: '100%', height: '100%', padding: '10px', boxSizing: 'border-box'}}>
            <div style={{width: '100%', textAlign: 'center', marginBottom: '10px'}}>
                <label htmlFor="date">Select date :  </label>
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
            <div style={{flexGrow: 1, width: '100%', height: '100%', position: 'relative'}}>
                <canvas ref={chartRef} style={{width: '100%', height: '100%'}}/>
            </div>
        </div>
    );
}

export default WeightHistory;
