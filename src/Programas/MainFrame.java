package Programas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame {
    private JTextField txtTitulo, txtAutor, txtAño, txtPrecio;
    private JButton btnAgregar;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public MainFrame() {
        setTitle("Gestión de Libros");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel para formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        
        formPanel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        formPanel.add(txtTitulo);
        
        formPanel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        formPanel.add(txtAutor);
        
        formPanel.add(new JLabel("Año:"));
        txtAño = new JTextField();
        formPanel.add(txtAño);
        
        formPanel.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        formPanel.add(txtPrecio);
        
        add(formPanel, BorderLayout.NORTH);
        
        // Botón para agregar un nuevo libro
        btnAgregar = new JButton("Agregar Nuevo Libro");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarLibro();
            }
        });
        
        add(btnAgregar, BorderLayout.SOUTH);
        
        // Modelo de tabla para mostrar los libros
        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "Año", "Precio"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Listener para seleccionar un registro de la tabla
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        String titulo = tableModel.getValueAt(selectedRow, 1).toString();
                        String autor = tableModel.getValueAt(selectedRow, 2).toString();
                        int año = Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString());
                        double precio = Double.parseDouble(tableModel.getValueAt(selectedRow, 4).toString());
                        
                        mostrarOpciones(id, titulo, autor, año, precio);
                    }
                }
            }
        });
        
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        cargarLibros();
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
    
    private void cargarLibros() {
        conexion db = new conexion();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM libros";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("año"),
                            rs.getDouble("precio")
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar libros: " + ex.getMessage());
        }
    }
    
    private void agregarLibro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int año = Integer.parseInt(txtAño.getText());
        double precio = Double.parseDouble(txtPrecio.getText());

        conexion db = new conexion();
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO libros (titulo, autor, año, precio) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, titulo);
                stmt.setString(2, autor);
                stmt.setInt(3, año);
                stmt.setDouble(4, precio);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Libro agregado exitosamente!");
                tableModel.setRowCount(0); // Limpiar la tabla
                cargarLibros(); // Recargar la tabla
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar libro: " + ex.getMessage());
        }
    }
    
    private void mostrarOpciones(int id, String titulo, String autor, int año, double precio) {
        int option = JOptionPane.showOptionDialog(this, "Seleccione una opción", "Opciones",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"Actualizar", "Eliminar"}, null);
        if (option == JOptionPane.YES_OPTION) {
            new UpdateFrame(id, titulo, autor, año, precio, this);
        } else if (option == JOptionPane.NO_OPTION) {
            eliminarLibro(id);
        }
    }
    
    private void eliminarLibro(int id) {
        conexion db = new conexion();
        try (Connection conn = db.getConnection()) {
            String sql = "DELETE FROM libros WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Libro eliminado exitosamente!");
                    tableModel.setRowCount(0); // Limpiar la tabla
                    cargarLibros(); // Recargar la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "Libro no encontrado!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar libro: " + ex.getMessage());
        }
    }
    
    public void recargarLibros() {
        tableModel.setRowCount(0); // Limpiar la tabla
        cargarLibros(); // Recargar la tabla
    }
}
