package staff;

import java.time.LocalDate;
/**
 * A staff member
 * @author Robert Clifton-Everest
 *
 */
public class StaffMember {
    public String name;
    public double salary;
    public LocalDate hireDate;
    public LocalDate endDate;

    public StaffMember (String name, double salary, LocalDate hireDate, LocalDate endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
        this.endDate = endDate;
    }
    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setHireDate (LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj ==  null) return false;
        if (this.getClass() != obj.getClass()) return false;
        StaffMember a = (StaffMember) obj;
        return this.name.equals(a.name) && this.name.equals(a.salary) && this.name.equals(a.hireDate);
    }

    @Override
    public String toString() {
        String str = "Name: " + getName() + "Salary: " + getSalary() + "start date: " + getHireDate() + "end date:" + getEndDate();
        return str;
    }
}