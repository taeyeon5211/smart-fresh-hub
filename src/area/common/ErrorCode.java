package area.common;

public enum ErrorCode {
    HELLO("hello"),
    WORLD("world");

    private final String text;

    ErrorCode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
