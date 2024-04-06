import './App.css';

function App() {

  return (
    <div className="App">
      <header className="App-header">
        <form action='/home' method='POST'>
          <label htmlFor="fname">First name:</label><br/>
          <input type="text" id="fname" name="fname"/><br/>
          <label htmlFor="lname">Last name:</label><br/>
          <input type="text" id="lname" name="lname"/><br/>
          <input type="submit"/>
        </form>
      </header>
    </div>
  );
}

export default App;
