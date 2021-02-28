import './App.css';
import QrReader from './qrReader';

function App() {
    return (
        <div className="App">
            <QrReader facingMode="environment" />
        </div>
    );
}

export default App;