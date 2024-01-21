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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student {
    private final int studentNumber;
    private final String studentName;
    private final String studentSurname;
    private final String studentDepartment;
    private final String studentCourses;
    private final String studentCourseCode;

    public Student(int studentNumber, String studentName, String studentSurname, String studentDepartment, String studentCourses, String studentCourseCode) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.studentDepartment = studentDepartment;
        this.studentCourses = studentCourses;
        this.studentCourseCode = studentCourseCode;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public String getStudentCourses() {
        return studentCourses;
    }

    public String getStudentCourseCode() {
        return studentCourseCode;
    }
}

public class StudentForm extends JFrame {
    private final JTextField studentNumberField;
    private final JTextField studentNameField;
    private final JTextField studentSurnameField;
    private final JTextField studentDepartmentField;
    private final JComboBox<String> studentCoursesField;
    private final JButton saveButton;
    private final JTable studentTable;
    private final JTextField filterTextField;

    private final DefaultTableModel tableModel;

    public StudentForm() {
        setTitle("Student Form");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 2));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setResizable(false);

        studentNumberField = new JTextField();
        studentNameField = new JTextField();
        studentSurnameField = new JTextField();
        studentDepartmentField = new JTextField();
        saveButton = createStyledButton("Save");

        tableModel = new DefaultTableModel();
        studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        filterTextField = new JTextField();
        filterTextField.setColumns(20);
        filterTextField.setBorder(new EmptyBorder(5, 10, 5, 10));

//        filterTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                filterStudentTable();
//            }
//        });
        filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStudentTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterStudentTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events.
            }
        });
        // Populate the JComboBox for "Student Courses"
        List<String> courses = readCourses();
        DefaultComboBoxModel<String> coursesComboBoxModel = new DefaultComboBoxModel<>(courses.toArray(new String[0]));
        studentCoursesField = new JComboBox<>(coursesComboBoxModel);

        leftPanel.add(createLabel("Student Number:"));
        leftPanel.add(studentNumberField);
        leftPanel.add(createLabel("Student Name:"));
        leftPanel.add(studentNameField);
        leftPanel.add(createLabel("Student Surname:"));
        leftPanel.add(studentSurnameField);
        leftPanel.add(createLabel("Student Department:"));
        leftPanel.add(studentDepartmentField);
        leftPanel.add(createLabel("Student Courses:"));
        leftPanel.add(studentCoursesField);
        leftPanel.add(new JLabel());
        leftPanel.add(saveButton);

        leftPanel.add(new JPanel()); // Empty panel for layout purposes

        add(leftPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(createLabel("Search:"));
        filterPanel.add(filterTextField);

        add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"Student Number", "Student Name", "Student Surname", "Student Department", "Student Courses", "Student Course Code"};
        tableModel.setColumnIdentifiers(columnNames);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validationCheck()) {
                    saveStudent();
                    refreshStudentTable();
                    JOptionPane.showMessageDialog(null, "Student successfully saved!");
                }
            }
        });

        refreshStudentTable();
    }

    private List<String> readCourses() {
        List<String> coursesList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader("courses.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject courseJson = (JSONObject) jsonParser.parse(line);
                String courseCode = String.valueOf(courseJson.get("CourseCode"));
                String courseName = (String) courseJson.get("CourseName");
                String combinedCourse = courseCode + " - " + courseName;
                coursesList.add(combinedCourse);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return coursesList;
    }

    private JButton createStyledButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(180, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBackground(new Color(70, 130, 180)); // Steel Blue color
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("Arial", Font.BOLD, 14));
        return comboBox;
    }

    private boolean validationCheck() {
        try {
            int studentNumber = Integer.parseInt(studentNumberField.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Only numeric input is allowed in the Student Number field!");
            return false;
        }
    }

    private void saveStudent() {
        String selectedCourse = (String) studentCoursesField.getSelectedItem();
        if (selectedCourse == null) {
            return;
        }
        String[] parts = selectedCourse.split(" - ");
        String courseCode = parts[0];
        String courseName = parts[1];

        Student student = new Student(
                Integer.parseInt(studentNumberField.getText()),
                studentNameField.getText(),
                studentSurnameField.getText(),
                studentDepartmentField.getText(),
                courseName,
                courseCode
        );

        JSONObject studentJson = new JSONObject();
        studentJson.put("studentNumber", student.getStudentNumber());
        studentJson.put("studentName", student.getStudentName());
        studentJson.put("studentSurname", student.getStudentSurname());
        studentJson.put("studentDepartment", student.getStudentDepartment());
        studentJson.put("studentCourses", student.getStudentCourses());
        studentJson.put("studentCourseCode", student.getStudentCourseCode());

        printStudent(studentJson);
    }

    private void refreshStudentTable() {
        tableModel.setRowCount(0);
        List<Student> students = readStudents("students.json");
        for (Student student : students) {
            Object[] row = {
                    student.getStudentNumber(),
                    student.getStudentName(),
                    student.getStudentSurname(),
                    student.getStudentDepartment(),
                    student.getStudentCourses(),
                    student.getStudentCourseCode()
            };
            tableModel.addRow(row);
        }
    }

    private List<Student> readStudents(String fileName) {
        List<Student> students = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject studentJson = (JSONObject) jsonParser.parse(line);
                Student student = new Student(
                        Integer.parseInt(studentJson.get("studentNumber").toString()),
                        (String) studentJson.get("studentName"),
                        (String) studentJson.get("studentSurname"),
                        (String) studentJson.get("studentDepartment"),
                        (String) studentJson.get("studentCourses"),
                        (String) studentJson.get("studentCourseCode")
                );
                students.add(student);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return students;
    }

    private void printStudent(JSONObject studentJson) {
        try (FileWriter file = new FileWriter("students.json", true)) {
            file.write(studentJson.toJSONString() + "\n");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterStudentTable() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);

        String text = filterTextField.getText().toLowerCase();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(new EmptyBorder(5, 5, 5, 10));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentForm studentForm = new StudentForm();
                studentForm.setLocationRelativeTo(null);
                studentForm.setVisible(true);
            }
        });
    }
}
