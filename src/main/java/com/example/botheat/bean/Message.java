package com.example.botheat.bean;

import lombok.Data;

/**
 * @author haya
 */
@Data
public class Message<T> {
    private boolean state;
    private T body;
}
