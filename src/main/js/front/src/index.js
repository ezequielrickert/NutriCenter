import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// const express = require("express")
// const app = express()
// app.listen(3000, () => console.log('listening at 3000'))
// app.use(express.static('public'))
//
// app.post('/home', (req, res) => {
//     console.log(res)
// })

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

reportWebVitals();
