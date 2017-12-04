package server;

public class Register {

    private final long id;
    private final String content;

    public Register(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}