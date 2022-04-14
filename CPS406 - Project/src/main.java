import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner input = new Scanner(System.in);

        Member member = new Member();

        System.out.println("****** Welcome to Salsa Club ******\n");

        while (true) {

            System.out.println("Member Create Account (1), Member Login (2), Staff Login (3) or Quit (4)");
            int choice = input.nextInt();
            if (choice == 1) {
                AccountCreation acc = new AccountCreation();
                member = acc.create();
            }
            else if (choice == 2) {
                input = new Scanner(System.in);
                System.out.println("Enter your email: ");
                String email = input.nextLine();

                input = new Scanner(System.in);

                System.out.println("Enter your password: ");
                String pass = input.nextLine();

                boolean success = login(member, email, pass);

                if (!success) {
                    System.out.println("Invalid Email/Password. Try again");
                }
                else {
                    LoginScreen log = new LoginScreen(member);
                    log.displayMenu();
                }

            }
            else if (choice == 3) {
                input = new Scanner(System.in);

                System.out.println("Treasurer (1) or Coach (2): ");
                int selection = input.nextInt();

                input = new Scanner(System.in);

                if (selection == 2) {
                    System.out.println("Enter your employee ID: ");
                    String id = input.nextLine();

                    System.out.println("Enter your password: ");
                    String password = input.nextLine();

                    Coach coach = coachLogin(id, password);

                    if (coach == null) {
                        System.out.println("Invalid Coach Login");
                    }
                    else {
                        CoachScreen screen = new CoachScreen(coach);
                        screen.displayMenu();
                    }
                }
                else if (selection == 1) {
                    System.out.println("Enter the treasurer email: ");
                    String treEmail = input.nextLine();

                    System.out.println("Enter your password: ");
                    String trePass = input.nextLine();

                    if (treEmail.equals("treasurer@salsa.ca") && trePass.equals("admin1234")) {
                        TreasurerLogin log = new TreasurerLogin();
                        log.displayMenu();
                    }
                    else {
                        System.out.println("Invalid treasurer login");
                    }
                }


            }
            else if (choice == 4) {
                System.out.println("Goodbye.");
                break;
            }
        }

    }

    /*
    Reads the coaches.csv file line by line in the while loop
    Each line is stored in an array called String[] info
    If the passed arguments (id and password) equal one of the lines of the file,
    it will create a new Coach object with the values from that line and return it
     */
    private static Coach coachLogin(String id, String password) throws IOException {

        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info[2].equals(id) && info[3].equals(password)) {
                br.close();
                fr.close();
                return new Coach(info[0], info[1], info[2], info[3], info[4]);
            }
        }
        br.close();
        fr.close();
        return null;
    }


    /*
    Check if successful login
    Reads the users.csv file line by line in the while loop
    Each line is stored in an array called String[] info
    If the email and pass match a line's information, memberLogin() method is called
    to set the Member object properties
    Return true if valid email and pass
     */
    public static boolean login(Member member, String email, String pass) throws IOException {
        FileReader file = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(file);

        String line = "";

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String userEmail = info[1];
            String userPass = info[2];

            if (email.equals(userEmail) && pass.equals(userPass)) {
                memberLogin(member, userEmail);
                br.close();
                file.close();
                return true;
            }
        }

        br.close();
        file.close();

        return false;
    }

    /*
    Sets the Member object properties using the appropriate values on the correct line
    in the users.csv files
     */
    private static void memberLogin(Member member, String email) throws IOException {
        FileReader file = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(file);

        String line = "";

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (email.equals(info[1])) {
                member.setName(info[0]);
                member.setEmail(info[1]);
                member.setPassword(info[2]);
                member.setMemberId(info[3]);
                member.setCoach(info[4]);
                member.setDates(info[6]);
                member.setClassAmount(7);
            }
        }

        file.close();
        br.close();
    }
}
