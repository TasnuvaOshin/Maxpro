package cricketworldcup.worldcup.Admin.Model;

public class Quiz_Data {
    String qus_one;
    String qus_two;
    String qus_one_opt_one;
    String qus_one_opt_two;
    String qus_two_opt_one;
    String qus_two_opt_two;
    String matchno;

    public String getMatchno() {
        return matchno;
    }

    public void setMatchno(String matchno) {
        this.matchno = matchno;
    }

    public String getQus_one() {
        return qus_one;
    }

    public void setQus_one(String qus_one) {
        this.qus_one = qus_one;
    }

    public String getQus_two() {
        return qus_two;
    }

    public void setQus_two(String qus_two) {
        this.qus_two = qus_two;
    }

    public String getQus_one_opt_one() {
        return qus_one_opt_one;
    }

    public void setQus_one_opt_one(String qus_one_opt_one) {
        this.qus_one_opt_one = qus_one_opt_one;
    }

    public String getQus_one_opt_two() {
        return qus_one_opt_two;
    }

    public void setQus_one_opt_two(String qus_one_opt_two) {
        this.qus_one_opt_two = qus_one_opt_two;
    }

    public String getQus_two_opt_one() {
        return qus_two_opt_one;
    }

    public void setQus_two_opt_one(String qus_two_opt_one) {
        this.qus_two_opt_one = qus_two_opt_one;
    }

    public String getQus_two_opt_two() {
        return qus_two_opt_two;
    }

    public void setQus_two_opt_two(String qus_two_opt_two) {
        this.qus_two_opt_two = qus_two_opt_two;
    }

    public Quiz_Data() {
    }

    public Quiz_Data(String qus_one, String qus_two, String qus_one_opt_one, String qus_one_opt_two, String qus_two_opt_one, String qus_two_opt_two,String matchno) {
        this.qus_one = qus_one;
        this.qus_two = qus_two;
        this.qus_one_opt_one = qus_one_opt_one;
        this.qus_one_opt_two = qus_one_opt_two;
        this.qus_two_opt_one = qus_two_opt_one;
        this.qus_two_opt_two = qus_two_opt_two;
        this.matchno = matchno;
    }
}
