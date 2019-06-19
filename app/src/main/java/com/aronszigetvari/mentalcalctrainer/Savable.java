package com.aronszigetvari.mentalcalctrainer;

public interface Savable {
    Object load(String storedValue);
    String save();
}
