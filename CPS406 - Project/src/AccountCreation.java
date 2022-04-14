import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class AccountCreation {

    private Member member;

    public AccountCreation() {
    }

    /*
    Information for each member is asked and inputted
     */
    public Member create() throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println("\n\t------ Account Registration ------\n");

        System.out.println("Enter your name: ");
        String name = input.nextLine();

        System.out.println("Enter your email: ");
        String email = input.nextLine();

        System.out.println("Enter your password: ");
        String password = input.nextLine();

        String memberId = generateId();

        this.member = new Member(name, memberId, password, email);

        System.out.println("\n------ Welcome " + this.member.getName() + " ------");

        System.out.println("Enter the number of classes (1 per week): ");
        int num = input.nextInt();

        this.member.setClassAmount(num);

        input = new Scanner(System.in);

        System.out.println("Choose your coach.");
        System.out.println(displayCoaches());
        System.out.println("Enter the coach's name: ");
        String chooseCoach = input.nextLine();

        this.member.setCoach(chooseCoach);

        String fee = this.getCoachFee(chooseCoach);
        this.member.setFee(fee);

        System.out.println("\nSelect " + num + " dates: ");
        System.out.println(dateSelection(num));

        int counter = 0;
        System.out.println("Choose from the following payments for your payment type: ");
        for (Payment pay : Payment.values()) {
            System.out.println(counter + ". " + pay);
            counter += 1;
        }

        String payment = input.nextLine();

        if (payment.equalsIgnoreCase("visa")) {
            this.member.setPay(Payment.VISA);
        }
        else if (payment.equalsIgnoreCase("amex")) {
            this.member.setPay(Payment.AMEX);
        }
        else if (payment.equalsIgnoreCase("debit")) {
            this.member.setPay(Payment.DEBIT);
        }
        else {
            this.member.setPay(Payment.MASTERCARD);
        }

        System.out.println("\nEnter payment information: ");
        String paymentInfo = input.nextLine();

        if (createAccount()) {
            System.out.println("\nAccount Creation Successful\n");
            return this.member;
        }
        else {
            System.out.println("\nUnsuccessful Creation\n");
        }
        return null;
    }

    /*
    Opens the users.csv file
    Adds the details of this.member's properties to the file as one line with each property
    separated by a comma
     */
    public boolean createAccount() {
        boolean success = false;

        File file = new File("users.csv");

        try {
            PrintWriter output = new PrintWriter(new FileWriter(file, true));
            output.append(this.member.getName()).append(",");
            output.append(this.member.getEmail()).append(",");
            output.append(this.member.getPassword()).append(",");
            output.append(this.member.getMemberId()).append(",");
            output.append(this.member.getCoach()).append(",");
            output.append(this.member.getPay().toString()).append(",");
            output.append(this.member.getDates()).append(",");
            output.append(Integer.toString(this.member.getClassAmount())).append(",");
            output.append(this.member.getFee());
            output.println();

            output.close();

            success = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }

    /*
    Returns the coach's fee by reading the coaches.csv file by checking the specific coachName
     */
    public String getCoachFee(String coachName) throws IOException {
        FileReader fr = new FileReader("coaches.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        String fee = "";

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info[0].equals(coachName)) {
                fee = info[4];
                break;
            }
        }

        fr.close();
        br.close();

        return fee;
    }

    /*
    Generates a random ID of length 9
     */
    public String generateId() {
        String chars = "ABCDEFGHIJKLMNOPRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();

        Random rand = new Random();

        while (id.length() < 9) {
            int ind = (rand.nextInt(chars.length()));
            id.append(chars.charAt(ind));
        }

        return id.toString();
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
        br.close();
        fr.close();
        return coaches;
    }

    public String dateSelection(int num) {
        int x = 0;
        String dates = "";
        Scanner input = new Scanner(System.in);

        while (x < num) {
            System.out.println("Enter date #" + (x + 1) + " (MM-DD-YYYY): ");
            String dateIn = input.nextLine();
            this.member.createDate(dateIn);
            dates += dateIn + "\n";
            x += 1;
        }


        return "Your selected dates: \n" + dates + "\n";
    }
}
