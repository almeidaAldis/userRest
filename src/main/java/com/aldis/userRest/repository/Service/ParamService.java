package com.aldis.userRest.repository.Service;

import com.aldis.userRest.entity.Param;
import com.aldis.userRest.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ParamService {
    @Autowired
    private ParamRepository repository;

    public Optional<Param> getFindByName(String name) {
        return repository.findByName(name);
    }

}
