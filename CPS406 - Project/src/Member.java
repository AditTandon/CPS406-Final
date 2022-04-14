public class Member extends User {

    private String memberId;
    private String password;
    private String email;
    private int classAmount;
    private String coach;
    private Payment pay;
    private String dates;
    private String fee;

    public Member(String name, String memberId, String password, String email) {
        super(name, email);
        this.memberId = memberId;
        this.password = password;
        this.classAmount = 0;
        this.coach = "";
        this.dates = "";
        this.fee = "";
    }

    public Member(String name, String email, String memberId, String coach, int classAmount) {
        super(name, email);
        this.memberId = memberId;
        this.coach = coach;
        this.classAmount = classAmount;
    }

    public Member() {

    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getClassAmount() {
        return classAmount;
    }

    public String getCoach() {
        return coach;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public void createDate(String dates) {
        this.dates += dates;
    }

    public Payment getPay() {
        return pay;
    }

    public void setPay(Payment pay) {
        this.pay = pay;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setClassAmount(int classAmount) {
        this.classAmount = classAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
