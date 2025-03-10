public class Writer {
    private String nickname;

    public Writer(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Writer)) {
            return false;
        }
        Writer writer = (Writer) obj;
        return nickname.equals(writer.nickname);
    }
}
