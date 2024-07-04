package com.app.views.UI.combobox;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComboBoxItem<T> {

    private String text;

    private T value;

    @Override
    public String toString() {
        return text;
    }

}
