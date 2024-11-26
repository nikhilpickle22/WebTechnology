<?php
// Database connection
$host = 'localhost';
$dbname = 'movies_db';
$user = 'root';
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Database connection failed: " . $e->getMessage());
}

// Handle form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = $_POST['title'];
    $genre = $_POST['genre'];
    $year = $_POST['year'];

    if (!empty($title) && !empty($genre) && !empty($year)) {
        $stmt = $pdo->prepare("INSERT INTO movies (title, genre, year) VALUES (?, ?, ?)");
        $stmt->execute([$title, $genre, $year]);
        $message = "Movie added successfully!";
    } else {
        $message = "All fields are required!";
    }
}

// Fetch all movies
$movies = $pdo->query("SELECT * FROM movies")->fetchAll(PDO::FETCH_ASSOC);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #2c3e50;
            color: #ecf0f1;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #e74c3c;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 40px auto;
            background-color: #34495e;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        label {
            display: block;
            margin: 15px 0 5px;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px;
            border: none;
            border-radius: 5px;
            background-color: #ecf0f1;
            color: #2c3e50;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #e74c3c;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #c0392b;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ecf0f1;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #e74c3c;
        }
        .message {
            color: #2ecc71;
            text-align: center;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <h1>Movie Manager</h1>
    <div class="container">
        <!-- Add Movie Form -->
        <form method="post" action="">
            <label for="title">Movie Title</label>
            <input type="text" id="title" name="title" placeholder="Enter movie title" required>

            <label for="genre">Genre</label>
            <input type="text" id="genre" name="genre" placeholder="Enter movie genre" required>

            <label for="year">Release Year</label>
            <input type="number" id="year" name="year" placeholder="Enter release year" required>

            <button type="submit">Add Movie</button>
        </form>

        <!-- Success/Error Message -->
        <?php if (!empty($message)) : ?>
            <p class="message"><?= htmlspecialchars($message) ?></p>
        <?php endif; ?>

        <!-- Movies Table -->
        <?php if ($movies): ?>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Year</th>
                </tr>
                <?php foreach ($movies as $movie): ?>
                    <tr>
                        <td><?= htmlspecialchars($movie['id']) ?></td>
                        <td><?= htmlspecialchars($movie['title']) ?></td>
                        <td><?= htmlspecialchars($movie['genre']) ?></td>
                        <td><?= htmlspecialchars($movie['year']) ?></td>
                    </tr>
                <?php endforeach; ?>
            </table>
        <?php else: ?>
            <p>No movies found.</p>
        <?php endif; ?>
    </div>
</body>
</html>
