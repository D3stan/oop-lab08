package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.*;


public class DeathNoteImplementation implements DeathNote {

    public class DeathArgs {
        String cause;
        String details;
    }

    private Map<String, DeathArgs> names;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
          throw new IllegalArgumentException("Out of bound!");
        }
        return null;
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (names.size() == 0 || cause == null || cause.length() == 0) {
            throw new IllegalStateException("Death cause must be valid");
        }
        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if (names.size() == 0 || details == null || details.length() == 0) {
            throw new IllegalStateException("Death cause must be valid");
        }
        return false;
    }

    @Override
    public String getDeathCause(final String name) {
        if (name == null || names.get(name) == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    @Override
    public String getDeathDetails(final String name) {
        if (name == null || names.get(name) == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    @Override
    public boolean isNameWritten(final String name) {
        if (name == null || names.get(name) == null) {
            return false;
        }
        return true;
    }

}