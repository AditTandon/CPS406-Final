import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class TreasurerLogin {

    private Treasurer treasurer;

    public TreasurerLogin() {

    }

    public void displayMenu() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n------ Welcome Treasurer ------");



        while (true) {
            System.out.println("\nTreasurer Menu");
            System.out.println("\nChoose an option.");
            System.out.println("Change Coaches (1) or Change Members (2) or Track Club Finances (3) or Member Options (4) " +
                    "or Quit (5)");
            int choice = input.nextInt();
            if (choice == 1) {
                changeCoaches();
            }
            else if (choice == 2) {
                changeUsers();
            }
            else if (choice == 3) {
                input = new Scanner(System.in);

                System.out.println("\nEnter year: ");
                String year = input.nextLine();

                System.out.println("\nEnter month: ");
                String month = input.nextLine();

                treasurer = new Treasurer("George Smith", "george@salsa.ca",
                        month, year);
                this.userIncome();
                this.coachExpense();
                System.out.println("\nView Log List (1) or Change Member Payment Status (2)");
                int logChoice = input.nextInt();

                if (logChoice == 1) {
                    System.out.println(this.treasurer.getLoggingList(""));
                }
                else if (logChoice == 2) {
                    input = new Scanner(System.in);
                    System.out.println("\nWhich member has paid? ");
                    String paidName = input.nextLine();

                    System.out.println("Updating pay status...");
                    Thread.sleep(1000);
                    System.out.println(this.treasurer.getLoggingList(paidName));
                    System.out.println("\n\n");
                }
            }
            else if (choice == 4) {
                Log log = new Log();
                log.readMembers();
                System.out.println("View Members (1) or Change Class Amount (2)");
                int c = input.nextInt();

                if (c == 1) {
                    log.displayMembers();
                }
                else if (c == 2) {
                    log.modifyClassAmount();
                }
            }
            else if (choice == 5) {
                break;
            }
        }


    }

    /*
    Adds each user's name and their fees that need to be paid to the
    incomeList HashMap in the treasurer object through the addIncome()
    method
     */
    public void userIncome() throws IOException {
        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String info[] = line.split(",");
            String name = info[0];
            String fee = info[8];
            this.treasurer.addIncome(name, Double.parseDouble(fee));
        }

        br.close();
        fr.close();
    }

    /*
    Adds each coach's name and their fees that they charge to the
    expenseList HashMap in the treasurer object through the addExpense()
    method
     */
    public void coachExpense() throws IOException {
        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String name = info[0];
            String coachFee = info[4];
            this.treasurer.addExpense(name, Double.parseDouble(coachFee));
        }

        fr.close();
        br.close();
    }

    /*
    This method allows the treasurer to change a coach to another coach
    The coaches names are displayed using the displayCoaches() method from below
    Information about the new coach is inputted

    To delete the old coach and add the new coach:
        - The coaches.csv file is read and the information is written to
          another temp file excluding the line of the coach that needs to be deleted
        - At the end of the temp file, the information about the new coach is added
        - The old coaches.csv file is deleted and the temp file is renamed to coaches.csv

    The users file is also read and the users who had the coach who was deleted is modified
    to the new coach using the same technique described above
     */
    public void changeCoaches() throws IOException {
        Scanner input = new Scanner(System.in);

        String coaches = displayCoaches();
        System.out.println("Here are the current coaches: ");
        System.out.println(coaches);

        System.out.println("Enter the name of the coach you'd like to change: ");
        String coachChange = input.nextLine();

        System.out.println("Enter new coach's name: ");
        String newName = input.nextLine();

        System.out.println("Enter new coach's email: ");
        String newEmail = input.nextLine();

        System.out.println("Enter new coach employee ID: ");
        String newId = input.nextLine();

        System.out.println("Enter new coach password: ");
        String newPass = input.nextLine();

        System.out.println("Enter the coach's fee: ");
        String newFee = input.nextLine();

        File file = new File("coaches.csv");
        File tempFile = new File("coachestemp.csv");

        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        // Transfer information from original coaches.csv file to new temp file
        // Exclude the line of the coach that needs to be deleted
        // New information of the new coach is entered at the bottom of the temp file
        // original file is deleted and temp file is renamed to coaches.csv
        try {
            PrintWriter output = new PrintWriter(new FileWriter(tempFile, true));
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                if (!info[0].equals(coachChange)) {
                    output.append(info[0]).append(",");
                    output.append(info[1]).append(",");
                    output.append(info[2]).append(",");
                    output.append(info[3]).append(',');
                    output.append(info[4]);
                    output.println();
                }
            }
            output.append(newName).append(",");
            output.append(newEmail).append(",");
            output.append(newId).append(",");
            output.append(newPass).append(",");
            output.append(newFee);
            output.println();

            output.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        fr.close();
        br.close();

        modifyUsers(coachChange, newName);


        file.delete();
        tempFile.renameTo(new File("coaches.csv"));

    }

    /*
    This method deletes a member
    The member's name who needs to be deleted is inputted

    The original users.csv file is read line by line
    All users except the line that matches the inputted name is transferred to
    a temp file
    The original users.csv file is deleted and temp file is renamed to users.csv
     */
    public void changeUsers() throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);

        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);


        System.out.println("Here are the current members: ");
        readMembers();


        System.out.println("Choose the name of the user you would like to delete: ");
        String name = input.nextLine();

        File file = new File("users.csv");
        File tempFile = new File("userstemp.csv");

        String secondLine;

        try {
            PrintWriter output = new PrintWriter(new FileWriter(tempFile, true));
            while ((secondLine = br.readLine()) != null) {
                String[] info = secondLine.split(",");
                if (!name.equals(info[0])) {
                    output.append(info[0]).append(",");
                    output.append(info[1]).append(",");
                    output.append(info[2]).append(",");
                    output.append(info[3]).append(",");
                    output.append(info[4]).append(",");
                    output.append(info[5]).append(",");
                    output.append(info[6]).append(",");
                    output.append(info[7]).append(",");
                    output.append(info[8]);
                    output.println();
                }
            }
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        fr.close();
        br.close();


        file.delete();
        tempFile.renameTo(new File("users.csv"));

        System.out.println("Deleting member...");
        Thread.sleep(2000);
        System.out.println("...Member deleted");
        System.out.println("\nUpdated Members:");
        readMembers();
        System.out.println("------------------------\n\n");

    }

    /*
    Reads the users.csv file line by line
    Each line's information is stored in an array which is split on the comma
    The name, member ID and email are saved to a string for each member and printed out
     */
    public void readMembers() throws IOException {
        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;
        int num = 1;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String name = info[0];
            String email = info[1];
            String memberId = info[3];

            String s1 = String.format("Member %d: \n\tName: %s\n\tEmail: %s", num, name, email);
            String s2 = String.format("\n\tMember ID: %s", memberId);

            System.out.println(s1 + s2);
            num += 1;
        }

        fr.close();
        br.close();
    }

    /*
    This method changes the coach of the user if the original coach is changed
    The deleted coach name and new coach name is passed into the method

    A new tempfile is created
    Each line from the original users.csv file is written into tempfile
        - If the deleted coach name matches the coach's name of the user, that coach's name
          is changed to the new one

    Original temp file is renamed to users.csv and the original one is deleted
     */
    public void modifyUsers(String name, String newName) throws IOException {
        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);

        File file = new File("users.csv");
        File tempFile = new File("userstemp.csv");

        String line;

        try {
            PrintWriter output = new PrintWriter(new FileWriter(tempFile, true));
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                if (!name.equals(info[4])) {
                    output.append(info[0]).append(",");
                    output.append(info[1]).append(",");
                    output.append(info[2]).append(",");
                    output.append(info[3]).append(",");
                    output.append(info[4]).append(",");
                    output.append(info[5]).append(",");
                    output.append(info[6]).append(",");
                    output.append(info[7]).append(",");
                    output.append(info[8]);
                    output.println();
                }
                else {
                    output.append(info[0]).append(",");
                    output.append(info[1]).append(",");
                    output.append(info[2]).append(",");
                    output.append(info[3]).append(",");
                    output.append(newName).append(",");
                    output.append(info[5]).append(",");
                    output.append(info[6]).append(",");
                    output.append(info[7]).append(",");
                    output.append(info[8]);
                    output.println();
                }
            }
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        fr.close();
        br.close();


        file.delete();
        tempFile.renameTo(new File("users.csv"));
    }

    /*
    Reads the coaches.csv file line by line
    Each value in the line is split on the comma and stored in the info array
    Stores the name of each coach (the first value in the array) to a string
    String with all coaches name is returned
     */
    private String displayCoaches() throws IOException {
        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String coaches = "";
        int counter = 1;
        String line;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            String name = info[0];
            coaches += counter + ": " + name + "\n";
            counter++;
        }

        fr.close();
        br.close();

        return coaches;
    }
}
