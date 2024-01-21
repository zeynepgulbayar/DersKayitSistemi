import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LecturerForm extends JFrame {
    private final JTextField teacherNoField;
    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField departmentField;
    private final JButton saveButton;
    private final JTable lecturerTable;
    private final JTextField filterTextField;

    private final DefaultTableModel tableModel;

    public LecturerForm() {
        setTitle("Lecturer Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 2));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setResizable(false);

        teacherNoField = new JTextField();
        nameField = new JTextField();
        surnameField = new JTextField();
        departmentField = new JTextField();
        saveButton = createStyledButton("Save");

        tableModel = new DefaultTableModel();
        lecturerTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(lecturerTable);

        filterTextField = new JTextField();
        filterTextField.setColumns(20);
        filterTextField.setBorder(new EmptyBorder(5, 10, 5, 10));
        filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterLecturerTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterLecturerTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events.
            }
        });

        //        filterTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                filterLecturerTable();
//            }
//        });

        leftPanel.add(createLabel("Teacher No:"));
        leftPanel.add(teacherNoField);
        leftPanel.add(createLabel("Name:"));
        leftPanel.add(nameField);
        leftPanel.add(createLabel("Surname:"));
        leftPanel.add(surnameField);
        leftPanel.add(createLabel("Department:"));
        leftPanel.add(departmentField);
        leftPanel.add(new JLabel());
        leftPanel.add(saveButton);

        leftPanel.add(new JPanel()); // Empty panel for layout consistency

        add(leftPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(createLabel("Search:"));
        filterPanel.add(filterTextField);

        add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"Teacher No", "Name", "Surname", "Department"};
        tableModel.setColumnIdentifiers(columnNames);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLecturer();
                refreshLecturerTable();
                JOptionPane.showMessageDialog(null, "Lecturer successfully saved!");
            }
        });

        refreshLecturerTable();
    }

    private void saveLecturer() {
        JSONArray lecturersArray = new JSONArray();

        // Read existing data
        try (FileReader reader = new FileReader("lecturers.json")) {
            JSONParser parser = new JSONParser();
            lecturersArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Create a new JSON object for the new data
        JSONObject lecturerJson = new JSONObject();
        lecturerJson.put("TeacherNo", Integer.parseInt(teacherNoField.getText()));
        lecturerJson.put("Name", nameField.getText());
        lecturerJson.put("Surname", surnameField.getText());
        lecturerJson.put("Department", departmentField.getText());

        // Add the new data to the array
        lecturersArray.add(lecturerJson);

        // Write the updated array back to the file
        try (FileWriter file = new FileWriter("lecturers.json")) {
            file.write(lecturersArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshLecturerTable() {
        tableModel.setRowCount(0);
        List<JSONObject> lecturers = readLecturers("lecturers.json");
        for (JSONObject lecturer : lecturers) {
            Object[] row = {
                    lecturer.get("TeacherNo"),
                    lecturer.get("Name"),
                    lecturer.get("Surname"),
                    lecturer.get("Department")
            };
            tableModel.addRow(row);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(new EmptyBorder(5, 5, 5, 10));
        return label;
    }

    private JButton createStyledButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(180, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private List<JSONObject> readLecturers(String fileName) {
        List<JSONObject> lecturers = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            Object obj = parser.parse(reader);

            if (obj instanceof JSONArray jsonArray) {

                for (Object o : jsonArray) {
                    if (o instanceof JSONObject) {
                        lecturers.add((JSONObject) o);
                    }
                }
            } else {
                System.err.println("Invalid JSON format. Expected JSONArray.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return lecturers;
    }

    private void filterLecturerTable() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        lecturerTable.setRowSorter(sorter);

        String text = filterTextField.getText().toLowerCase();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LecturerForm().setVisible(true);
            }
        });
    }
}
