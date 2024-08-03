package com.app.core.inuha.request;

import com.app.common.infrastructure.request.FillterRequest;
import com.app.views.UI.combobox.ComboBoxItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InuhaFilterPhieuGiamGiaRequest extends FillterRequest {

    private String keyword;
    
    private String ngayBatDau;
    
    private String ngayKetThuc;
    
    private ComboBoxItem<Integer> trangThai = new ComboBoxItem<>();

}
