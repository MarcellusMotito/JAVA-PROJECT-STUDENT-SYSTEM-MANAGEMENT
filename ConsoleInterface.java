import java.util.Scanner;

public class ConsoleInterface {
    private final StudentManagementSystem sms;
    private final Scanner scanner;

    public ConsoleInterface(StudentManagementSystem sms) {
        this.sms = sms;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    sms.displayStudents();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addStudent() {
        while (true) {
            try {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                if (name.trim().isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Name must contain only letters and spaces and cannot be empty.");
                }

                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                if (id.trim().isEmpty() || !id.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("ID must be alphanumeric and cannot be empty.");
                }

                System.out.print("Enter course: ");
                String course = scanner.nextLine();
                if (course.trim().isEmpty() || !course.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Course must contain only letters and spaces and cannot be empty.");
                }

                System.out.print("Enter grade (0-100): ");
                String gradeInput = scanner.nextLine();
                double grade;
                try {
                    grade = Double.parseDouble(gradeInput);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Grade must be a valid number.");
                }
                if (grade < 0 || grade > 100) {
                    throw new IllegalArgumentException("Grade must be between 0 and 100.");
                }

                sms.addStudent(name, id, course, grade);
                System.out.println("Student added successfully.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void updateStudent() {
        while (true) {
            try {
                System.out.print("Enter student ID to update: ");
                String id = scanner.nextLine();
                if (id.trim().isEmpty() || !id.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("ID must be alphanumeric and cannot be empty.");
                }

                System.out.print("Enter new name (or press Enter to skip): ");
                String name = scanner.nextLine();
                if (!name.isEmpty() && !name.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Name must contain only letters and spaces.");
                }

                System.out.print("Enter new course (or press Enter to skip): ");
                String course = scanner.nextLine();
                if (!course.isEmpty() && !course.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Course must contain only letters and spaces.");
                }

                System.out.print("Enter new grade (or -1 to skip): ");
                String gradeInput = scanner.nextLine();
                double grade = -1;
                if (!gradeInput.isEmpty()) {
                    try {
                        grade = Double.parseDouble(gradeInput);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Grade must be a valid number.");
                    }
                    if (grade < 0 && grade != -1 || grade > 100) {
                        throw new IllegalArgumentException("Grade must be between 0 and 100 or -1 to skip.");
                    }
                }

                sms.updateStudent(id,
                        name.isEmpty() ? null : name,
                        course.isEmpty() ? null : course,
                        grade);

                System.out.println("Student updated successfully.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void deleteStudent() {
        while (true) {
            try {
                System.out.print("Enter student ID to delete: ");
                String id = scanner.nextLine();
                if (id.trim().isEmpty() || !id.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("ID must be alphanumeric and cannot be empty.");
                }
                sms.deleteStudent(id);
                System.out.println("Student deleted successfully.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
