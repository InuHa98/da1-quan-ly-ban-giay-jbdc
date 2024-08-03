/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.core.dattv.request;

import com.app.common.infrastructure.request.FillterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author WIN
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DatFillerHoaDonRequest extends FillterRequest{
     private String keyword;
    
    private int idPhuongThucthanhtoan;
    
    private int idNguoitao;
    
    private int trangThai;
}
