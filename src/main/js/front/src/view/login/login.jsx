import React, {useState} from "react";
import axios from 'axios';


const Login =  () => {


    const [fname, setFname] = useState('');
    const [lname, setLname] = useState('');
    const [email, setEmail] = useState('')

    const loginData = {
        fname: fname,
        lname: lname,
        email: email,
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        await axios.post("http://localhost:8080/createCustomer", { loginData })
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    return (
        <div className="App">
            <header className="App-header">
                <form onSubmit={handleSubmit}>
                    <label htmlFor="fname">First name:</label><br/>
                    <input type="text" id="fname" name="fname" value={fname}
                           onChange={e => setFname(e.target.value)}/><br/>
                    <label htmlFor="lname">Last name:</label><br/>
                    <input type="text" id="lname" name="lname" value={lname}
                           onChange={e => setLname(e.target.value)}/><br/>
                    <label htmlFor="email">Enter eMail:</label><br/>
                    <input type="text" id="email" name="email" value={email}
                           onChange={e => setEmail(e.target.value)}/><br/>
                    <input type="submit"/>
                </form>
            </header>
        </div>
    );
}

export default Login;