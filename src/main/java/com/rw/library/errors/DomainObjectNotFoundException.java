package com.rw.library.errors;

public class DomainObjectNotFoundException extends RuntimeException {
    public DomainObjectNotFoundException(String domainObject , Long id) {
        super("Could not find " + domainObject + " id '" + id + "'");
    }
}
