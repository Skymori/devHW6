package ua.goit.jdbc.dto;

import java.util.List;
import java.util.Objects;

public class Developer {
    private long id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private double salary;
    private List<Skill> skills;
    private List<Project> projects;

    public Developer() {
    }

    public Developer(long id, String firstName, String lastName, Sex sex, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.salary = salary;
    }

    public Developer(String firstName, String lastName, Sex sex, double salary) {
        this(0, firstName, lastName, sex, salary);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && Double.compare(developer.salary, salary) == 0 &&
                Objects.equals(firstName, developer.firstName) &&
                Objects.equals(lastName, developer.lastName) &&
                sex == developer.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, sex, salary);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex.getName() +
                ", salary=" + salary +
                '}';
    }
}
