package com.example.botheat.service.impl;

import com.example.botheat.entity.Operator;
import com.example.botheat.service.UUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author haya
 */
@Service
public class UUIDServiceImpl implements UUIDService {
    @Autowired
    private Map<UUID, Operator> uuidPoll;

    @Override
    public UUID add(Operator operator) {
        UUID uuid = UUID.nameUUIDFromBytes( operator.toString().getBytes() );
        uuidPoll.put( uuid, operator );
        return uuid;
    }

    @Override
    public boolean remove(UUID uuid) {
        uuidPoll.remove( uuid );
        return !uuidPoll.containsKey( uuid );
    }

}
