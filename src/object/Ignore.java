package object;

public enum Ignore {
    URL("jdbc:mysql://127.0.0.1:3306/wms_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"),
    PASSWORD("1234");

    private final String text;

    Ignore(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
