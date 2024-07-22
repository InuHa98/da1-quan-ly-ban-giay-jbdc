package com.app.views.UI.combobox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author InuHa
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComboBoxItem<T> {

    private String text;

    private T value;

    @Override
    public String toString() {
        return text;
    }

}
