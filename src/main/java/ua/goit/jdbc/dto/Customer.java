package ua.goit.jdbc.dto;

import java.util.List;
import java.util.Objects;

public class Customer {
    private long id;
    private String name;
    private String industry;
    private List<Company> companies;
    private List<Project> projects;

    public Customer() {
    }

    public Customer(long id, String name, String industry) {
        this.id = id;
        this.name = name;
        this.industry = industry;
    }

    public Customer(String name, String industry) {
        this(0, name, industry);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(industry, customer.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, industry);
    }
}
