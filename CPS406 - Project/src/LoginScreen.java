import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoginScreen {

    private Member member;

    public LoginScreen(Member member) {
        this.member = member;
    }

    public void displayMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n\n------ Welcome " + this.member.getName() + " ------");
            System.out.println("Choose your option: ");
            System.out.println("Display your info (1)\nPay (2)\nQuit (3)");
            int choice = input.nextInt();

            if (choice == 1) {
                displayInfo();
            }
            else if (choice == 2) {
                System.out.println("Enter your new payment info: ");
                input = new Scanner(System.in);
                String payment = input.nextLine();
            }
            else if (choice == 3) {
                break;
            }
        }


    }

    /*
    Displays the member's information using the member object
     */
    public void displayInfo() throws IOException {
        System.out.println("\n\n------Your Info------");
        System.out.println("Your Coach: " + this.member.getCoach());
        System.out.printf("Your hourly fee: $%s.00\n", this.getCoachFee(this.getMember().getCoach()));
        System.out.println("Your Member Id: " + this.member.getMemberId());
        System.out.println("Your dates: ");
        displayDates();
    }

    public void displayDates() {
        String dates = this.member.getDates();
        String singleDate = "";

        for (int i = 0; i < dates.length(); i++) {
            if (i % 10 == 0 && i != 0) {
                System.out.println("\t" + singleDate);
                singleDate = "0";
            }
            else {
                singleDate += dates.charAt(i);
            }
        }

        System.out.println("\t" + singleDate + "\n");

    }

    public String getCoachFee(String name) throws IOException {
        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;
        String fee = "";

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (name.equals(info[0])) {
                fee = info[4];
                break;
            }
        }

        fr.close();
        br.close();

        return fee;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
