package it.polimi.ingsw.client.cli;

public enum Colors {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),

    SELECTED("\u001B[36m"),
    WITH_MN("\u001B[32m");

    static final String RESET = "\u001B[0m";
    private String escape;

    Colors(String escape) {
        this.escape = escape;
    }
    public String getEscape()
    {
        return escape;
    }
    @Override
    public String toString()
    {
        return escape;
    }
}
