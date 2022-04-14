import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CoachScreen {

    private Coach coach;

    public CoachScreen(Coach coach) {
        this.coach = coach;
    }

    public void displayMenu() throws InterruptedException, IOException {

        Scanner input = new Scanner(System.in);

        System.out.println("------ Welcome " + coach.getName() + " ------");

        while (true) {
            System.out.println("Send message to all members (1), some members (2) or exit (3): ");
            int selection = input.nextInt();

            if (selection == 1) {
                sendAll();
            }
            else if (selection == 2) {
                sendSome();
            }
            else if (selection == 3) {
                break;
            }
        }
    }

    /*
    Simulates sending a message to all members of the club
     */
    private void sendAll() throws InterruptedException {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your message: ");
        String message = input.nextLine();

        System.out.println("Sending message...");
        Thread.sleep(2000);
        System.out.println("...Message sent.");
    }

    /*
        Gets the coach's users through the viewMembers() method
        Simulates sending a message to a specific member
     */
    private void sendSome() throws IOException, InterruptedException {

        ArrayList<String> users = this.coach.viewMembers();
        int counter = 1;

        Scanner input = new Scanner(System.in);

        System.out.println("\nYour registered members: ");
        for (String u : users) {
            System.out.println(counter + ". " + u);
            counter += 1;
        }

        System.out.println("\nEnter the user you want to send the message to: ");
        String user = input.nextLine();

        System.out.println("Enter your message: ");
        String msg = input.nextLine();

        System.out.println("Sending message to " + user + "...");
        Thread.sleep(2000);
        System.out.println("...Message sent.");
    }
}
