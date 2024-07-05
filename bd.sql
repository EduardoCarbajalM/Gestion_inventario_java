CREATE DATABASE Proyecto_Bryan_BD;
USE Proyecto_Bryan_BD;

CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    año INT,
    precio DECIMAL(10, 2)
);

SELECT * FROM libros;

INSERT INTO libros (titulo, autor, año, precio) VALUES
('Cien años de soledad', 'Gabriel García Márquez', 1967, 20.00),
('Don Quijote de la Mancha', 'Miguel de Cervantes', 1605, 15.00),
('Crimen y castigo', 'Fiódor Dostoyevski', 1866, 18.50),
('Matar a un ruiseñor', 'Harper Lee', 1960, 22.30),
('Orgullo y prejuicio', 'Jane Austen', 1813, 19.95),
('1984', 'George Orwell', 1949, 17.75),
('El Gran Gatsby', 'F. Scott Fitzgerald', 1925, 14.20),
('La metamorfosis', 'Franz Kafka', 1915, 13.50),
('En busca del tiempo perdido', 'Marcel Proust', 1913, 25.00),
('Ulises', 'James Joyce', 1922, 23.00),
('El retrato de Dorian Gray', 'Oscar Wilde', 1890, 16.50),
('El guardián entre el centeno', 'J.D. Salinger', 1951, 18.00),
('El amor en los tiempos del cólera', 'Gabriel García Márquez', 1985, 21.00),
('La sombra del viento', 'Carlos Ruiz Zafón', 2001, 20.00),
('Fahrenheit 451', 'Ray Bradbury', 1953, 15.60),
('El nombre de la rosa', 'Umberto Eco', 1980, 18.75),
('El Hobbit', 'J.R.R. Tolkien', 1937, 14.95),
('Los miserables', 'Victor Hugo', 1862, 24.50),
('La Odisea', 'Homero', -800, 12.00),
('Moby Dick', 'Herman Melville', 1851, 16.80),
('Guerra y paz', 'León Tolstói', 1869, 23.50),
('La Iliada', 'Homero', -750, 12.50),
('Anna Karénina', 'León Tolstói', 1877, 19.50),
('Madame Bovary', 'Gustave Flaubert', 1857, 17.25),
('El proceso', 'Franz Kafka', 1925, 15.00);

SELECT * FROM libros;

-- DROP DATABASE Proyecto_Brayan_BD;