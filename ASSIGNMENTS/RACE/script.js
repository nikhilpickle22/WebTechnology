const speeds = {
    char1: { fire: 0.7, land: 1, water: 0.5 },//bulb
    char2: { fire: 1, land: 0.5, water: 0.7 },//charizard
    char3: { fire: 0.6, land: 0.6, water: 1.1 }//blastoise
};

const powerUpEffects = {
    char1: 'Transform1', // Stops other character
    char2: 'Transform2', // Changes terrain and slows them
    char3: 'Transform3'// Speed boost
};

// Global variables to manage the terrain sequence and power-ups
let randomTerrainSequence = [];
let powerUps = []; // Stores current power-ups on the track

/**
 * Starts the race by initializing characters, terrain, and power-ups.
 */
function startRace() {
    const raceTrack = document.getElementById("race-track");
    raceTrack.innerHTML = ''; // Clear previous race elements
    document.getElementById("message").textContent = "";
    document.getElementById("WImage").innerHTML = "";

    // Create and append characters to the race track
    const char1 = createCharacter('char1', 50);
    const char2 = createCharacter('char2', 150);
    const char3 = createCharacter('char3', 250);
    raceTrack.appendChild(char1);
    raceTrack.appendChild(char2);
    raceTrack.appendChild(char3);

    // Generate and display the initial random terrain sequence
    randomTerrainSequence = generateRandomTerrainSequence();
    let currentPosition = 0;
    randomTerrainSequence.forEach(terrain => {
        const terrainDiv = document.createElement('div');
        terrainDiv.classList.add('terrain', terrain);
        terrainDiv.style.left = currentPosition + 'px';
        terrainDiv.style.width = '100px';
        raceTrack.appendChild(terrainDiv);
        currentPosition += 100;
    });

    // Generate and display a limited number of power-ups (2 to 4)
    powerUps = generatePowerUps();
    powerUps.forEach(powerUp => raceTrack.appendChild(powerUp));

    // Initialize character positions
    let char1Position = 0, char2Position = 0, char3Position = 0;
    let isRaceOngoing = true;

    /**
     * Animates the race by updating character positions based on terrain and speed.
     */
    function raceAnimation() {
        if (!isRaceOngoing) return;

        // Determine current terrain for each character
        let char1Terrain = getTerrain(char1Position);
        let char2Terrain = getTerrain(char2Position);
        let char3Terrain = getTerrain(char3Position);

        // Calculate speeds based on terrain
        let char1Speed = speeds.char1[char1Terrain];
        let char2Speed = speeds.char2[char2Terrain];
        let char3Speed = speeds.char3[char3Terrain];

        // Check for power-up collections
        checkPowerUpCollection(char1, char1Position, 'char1');
        checkPowerUpCollection(char2, char2Position, 'char2');
        checkPowerUpCollection(char3, char3Position, 'char3');

        // Update character positions
        char1Position += char1Speed;
        char2Position += char2Speed;
        char3Position += char3Speed;

        // Reflect positions on the UI
        char1.style.left = char1Position + "px";
        char2.style.left = char2Position + "px";
        char3.style.left = char3Position + "px";

        // Check for race completion
        if (char1Position >= 800) {
            isRaceOngoing = false;
            document.getElementById("message").textContent = "You can keep up? who am I kidding? You know you can't";
            document.getElementById("WImage").innerHTML = "<img src='assets/bulb3.png' width='300' height='auto'>";
        } else if (char2Position >= 800) {
            isRaceOngoing = false;
            document.getElementById("message").textContent = "Move or get zapped";
            document.getElementById("WImage").innerHTML = "<img src='assets/char3.png' width='300' height='auto'>";
        } else if (char3Position >= 800) {
            isRaceOngoing = false;
            document.getElementById("message").textContent = "Just take a seat, I got this.";
            document.getElementById("WImage").innerHTML = "<img src='assets/sqrl3.png' width='300' height='auto'>";
        } else {
            requestAnimationFrame(raceAnimation);
        }
    }

    // Start the animation loop
    requestAnimationFrame(raceAnimation);
}

/**
 * Creates a character element with the given ID and vertical position.
 * @param {string} id - The ID of the character.
 * @param {number} topPosition - The vertical position (px) of the character.
 * @returns {HTMLElement} The character DOM element.
 */
function createCharacter(id, topPosition) {
    const char = document.createElement('div');
    char.classList.add('character');
    char.id = id;
    char.style.top = topPosition + 'px';
    return char;
}

/**
 * Generates a random sequence of terrains for the race track.
 * @returns {string[]} An array representing the terrain sequence.
 */
function generateRandomTerrainSequence() {
    const sequence = [];
    const terrains = ['fire', 'land', 'water'];
    for (let i = 0; i < 8; i++) {
        sequence.push(terrains[Math.floor(Math.random() * terrains.length)]);
    }
    return sequence;
}

/**
 * Generates a limited number of power-ups (2 to 4) placed at random locations.
 * @returns {HTMLElement[]} An array of power-up DOM elements.
 */
function generatePowerUps() {
    const xValues = [150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700];
    const yValues = [35, 135, 235];
    const powerUpImages = ['assets/blu.png', 'assets/gree.png', 'assets/red.png'];
    const possibleLocations = Array.from({ length: 50 }, () => [
        xValues[Math.floor(Math.random() * xValues.length)],
        yValues[Math.floor(Math.random() * yValues.length)]
    ]);

    const numPowerUps = Math.floor(Math.random() * 3) + 2;
    const shuffled = shuffleArray([...possibleLocations]);
    const selectedLocations = shuffled.slice(0, numPowerUps);

    const generatedPowerUps = selectedLocations.map(([x, y]) => {
        const powerUp = document.createElement('img');
        const randomImage = powerUpImages[Math.floor(Math.random() * powerUpImages.length)];
        powerUp.src = randomImage;
        powerUp.alt = 'Power Up';
        powerUp.style.position = 'absolute';
        powerUp.style.left = `${x}px`;
        powerUp.style.top = `${y}px`;
        powerUp.style.width = '100px';
        powerUp.style.height = '100px';
        document.body.appendChild(powerUp);
        return powerUp;
    });
    return generatedPowerUps;
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function checkPowerUpCollection(char, charPosition, charId) {
    powerUps.forEach((powerUp, index) => {
        const powerUpRect = powerUp.getBoundingClientRect();
        const charRect = char.getBoundingClientRect();
        if (
            charRect.left < powerUpRect.left + powerUpRect.width &&
            charRect.left + charRect.width > powerUpRect.left &&
            charRect.top < powerUpRect.top + powerUpRect.height &&
            charRect.top + charRect.height > powerUpRect.top
        ) {
            powerUp.remove();
            powerUps.splice(index, 1);
            applyPowerUpEffect(charId);
        }
    });
}

function applyPowerUpEffect(charId) {
    if (powerUpEffects[charId] === 'VoidedTime') {
        console.log(`${charId} activated 'VoidedTime' power-up.`);
        speeds.char2 = { fire: 0, land: 0, water: 0 };
        speeds.char3 = { fire: 0, land: 0, water: 0 };

        setTimeout(() => {
            speeds.char2 = { fire: 1, land: 0.5, water: 0.7 };
            speeds.char3 = { fire: 0.6, land: 0.6, water: 1.1 };
            console.log(`${charId}'s 'VoidedTime' effect has ended.`);
        }, 1500);

        setTimeout(() => {
            document.getElementById("message").textContent = "";
            document.getElementById("WImage").innerHTML = "";
        }, 2000);
    } else if (powerUpEffects[charId] === 'FatesGamble') {
        console.log(`${charId} activated 'FatesGamble' power-up.`);
        const newTerrainSequence = generateRandomTerrainSequence();
        document.querySelectorAll('.terrain').forEach((terrain, index) => {
            terrain.className = 'terrain ' + newTerrainSequence[index];
        });

        setTimeout(() => {
            document.getElementById("message").textContent = "";
            document.getElementById("WImage").innerHTML = "";
        }, 2000);
    } else if (powerUpEffects[charId] === 1.3) {
        console.log(`${charId} activated speed boost.`);
        ['fire', 'land', 'water'].forEach(terrain => {
            speeds[charId][terrain] *= powerUpEffects[charId];
        });

        setTimeout(() => {
            ['fire', 'land', 'water'].forEach(terrain => {
                speeds[charId][terrain] /= powerUpEffects[charId];
            });
            console.log(`${charId}'s speed boost has ended.`);
        }, 1500);
    }
}

function getTerrain(charPosition) {
    return randomTerrainSequence[Math.floor(charPosition / 100)];
}

startRace();
