package me.jtrenaud1s.cis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private HashMap<Integer, Student> students;
    private boolean running;
    private Screen currentScreen;
    private Scanner scanner;


    public Main(){
        students = new HashMap<>();
        running = false;
        currentScreen = Screen.MAIN;
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws InterruptedException {
        new Main().start();
    }

    public void start() throws InterruptedException {
        clearScreen();
        System.out.println("Welcome to jtrenaud1s's student management program!");
        Thread.sleep(3000L);
        clearScreen();

        StringBuilder main = new StringBuilder();
        main.append("1. View Students").append("\n").append("2. Exit").append("\n").append("Choose an option: ");

        StringBuilder viewList = new StringBuilder().append("1. View Student").append("\n").append("2. Add Student").append("\n").append("3. Go Back").append("\n").append("Choose an option: ");
        StringBuilder viewStudent = new StringBuilder().append("1. Add Grade").append("\n").append("2. Delete Grade").append("\n").append("3. Delete Student").append("\n").append("4. Go Back").append("\n").append("Choose an option: ");
        running = true;

        int currentStudent = 0;
        String input = "";
        while(running) {
            clearScreen();
            if(currentScreen == Screen.MAIN) {
                System.out.println(main);
                input = read();

                if(input.equals("2")) {
                    running = false;
                } else if(input.equals("1")) {
                    currentScreen = Screen.STUDENT_LIST;
                    continue;
                }
            } else if(currentScreen == Screen.STUDENT_LIST) {
                Student s = null;
                if(students.size() == 0) {
                    System.out.println("There are no students!");
                }
                for (Map.Entry<Integer, Student> entry : students.entrySet()) {
                    StringBuilder student = new StringBuilder();
                    s = entry.getValue();
                    student.append(entry.getKey()).append(". ").append(s.getLastname()).append(", ").append(s.getFirstname()).append(" ").append(s.getMiddlename());
                    System.out.println(student);
                }
                System.out.println();
                System.out.println(students.size() > 0 ? viewList : viewList.toString().replace("1. View Student\n", ""));
                input = read();
                if(input.equals("1")) {
                    System.out.println("Enter the Student ID: ");
                    input = read();
                    currentScreen = Screen.VIEW_STUDENT;
                    continue;
                } else if(input.equals("2")) {
                    currentScreen = Screen.ADD_STUDENT;
                    continue;
                } else if(input.equals("3")) {
                    currentScreen = Screen.MAIN;
                    continue;
                }
            } else if(currentScreen == Screen.VIEW_STUDENT) {
                currentStudent = Integer.parseInt(input);
                Student st = students.get(currentStudent);
                Grade g = null;
                System.out.println("Grade Report for " + st.getFirstname() + " " + st.getLastname());
                for (Map.Entry<Integer, Grade> entry : st.getGrades().entrySet()) {
                    StringBuilder grade = new StringBuilder();
                    g = entry.getValue();
                    grade.append(entry.getKey()).append(". ").append(g.getCourse()).append("\t").append(g.getPercentage()).append("% - ").append(g.getLetterGrade());
                    System.out.println(grade);
                }
                System.out.println();
                System.out.println(viewStudent);
                input = read();
                if(input.equals("1")) {
                    currentScreen = Screen.ADD_GRADE;
                    continue;
                } else if(input.equals("2")) {
                    System.out.println("Enter the Grade ID: ");
                    input = read();
                    currentScreen = Screen.DELETE_GRADE;
                    continue;
                } else if(input.equals("3")) {
                    currentScreen = Screen.DELETE_STUDENT;
                    continue;
                } else if(input.equals("4")) {
                    currentScreen = Screen.STUDENT_LIST;
                }
            } else if(currentScreen == Screen.ADD_GRADE) {
                Student st = students.get(currentStudent);
                System.out.println("Enter the course name (EX CS280): ");
                String course = read();
                System.out.println("Enter the percentage for the grade: ");
                double percent = Double.parseDouble(read());

                Grade grade = new Grade(percent, course);

                st.getGrades().put(nextAvailableID(st.getGrades()), grade);
                System.out.println("Grade Added");
                Thread.sleep(2000L);
                currentScreen = Screen.VIEW_STUDENT;
                input = currentStudent + "";
                continue;
            } else if(currentScreen == Screen.DELETE_GRADE) {
                Student st = students.get(currentStudent);
                int gradeToDelete = Integer.parseInt(input);
                System.out.println("Are you sure you want to delete grade " + input + "? (Y/N)");
                input = read();
                if (input.equalsIgnoreCase("Y")) {
                    st.getGrades().remove(gradeToDelete);
                }
                currentScreen = Screen.VIEW_STUDENT;
                input = currentStudent + "";
            } else if(currentScreen == Screen.DELETE_STUDENT) {
                Student st = students.get(currentStudent);
                System.out.println("Are you sure you want to delete " + st.getFirstname() + " from the gradebook? (Y/N)");
                input = read();
                if (input.equalsIgnoreCase("Y")) {
                    students.remove(currentStudent);
                    currentStudent = 0;
                    currentScreen = Screen.STUDENT_LIST;
                    continue;
                }
                currentScreen = Screen.VIEW_STUDENT;
                input = currentStudent + "";
                continue;
            } else if(currentScreen == Screen.ADD_STUDENT) {
                System.out.println("Enter the student's full name (First Middle Last): ");
                String[] name = read().split(" ");
                Student s = new Student(name[0], name[2], name[1]);
                int nextAvailableID = nextAvailableID(students);
                students.put(nextAvailableID, s);
                input = nextAvailableID + "";
                currentScreen = Screen.VIEW_STUDENT;
                continue;
            }
        }
    }

    private String read() {
        return scanner.nextLine().trim();
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                pb.inheritIO();

                Process p = pb.start();
                p.waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private int nextAvailableID(HashMap<Integer, ?> map) {
        int i = 0;
        while(map.containsKey(i)) {
            i++;
        }
        return i;
    }
}
