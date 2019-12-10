package com.example.botheat.service;

import com.example.botheat.entity.Operator;

import java.util.UUID;

/**
 * @author haya
 */
public interface UUIDService {
    UUID add(Operator credential);

    boolean remove(UUID uuid);
}
