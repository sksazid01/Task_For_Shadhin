DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority INT NOT NULL CHECK (priority BETWEEN 1 AND 5),
    completed BOOLEAN DEFAULT FALSE
);