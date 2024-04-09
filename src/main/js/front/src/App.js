import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import InitialPage from "./view/Home/initial";
import LoginCustomer from "./view/Customer/login/login";
import SignUpCustomer from "./view/Customer/signup/signup";
import SignupSuperAdmin from "./view/SuperAdmin/signup/signup";

function App() {
  return (
      <Router>
        <div className="App">
          <Routes>
              <Route path="/" element={<InitialPage />} />
              <Route path="/signUpCustomer" element={<SignUpCustomer />} />
              <Route path="/loginCustomer" element={<LoginCustomer />} />
              <Route path="/signUpStore" element={<SignUpCustomer />} />
              <Route path="/loginStore" element={<SignupSuperAdmin />} />
              <Route path="/signUpSuperAdmin" element={<SignupSuperAdmin />} />
          </Routes>
        </div>
      </Router>
  );
}

export default App;