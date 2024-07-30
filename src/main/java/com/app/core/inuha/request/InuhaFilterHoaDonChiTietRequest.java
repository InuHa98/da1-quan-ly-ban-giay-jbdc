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
public class InuhaFilterHoaDonChiTietRequest extends FillterRequest {

    private int idHoaDon;
    
    private int idSanPhamChiTiet;

}
