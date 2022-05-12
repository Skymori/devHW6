package ua.goit.jdbc.dto;

import ua.goit.jdbc.exceptions.DAOException;

import java.util.Arrays;

public enum SkillLevel {
    JUNIOR("Junior"),
    MIDDLE("Middle"),
    SENIOR("Senior");

    private final String name;

    SkillLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SkillLevel findByName(String name) throws DAOException {
        return Arrays.stream(SkillLevel.values())
                .filter(level -> level.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new DAOException("Level with name " + name + " doesn't exists"));
    }
}
