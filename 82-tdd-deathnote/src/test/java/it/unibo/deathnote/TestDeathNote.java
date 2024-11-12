package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

import java.util.Objects;
import java.util.Random;

class TestDeathNote {
    
    private static final int ZERO_RULE_BOUND = 0;
    private static final int NEG_RULE_BOUND = -1;
    private static final String HUMAN = "Joe";
    private final Random randomGenerator = new Random();
    
    private DeathNote myDeathNote;

    @BeforeEach
    public void setUp() {
        myDeathNote = new DeathNoteImplementation();
    }

    @Test
    void testRulesBounds() {
        try {
            myDeathNote.getRule(ZERO_RULE_BOUND);
            myDeathNote.getRule(NEG_RULE_BOUND);
        } catch (Exception exception) {
            if (exception.getClass() != IllegalArgumentException.class ||
                exception.getMessage() != null
            ) {
                fail();
            }
        }
        
    }

    @Test
    void testNullRule() {
        try {
            for (String rule : DeathNote.RULES) {
                Objects.requireNonNull(rule);
                if (rule.length() == 0) {
                    fail();
                }
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    void testHumanDeath() {
        String modifier = HUMAN;
        while (myDeathNote.isNameWritten(modifier)) {
            modifier += String.valueOf(randomGenerator.nextInt());
        }

        myDeathNote.writeName(modifier);
        if (!myDeathNote.isNameWritten(modifier)) {
            fail();
        }
    }
}