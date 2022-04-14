import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Coach extends User {

    private String employeeId;
    private String password;
    private String fee;

    public Coach(String name, String email, String id, String password, String fee) {
        super(name, email);
        this.employeeId = id;
        this.password = password;
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Coach(String id, String password) {
        this.employeeId = id;
        this.password = password;
    }

    public Coach() {

    }

    /*
    Reads users.csv file line by line
    Each line split on the comma is stored into an array
    If the user's coach matches this.coach's name, that user's name is added to an arraylist
    The arraylist is returned
     */
    public ArrayList<String> viewMembers() throws IOException {
        FileReader fr = new FileReader("users.csv");
        BufferedReader br = new BufferedReader(fr);

        String line;

        ArrayList<String> users = new ArrayList<String>();

        while ((line = br.readLine()) != null) {
            String[] info = line.split(",");
            if (info[4].equals(this.getName())) {
                users.add(info[0]);
            }
        }

        fr.close();
        br.close();

        return users;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
