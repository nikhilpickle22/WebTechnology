<?php
// Load the XML file
$xmlFile = 'movies.xml'; // Path to your XML file
if (!file_exists($xmlFile)) {
    die("Error: XML file not found.");
}

$xml = simplexml_load_file($xmlFile);
if ($xml === false) {
    die("Error: Unable to load XML file.");
}

// Extract and display movie data
echo "<h1>Movies List</h1>";
echo "<table border='1' cellpadding='10'>";
echo "<tr><th>Title</th><th>Genre</th><th>Year</th></tr>";

foreach ($xml->movie as $movie) {
    echo "<tr>";
    echo "<td>" . htmlspecialchars($movie->title) . "</td>";
    echo "<td>" . htmlspecialchars($movie->genre) . "</td>";
    echo "<td>" . htmlspecialchars($movie->year) . "</td>";
    echo "</tr>";
}

echo "</table>";
?>
