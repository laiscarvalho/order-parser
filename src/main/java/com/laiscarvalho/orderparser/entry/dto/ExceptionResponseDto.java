package com.laiscarvalho.orderparser.entry.dto;

public record ExceptionResponseDto(
    Integer status,
    String error,
    String message
) {
}