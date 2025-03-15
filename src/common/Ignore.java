package common;

public enum Ignore {
    URL("jdbc:mysql://127.0.0.1:3306/~~~~~~?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"),
    PASSWORD("~~~~~");

    private final String text;

    Ignore(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
