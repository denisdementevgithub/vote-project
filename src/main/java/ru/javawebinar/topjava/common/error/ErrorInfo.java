package ru.javawebinar.topjava.common.error;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}