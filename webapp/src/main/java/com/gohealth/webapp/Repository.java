package com.gohealth.webapp;

import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository <User, String> {
    User findUserByEmail (String email);
}
