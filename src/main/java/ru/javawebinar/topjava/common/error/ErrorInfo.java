package ru.javawebinar.topjava.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "ErrorInfo object")
public class ErrorInfo {
    @Schema(description = "URL of a request")
    private final String url;
    @Schema(description = "Type of error")
    private final ErrorType type;
    @Schema(description = "Error information")
    private String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }
}