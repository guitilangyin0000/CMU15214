package edu.cmu.cs.cs214.hw6.util;

import java.io.Serializable;

/**
 * A simple class that organizes two Strings in a key-value pair.
 *
 * You may modify (or choose not to use) this class if you wish.
 */
public class KeyValuePair implements Serializable {
    private static final long serialVersionUID = -3076818555292211066L;

    private String mKey;
    private String mValue;

    public KeyValuePair(String key, String value) {
        mKey = key;
        mValue = value;
    }

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public void setValue(String value) {
        mValue = value;
    }

    @Override
    public String toString() {
        return String.format("<%s: key=%s, value=%s>", KeyValuePair.class.getSimpleName(), mKey,
                mValue);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (mKey == null ? 0 : mKey.hashCode());
        result = prime * result + (mValue == null ? 0 : mValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyValuePair)) {
            return false;
        }
        KeyValuePair other = (KeyValuePair) o;
        return equals(mKey, other.mKey) && equals(mValue, other.mValue);
    }

    private static boolean equals(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

}
