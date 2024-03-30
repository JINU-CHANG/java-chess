package chess.domain;

import java.util.Arrays;

public enum Command {

    START("start"),
    END("end"),
    STATUS("status"),
    MOVE("move");

    private final String message;

    Command(final String message) {
        this.message = message;
    }

    public static Command from(final String message) {
        return Arrays.stream(values())
                .filter(command -> command.message.equals(message))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바르지 않은 명령어입니다."));
    }

    public boolean isStart() {
        return this.equals(Command.START);
    }

    public boolean isMove() {
        return this.equals(Command.MOVE);
    }

    public boolean isEnd() {
        return this.equals(Command.END);
    }

    public boolean isStatus() {
        return this.equals(Command.STATUS);
    }
}
