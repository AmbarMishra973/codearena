import React from 'react';
import './App.css';
import CodeEditor from './CodeEditor';

function App() {
  return (
    <div className="App">
      <h1>CodeArena - Submit Code</h1>
      <CodeEditor problemId={1} language="java" userId={1} />
    </div>
  );
}

export default App;
