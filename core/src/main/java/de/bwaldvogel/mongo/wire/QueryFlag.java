package de.bwaldvogel.mongo.wire;

public enum QueryFlag implements Flag {
    TAILABLE(1), //
    SLAVE_OK(2), //
    OPLOG_REPLAY(3), //
    NO_CURSOR_TIMEOUT(4), //
    AWAIT_DATA(5), //
    EXHAUST(6), //
    PARTIAL(7);

    private int value;

    QueryFlag(int bit) {
        this.value = 1 << bit;
    }

    @Override
    public boolean isSet(int flags) {
        return (flags & value) == value;
    }

    @Override
    public int removeFrom(int flags) {
        return flags - value;
    }
}
