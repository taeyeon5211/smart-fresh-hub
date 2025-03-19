package warehouse.common;

public enum TextEnum {

    HELLO("hello"),
    WORLD("world");

    private final String text;

    TextEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
