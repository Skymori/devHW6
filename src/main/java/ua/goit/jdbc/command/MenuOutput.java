package ua.goit.jdbc.command;

public enum MenuOutput {
    HELP_MENU("""
            create - to add info in database
            read - to get existing info in database
            update - to update existing info in database
            delete - to delete info in database
            exit - exit from an application"""),
    CREATE_MENU("""
            Please, enter the number according to list below
            1 - create customer
            2 - create company
            3 - create project
            4 - create developer
            5 - create skill
            return - go back to main menu
            """),
    READ_SECTION_MENU("""
            Please, enter the number according to list below
            1 - to get info by id
            2 - to get all info
            3 - get sum of salary for project
            4 - get list of developers for project
            5 - get list of developers by branch
            6 - get list of developers by skill level
            7 - get list of projects with date, name and quantity of developers
            return - go back to main menu
            """),
    READ_SUB_SECTION_MENU("""
            Choose the section you would like to read. Enter the number according to list below
            1 - customers
            2 - companies
            3 - projects
            4 - developers
            5 - skills
            return - back to the read menu
            """),
    UPDATE_SECTION_MENU("""
            Please, enter the number according to list below
            1 - update customer
            2 - update company
            3 - update project
            4 - update developer
            5 - update skill
            return - go back to main menu
            """),
    UPDATE_CUSTOMER_SECTION_MENU("""
            Choose info you would like to update from list below
            1 - update customer name
            2 - update customer industry
            3 - add customer companies and projects
            ok - when you are ready
            """),
    UPDATE_COMPANY_SECTION_MENU("""
            Choose info you would like to update from list below
            1 - update company name
            2 - update company headquarters
            3 - add company customers and projects
            ok - when you are ready
            """),
    UPDATE_PROJECT_SECTION_MENU("""
            Choose info you would like to update from list below
            1 - update project name
            2 - update project description
            3 - update project cost
            4 - add project customers and companies
            5 - add project developers
            ok - when you are ready
            """),
    UPDATE_DEVELOPER_SECTION_MENU("""
            Choose info you would like to update from list below
            1 - update developer first name
            2 - update developer last name
            3 - update developer gender
            4 - update developer salary
            5 - add developer projects
            6 - add developer skills
            ok - when you are ready
            """),
    UPDATE_SKILL_SECTION_MENU("""
            Choose info you would like to update from list below
            1 - update skill branch
            2 - update skill level
            3 - add skill developers
            ok - when you are ready
            """),
    DELETE_MENU("""
            Please, enter the number according to list below
            1 - delete customer
            2 - delete company
            3 - delete project
            4 - delete developer
            5 - delete skill
            return - go back to main menu
            """);

    private final String text;

    MenuOutput(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
