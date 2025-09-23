import React, { useState } from 'react';
import Timer from './components/Timer';  // Make sure the Timer component exists
import Leaderboard from './components/Leaderboard';  // Ensure this is correctly implemented

function App() {
    const [contestId, setContestId] = useState(1);  // Example contest ID
    const [endTime, setEndTime] = useState(new Date('2025-12-31T23:59:59'));  // Example end time

    return (
        <div className="App">
            <h1>CodeArena Contest</h1>
            <Timer endTime={endTime} />
            <Leaderboard contestId={contestId} />
        </div>
    );
}

export default App;
