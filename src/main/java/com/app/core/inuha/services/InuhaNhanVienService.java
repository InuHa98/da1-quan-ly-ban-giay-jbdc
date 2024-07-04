/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.app.core.inuha.services;

import com.app.common.infrastructure.interfaces.IDAOService;
import com.app.core.inuha.models.InuhaNhanVienModel;

/**
 *
 * @author inuHa
 */

public interface InuhaNhanVienService extends IDAOService<InuhaNhanVienModel, Integer> {

    public void changePassword(String oldPassword, String newPassword, String confirmPassword);

}