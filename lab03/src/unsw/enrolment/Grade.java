package unsw.enrolment;

public class Grade {
    private int mark;
    private String grade;

    public Grade(int mark) {
        this.mark = mark;
        if (mark < 50)
            grade = "FL";
    }

    public boolean isPassing() {
        return mark >= 50;
    }
}
