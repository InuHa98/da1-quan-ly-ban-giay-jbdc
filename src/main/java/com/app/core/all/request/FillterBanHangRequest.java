package com.app.core.all.request;

import com.app.common.infrastructure.request.FillterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FillterBanHangRequest extends FillterRequest {

    private String keyword;

}
