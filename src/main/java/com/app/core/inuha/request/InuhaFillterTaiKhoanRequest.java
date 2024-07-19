package com.app.core.inuha.request;

import com.app.common.infrastructure.request.FillterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InuhaFillterTaiKhoanRequest extends FillterRequest {

    private String keyword;

}
