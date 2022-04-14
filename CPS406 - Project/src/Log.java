import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Log {

    private ArrayList<Member> members;

    public Log() {
        this.members = new ArrayList<Member>();
    }

    /*
    Reads the users.csv file line by line
    Each line is stored into an array split on the comma
    A member object is constructed using the array elements as the properties
    and the member is added to the ArrayList
     */
    public void readMembers() throws IOException {
        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            Member member = new Member(info[0],info[1],info[3],info[4],Integer.parseInt(info[7]));
            this.members.add(member);
        }

        fr.close();
        br.close();
    }

    public void displayMembers() {
        System.out.printf("%-20s%-30s%-30s%-30s%-5s\n", "Name", "Email", "Member ID", "Coach", "Number of Classes");

        for (int i = 0; i < 150; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (Member m : this.members) {
            System.out.printf("%-20s%-30s%-30s%-30s%7d\n", m.getName(), m.getEmail(), m.getMemberId(), m.getCoach(), m.getClassAmount());
        }
        System.out.println("\n\n\n");
    }

    /*
    Loops through the ArrayList of member objects and finds the inputted name
    Changes that member's classAmount to the amount inputted
     */
    public void modifyClassAmount() throws InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the member's name: ");
        String name = input.nextLine();

        System.out.println("How many classes have they attended?");
        int amt = input.nextInt();

        System.out.println("Modifying class amount...");
        Thread.sleep(3000);
        System.out.println("Updated members: ");

        for (Member m : this.members) {
            if (name.equals(m.getName())) {
                m.setClassAmount(m.getClassAmount() - amt);
                break;
            }
        }

        System.out.println("\n");
        displayMembers();

    }
}
