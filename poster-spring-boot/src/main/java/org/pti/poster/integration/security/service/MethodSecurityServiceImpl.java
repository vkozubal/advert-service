package org.pti.poster.integration.security.service;

import org.springframework.stereotype.Service;

@Service
public class MethodSecurityServiceImpl implements MethodSecurityService {

    public String requiresUserRole() {
        return "You have ROLE_USER";
    }
}