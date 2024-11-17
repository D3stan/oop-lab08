package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private static final long CAUSE_MILLIS = 40;
    private final Random randomGenerator = new Random();
    
    private DeathNote myDeathNote;

    private String writeNewHuman(String modifier) {
        while (myDeathNote.isNameWritten(modifier)) {
            modifier += String.valueOf(randomGenerator.nextInt());
        }
        myDeathNote.writeName(modifier);
        return modifier;
    }

    @BeforeEach
    public void setUp() {
        myDeathNote = new DeathNoteImplementation();
    }

    @Test
    void testRulesBounds() {
        try {
            myDeathNote.getRule(ZERO_RULE_BOUND);
            myDeathNote.getRule(NEG_RULE_BOUND);
        } catch (Exception e) {
            if (e.getClass() != IllegalArgumentException.class ||
                e.getMessage() == null ||
                e.getMessage().isBlank() ||
                e.getMessage().isEmpty()
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
        } catch (Exception e) {
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
        assertTrue(myDeathNote.isNameWritten(modifier));
        assertFalse(myDeathNote.isNameWritten(modifier + String.valueOf(randomGenerator.nextInt())));
        assertFalse(myDeathNote.isNameWritten(""));    
    }

    @Test
    void testCauseTiming() throws InterruptedException {
        try {
            myDeathNote.writeDeathCause(String.valueOf(randomGenerator.nextInt()));
        } catch (Exception e) {
            if (e.getClass() != IllegalStateException.class) {
                fail();
            }
        }

        String newHumanName = writeNewHuman(HUMAN);
        Thread.sleep(CAUSE_MILLIS + 2);
        assertEquals(myDeathNote.getDeathCause(newHumanName), "heart attack");
        
        newHumanName = writeNewHuman(HUMAN);
        myDeathNote.writeDeathCause("karting accident");
        assertEquals(myDeathNote.getDeathCause(newHumanName), "karting accident");
        Thread.sleep(100);

        myDeathNote.writeDeathCause("swimming accident");
        assertEquals(myDeathNote.getDeathCause(newHumanName), "karting accident");
    }

    @Test
    void testDetailsTiming() throws InterruptedException {
        try {
            myDeathNote.writeDetails(String.valueOf(randomGenerator.nextInt()));
        } catch (Exception exception) {
            if (exception.getClass() != IllegalStateException.class) {
                fail();
            }
        }

        String newHumanName = writeNewHuman(HUMAN);
        assertNull(myDeathNote.getDeathDetails(newHumanName));
        assertTrue(myDeathNote.writeDetails("ran for too long"));
        assertEquals("ran for too long", myDeathNote.getDeathDetails(newHumanName));

        newHumanName = writeNewHuman(HUMAN);
        Thread.sleep(6100);
        try {
            myDeathNote.writeDetails("ran for too long");
        } catch (Exception e) {
            if (e.getClass() != IllegalStateException.class) {
                fail();
            }
        }
        assertNull(myDeathNote.getDeathDetails(newHumanName));
    }
}