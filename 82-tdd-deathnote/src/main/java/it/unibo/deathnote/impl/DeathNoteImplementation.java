package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.*;


public class DeathNoteImplementation implements DeathNote {
    
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";
    private Map<String, DeathArgs> names;
    private String lastName;
    private long lastNameModification;
    
    public DeathNoteImplementation() {
        names = new HashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Out of bound!");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        names.put(name, new DeathArgs(DEFAULT_DEATH_CAUSE));
        lastName = name;
        lastNameModification = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (names.isEmpty() || cause == null || cause.length() == 0) {
            throw new IllegalStateException("Death cause must be valid");
        }
        if (System.currentTimeMillis() - lastNameModification <= 40) {
            names.get(this.lastName).cause = cause;
            lastNameModification = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if (names.isEmpty() || details == null || details.length() == 0) {
            throw new IllegalStateException("Death cause must be valid");
        }
        if (System.currentTimeMillis() - lastNameModification <= 6040) {
            names.get(this.lastName).details = details;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        if (name == null || names.get(name) == null) {
            throw new IllegalArgumentException();
        }
        return names.get(name).cause;
    }

    @Override
    public String getDeathDetails(final String name) {
        if (name == null || names.get(name) == null) {
            throw new IllegalArgumentException();
        }
        return names.get(name).details;
    }

    @Override
    public boolean isNameWritten(final String name) {
        if (name == null || names.get(name) == null) {
            return false;
        }
        return true;
    }
    
    
    private class DeathArgs {
        String cause;
        String details;

        public DeathArgs(String cause) {
            this.cause = cause;
        }
    }

}