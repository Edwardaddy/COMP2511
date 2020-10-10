package staff;

import java.time.LocalDate;

public class Lecturer extends StaffMember{
    private String school;
    private char status;
    public Lecturer(String name, double salary,LocalDate hireDate, LocalDate endDate, String school, char status) {
        super(name, salary,hireDate,endDate);
        this.school = school;
        this.status = status;
        
    }
    public String getSchool() {
        return this.school;
    }
    public char getStatus() {
        return status;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return super.toString()+"school: "+this.school+"status: "+this.status;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Lecturer a = (Lecturer) obj;
        return ((StaffMember) a).equals((StaffMember) this) && a.getSchool().equals(this.school) && a.getStatus() == this.status;
    }
    
}