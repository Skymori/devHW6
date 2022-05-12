package ua.goit.jdbc.dto;

import ua.goit.jdbc.exceptions.DAOException;

import java.util.Arrays;

public enum Branch {
    JAVA("Java"),
    CPLUS("C++"),
    CSHARP("C#"),
    JS("JS");

    private final String name;

    Branch(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Branch findByName(String name) throws DAOException {
        return Arrays.stream(Branch.values())
                .filter(branch -> branch.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new DAOException("Branch with name " + name + " doesn't exists"));
    }
}
