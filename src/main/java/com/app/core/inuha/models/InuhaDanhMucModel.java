package com.app.core.inuha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author InuHa
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InuhaDanhMucModel {
    
    private int stt;
    
    private String ten;
    
    private String ngayTao;
    
    private String ngayCapNhat;
    
    public Object[] toDataRow() { 
        return new Object[] { 
            stt,
            ten,
            ngayTao,
            ngayCapNhat
        };
    }
}
