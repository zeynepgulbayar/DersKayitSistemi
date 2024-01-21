
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
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

class Course {
    private final int courseCode;
    private final String courseName;
    private final int courseSemester;
    private final String lecturer;

    public Course(int courseCode, String courseName, int courseSemester, String lecturer) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.lecturer = lecturer;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseSemester() {
        return courseSemester;
    }

    public String getLecturer() {
        return lecturer;
    }
}

public class CourseForm extends JFrame {
    private final JTextField courseCodeField;
    private final JTextField courseNameField;
    private final JTextField courseSemesterField;
    private final JComboBox<String> lecturerComboBox;
    private final JButton saveButton;
    private final JTable courseTable;
    private final JTextField filterTextField;

    private final DefaultTableModel tableModel;

    public CourseForm() {
        setTitle("Course Form");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 2));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setResizable(false);

        courseCodeField = new JTextField();
        courseNameField = new JTextField();
        courseSemesterField = new JTextField();
        lecturerComboBox = new JComboBox<>();
        saveButton = createStyledButton("Save");

        tableModel = new DefaultTableModel();
        courseTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(courseTable);

        filterTextField = new JTextField();
        filterTextField.setColumns(20);
        filterTextField.setBorder(new EmptyBorder(5, 10, 5, 10));
        filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterCourseTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterCourseTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events.
            }
        });


        //        filterTextField.addActionListener(e -> filterCourseTable());

        leftPanel.add(createLabel("Course Code:"));
        leftPanel.add(courseCodeField);
        leftPanel.add(createLabel("Course Name:"));
        leftPanel.add(courseNameField);
        leftPanel.add(createLabel("Semester:"));
        leftPanel.add(courseSemesterField);
        leftPanel.add(createLabel("Lecturer:"));
        leftPanel.add(lecturerComboBox);
        leftPanel.add(new JLabel());
        leftPanel.add(saveButton);

        leftPanel.add(new JPanel()); // Empty panel for layout preservation

        add(leftPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.add(createLabel("Search:"));
        filterPanel.add(filterTextField);

        add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {"Course Code", "Course Name", "Semester", "Lecturer"};
        tableModel.setColumnIdentifiers(columnNames);

        List<String> lecturers = readLecturers("Lecturers.json");
        for (String lecturer : lecturers) {
            lecturerComboBox.addItem(lecturer);
        }

        saveButton.addActionListener(e -> {
            if (validationCheck()) {
                saveCourse();
                refreshCourseTable();
                JOptionPane.showMessageDialog(null, "Course successfully saved!");
            }
        });

        refreshCourseTable();
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

    private boolean validationCheck() {
        try {
            int courseCode = Integer.parseInt(courseCodeField.getText());
            int courseSemester = Integer.parseInt(courseSemesterField.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Course Code and Semester fields can only have numeric input!");
            return false;
        }
    }

    private void saveCourse() {
        JSONObject courseJson = new JSONObject();
        courseJson.put("CourseCode", Integer.parseInt(courseCodeField.getText()));
        courseJson.put("CourseName", courseNameField.getText());
        courseJson.put("Semester", Integer.parseInt(courseSemesterField.getText()));
        courseJson.put("Lecturer", lecturerComboBox.getSelectedItem());

        writeCourse(courseJson);
    }

    private List<String> readLecturers(String fileName) {
        List<String> lecturers = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            JSONArray lecturersArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : lecturersArray) {
                JSONObject lecturerObj = (JSONObject) obj;
                String fullName = lecturerObj.get("Name") + " " + lecturerObj.get("Surname");
                lecturers.add(fullName);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return lecturers;
    }

    private void writeCourse(JSONObject courseJson) {
        try (FileWriter file = new FileWriter("courses.json", true)) {
            file.write(courseJson.toJSONString() + "\n");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshCourseTable() {
        tableModel.setRowCount(0);
        List<Course> courses = readCourses("courses.json");
        for (Course course : courses) {
            Object[] row = {
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getCourseSemester(),
                    course.getLecturer()
            };
            tableModel.addRow(row);
        }
    }

    private List<Course> readCourses(String fileName) {
        List<Course> courses = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject courseJson = (JSONObject) jsonParser.parse(line);

                int courseCode = Integer.parseInt(courseJson.get("CourseCode").toString());
                String courseName = (String) courseJson.get("CourseName");

                int courseSemester;
                try {
                    courseSemester = Integer.parseInt(courseJson.get("Semester").toString());
                } catch (NumberFormatException | NullPointerException e) {
                    courseSemester = 0; // Set a default value or handle as appropriate
                }

                String lecturer = courseJson.get("Lecturer") != null ? courseJson.get("Lecturer").toString() : "";

                Course course = new Course(courseCode, courseName, courseSemester, lecturer);
                courses.add(course);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private void filterCourseTable() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        courseTable.setRowSorter(sorter);

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
        SwingUtilities.invokeLater(() -> {
            CourseForm courseForm = new CourseForm();
            courseForm.setLocationRelativeTo(null); // Center the frame on the screen
            courseForm.setVisible(true);
        });
    }
}
