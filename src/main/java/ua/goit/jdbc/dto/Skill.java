package ua.goit.jdbc.dto;


import java.util.List;
import java.util.Objects;

public class Skill {
    private long id;
    private Branch branch;
    private SkillLevel level;
    private List<Developer> developers;

    public Skill() {
    }

    public Skill(long id, Branch branch, SkillLevel level) {
        this.id = id;
        this.branch = branch;
        this.level = level;
    }

    public Skill(Branch branch, SkillLevel level) {
        this(0, branch, level);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", branch=" + branch.getName() +
                ", level=" + level.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && branch == skill.branch && level == skill.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch, level);
    }

    public String simpleString() {
        return "branch-" + branch.getName() + ", level-" + level.getName();
    }
}
