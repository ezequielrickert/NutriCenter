import React, { useEffect, useState } from "react";
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import Footer from '../../../components/footer';
import './CreationStyle.css';  // Importa tu archivo CSS aquí
import 'animate.css';

const StoreCreateIngredient = () => {
    const [name, setName] = useState('');
    const [allergy, setAllergy] = useState(null);
    const [allergies, setAllergies] = useState([]);
    const [proteins, setProteins] = useState('');
    const [sodium, setSodium] = useState('');
    const [calories, setCalories] = useState('');
    const [totalFat, setTotalFat] = useState('');
    const [cholesterol, setCholesterol] = useState('');
    const [totalCarbohydrate, setTotalCarbohydrate] = useState('');

    useEffect(() => {
        axios.get('http://localhost:8080/allergies')
            .then(response => {
                const data = JSON.parse(response.data);
                if (Array.isArray(data)) {
                    setAllergies(data);
                } else {
                    console.error('Data received from server is not an array');
                }
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }, []);

    const reset = () => {
        const formElements = document.querySelectorAll('.form-control');
        formElements.forEach(element => {
            if (element.classList.contains('success')) {
                element.classList.remove('success');
            } else if (element.classList.contains('error')) {
                element.classList.remove('error');
            }
            element.classList.add('fade-out', 'animate__animated', 'animate__fadeOut'); // Añadimos clases de animación
            setTimeout(() => {
                element.classList.remove('fade-out', 'animate__animated', 'animate__fadeOut');
                element.value = ''; // Borramos el valor del campo
            }, 1000); // Tiempo de duración de la animación
        });

        setName('');
        setAllergy(null);
        setProteins('');
        setSodium('');
        setCalories('');
        setTotalFat('');
        setCholesterol('');
        setTotalCarbohydrate('');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const ingredientData = {
            ingredientName: name,
            allergy: allergy,
            proteins: proteins,
            sodium: sodium,
            calories: calories,
            totalFat: totalFat,
            cholesterol: cholesterol,
            totalCarbohydrate: totalCarbohydrate
        };

        try {
            const response = await axios.post("http://localhost:8080/ingredient", ingredientData);
            console.log(response);
            toast.success('Ingredient created successfully!');
            const formElements = document.querySelectorAll('.form-control');
            formElements.forEach(element => {
                element.classList.add('success'); // Añadimos la clase de éxito
            });
            setTimeout(() => {
                reset();
            }, 700); // Esperamos a que termine la animación antes de resetear el formulario
        } catch (error) {
            console.error(error);
            toast.error('Failed to create ingredient!');
            const formElements = document.querySelectorAll('.form-control');
            formElements.forEach(element => {
                element.classList.add('error'); // Añadimos la clase de error
            });
            setTimeout(() => {
                reset();
            }, 700); // Esperamos a que termine la animación antes de resetear el formulario
        }
    };

    const isFormValid = name && allergy && proteins && sodium &&
        calories && totalFat && cholesterol && totalCarbohydrate;

    return (
        <Container className="py-5">
            <Row className="justify-content-center">
                <Col md="10">  {/* Cambiado de md="8" a md="10" */}
                    <h1 className="text-center mb-4" style={{ fontSize: '2rem' }}>Create Ingredient</h1>

                    <Form onSubmit={handleSubmit}>
                        <Row className="justify-content-center mb-4">
                            <Col md="6">
                                <Form.Group controlId="ingredientName">
                                    <Form.Label>Ingredient Name</Form.Label>
                                    <Form.Control type="text" value={name} onChange={e => setName(e.target.value)} className="form-control" />
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row className="justify-content-center mb-4">
                            <Col md="6">
                                <Form.Group controlId="allergy">
                                    <Form.Label>Allergy</Form.Label>
                                    <Form.Control as="select" value={allergy ? allergy.allergyName : ''}
                                                  onChange={e => setAllergy(allergies.find(a => a.allergyName === e.target.value))}
                                                  style={{ textAlignLast: 'center' }} className="form-control">
                                        <option value="">Select an allergy</option>
                                        {allergies.map((allergy, index) => (
                                            <option key={index} value={allergy.allergyName}>{allergy.allergyName}</option>
                                        ))}
                                    </Form.Control>
                                </Form.Group>
                            </Col>
                        </Row>

                        <h3 className="text-center mb-4" style={{ fontSize: '1rem' }}>Content per 100 grs</h3>

                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId="proteins">
                                    <Form.Label>Proteins</Form.Label>
                                    <Form.Control type="number" value={proteins} onChange={e => setProteins(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="sodium">
                                    <Form.Label>Sodium</Form.Label>
                                    <Form.Control type="number" value={sodium} onChange={e => setSodium(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId="calories">
                                    <Form.Label>Calories</Form.Label>
                                    <Form.Control type="number" value={calories} onChange={e => setCalories(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="totalFat">
                                    <Form.Label>Total Fat</Form.Label>
                                    <Form.Control type="number" value={totalFat} onChange={e => setTotalFat(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId="cholesterol">
                                    <Form.Label>Cholesterol</Form.Label>
                                    <Form.Control type="number" value={cholesterol} onChange={e => setCholesterol(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="totalCarbohydrate">
                                    <Form.Label>Total Carbohydrate</Form.Label>
                                    <Form.Control type="number" value={totalCarbohydrate} onChange={e => setTotalCarbohydrate(e.target.value)} min="0" className="form-control" />
                                </Form.Group>
                            </Col>
                        </Row>

                        <Button variant="primary" type="submit" disabled={!isFormValid} className="mt-3">
                            Submit
                        </Button>
                    </Form>
                </Col>
            </Row>
            <ToastContainer />
            <Footer />
        </Container>
    );
};

export default StoreCreateIngredient;
