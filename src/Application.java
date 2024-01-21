import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends JFrame {
    private final JButton courseFormButton;
    private final JButton studentFormButton;
    private final JButton lecturerFormButton;

    public Application() {
        setTitle("Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout'u BorderLayout olarak ayarla
        setLayout(new BorderLayout());

        // Button colors and styles
        courseFormButton = createStyledButton("Course", new Color(180, 86, 70));
        studentFormButton = createStyledButton("Student", new Color(70, 130, 180));
        lecturerFormButton = createStyledButton("Lecturer", new Color(60, 179, 113));

        // Adding action listeners to the buttons
        courseFormButton.addActionListener(e -> openForm(new CourseForm()));
        studentFormButton.addActionListener(e -> openForm(new StudentForm()));
        lecturerFormButton.addActionListener(e -> openForm(new LecturerForm()));

        // Eklenen butonları panele ekle
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.add(courseFormButton);
        buttonPanel.add(studentFormButton);
        buttonPanel.add(lecturerFormButton);

        // Buton panelini FRAME_START konumuna yerleştir
        add(buttonPanel, BorderLayout.CENTER);

        // Sağ alt köşede metin etiketi eklemek
        JLabel nameLabel = new JLabel("Zeynep Gül Bayar");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setForeground(Color.GRAY);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Metin etiketini FRAME_END (sağ alt köşe) konumuna yerleştir
        add(nameLabel, BorderLayout.SOUTH);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String buttonText, Color backgroundColor) {
        JButton button = new JButton(buttonText);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private void openForm(JFrame form) {
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application application = new Application();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
