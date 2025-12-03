import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {

    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject "
                        + (i + 1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        double sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return sum / marks.length;
    }

    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);

        System.out.print("Marks: ");
        for (int m : marks) {
            System.out.print(m + " ");
        }
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 40)
            System.out.println("Result: Pass");
        else
            System.out.println("Result: Fail");
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

public class Main2 {

    private Student[] students = new Student[50];
    private int count = 0;

    Scanner sc = new Scanner(System.in);

    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();

            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    System.out.println("Error: Roll number already exists.");
                    return;
                }
            }
            sc.nextLine(); 
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();


            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            Student s = new Student(roll, name, marks);
            s.validateMarks();

            students[count++] = s;
            System.out.println("Student added successfully.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();

            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input.");
            sc.nextLine();
        } finally {
            System.out.println("Search completed.");
        }
    }

    public void mainMenu() {
        int choice = 0;

        while (true) {
            System.out.println("\n===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");

            try {
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        showStudentDetails();
                        break;
                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Enter numbers only.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Main2 rm = new Main2();
        rm.mainMenu();
    }
}
