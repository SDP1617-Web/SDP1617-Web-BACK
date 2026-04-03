package com.sdp1617.webserver.domain.test.presentation;

import com.sdp1617.webserver.global.common.response.result.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Example",description = "해당 컨트롤러를 참고하여 개발해주세요.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hi")
    @Operation(summary="예시용 api",description = "예시용 api 입니다.")
    public SuccessResponse<Void> hi(){
        return SuccessResponse.ok(null);
    }

}
