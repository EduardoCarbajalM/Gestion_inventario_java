package Programas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFrame extends JFrame {
    private JTextField txtTitulo, txtAutor, txtAño, txtPrecio;
    private JButton btnGuardar;
    private int libroId;
    private MainFrame mainFrame;

    public UpdateFrame(int id, String titulo, String autor, int año, double precio, MainFrame mainFrame) {
        this.libroId = id;
        this.mainFrame = mainFrame;
        
        setTitle("Actualizar Libro");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        
        add(new JLabel("Título:"));
        txtTitulo = new JTextField(titulo);
        add(txtTitulo);
        
        add(new JLabel("Autor:"));
        txtAutor = new JTextField(autor);
        add(txtAutor);
        
        add(new JLabel("Año:"));
        txtAño = new JTextField(String.valueOf(año));
        add(txtAño);
        
        add(new JLabel("Precio:"));
        txtPrecio = new JTextField(String.valueOf(precio));
        add(txtPrecio);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarLibro();
            }
        });
        add(btnGuardar);
        
        setVisible(true);
    }
    
    private void actualizarLibro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int año = Integer.parseInt(txtAño.getText());
        double precio = Double.parseDouble(txtPrecio.getText());

        conexion db = new conexion();
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE libros SET titulo = ?, autor = ?, año = ?, precio = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, titulo);
                stmt.setString(2, autor);
                stmt.setInt(3, año);
                stmt.setDouble(4, precio);
                stmt.setInt(5, libroId);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente!");
                    mainFrame.recargarLibros();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Libro no encontrado!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar libro: " + ex.getMessage());
        }
    }
}
