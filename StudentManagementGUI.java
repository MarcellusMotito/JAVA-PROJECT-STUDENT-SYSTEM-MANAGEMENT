import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentManagementGUI extends Application {
    private StudentManagementSystem sms = new StudentManagementSystem();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        grid.setHgap(10);

        // Input fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label courseLabel = new Label("Course:");
        TextField courseField = new TextField();
        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();

        // Buttons
        Button addButton = new Button("Add Student");
        Button updateButton = new Button("Update Student");
        Button deleteButton = new Button("Delete Student");
        Button displayButton = new Button("Display Students");
        Button exitButton = new Button("Exit");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        // Add components to grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(idLabel, 0, 1);
        grid.add(idField, 1, 1);
        grid.add(courseLabel, 0, 2);
        grid.add(courseField, 1, 2);
        grid.add(gradeLabel, 0, 3);
        grid.add(gradeField, 1, 3);

        grid.add(addButton, 0, 4);
        grid.add(updateButton, 1, 4);
        grid.add(deleteButton, 0, 5);
        grid.add(displayButton, 1, 5);
        grid.add(exitButton, 0, 6);
        grid.add(outputArea, 0, 7, 2, 1);

        // Button actions
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String id = idField.getText().trim();
                String course = courseField.getText().trim();
                String gradeText = gradeField.getText().trim();

                if (name.isEmpty() || id.isEmpty() || course.isEmpty() || gradeText.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
                if (!name.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Name must contain only letters and spaces.");
                }
                if (!id.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("ID must be alphanumeric.");
                }
                if (!course.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Course must contain only letters and spaces.");
                }
                double grade = Double.parseDouble(gradeText);
                if (grade < 0 || grade > 100) {
                    throw new IllegalArgumentException("Grade must be between 0 and 100.");
                }

                sms.addStudent(name, id, course, grade);
                outputArea.setText("Student added successfully!");
                clearFields(nameField, idField, courseField, gradeField);
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Grade must be a valid number.");
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });

        updateButton.setOnAction(e -> {
            try {
                String id = idField.getText().trim();
                if (id.isEmpty()) {
                    throw new IllegalArgumentException("ID must be provided for update.");
                }
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();
                String gradeText = gradeField.getText().trim();

                Double grade = null;
                if (!gradeText.isEmpty()) {
                    grade = Double.parseDouble(gradeText);
                    if (grade < 0 || grade > 100) {
                        throw new IllegalArgumentException("Grade must be between 0 and 100.");
                    }
                }

                sms.updateStudent(id,
                        name.isEmpty() ? null : name,
                        course.isEmpty() ? null : course,
                        grade == null ? -1 : grade);

                outputArea.setText("Student updated successfully!");
                clearFields(nameField, idField, courseField, gradeField);
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Grade must be a valid number.");
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                String id = idField.getText().trim();
                if (id.isEmpty()) {
                    throw new IllegalArgumentException("ID must be provided for deletion.");
                }
                sms.deleteStudent(id);
                outputArea.setText("Student deleted successfully!");
                clearFields(nameField, idField, courseField, gradeField);
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });

        displayButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sms.getStudents().forEach(s -> sb.append(s.toString()).append("\n"));
            outputArea.setText(sb.length() == 0 ? "No students registered." : sb.toString());
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        // Set scene and show
        Scene scene = new Scene(grid, 450, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
