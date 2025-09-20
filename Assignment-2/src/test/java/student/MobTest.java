package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MobTest {
    Mob skeleton;
    Mob zombie;

    @BeforeEach
    public void setup() {
        skeleton = new Mob("skeleton", 20, 2);
        zombie = new Mob("zombie", 15, 3);
    }


    @Test
    public void getNameReturnsName() {
        assertEquals("skeleton", skeleton.getName());
        assertEquals("zombie", zombie.getName());
    }

    @Test
    public void getMaxHealthReturnsMaxHealth() {
        assertEquals(20, skeleton.getMaxHealth());
        assertEquals(15, zombie.getMaxHealth());
    }

    @Test
    public void getHealthReturnsHealth() {
        assertEquals(20, skeleton.getHealth());
        skeleton.takeDamage(5);
        assertEquals(15, skeleton.getHealth());
    }

    @Test
    public void toStringWorksWhenHealthy() {
        assertEquals("healthy skeleton", skeleton.toString());
    }

    @Test
    public void toStringWorksWhenInjured() {
        zombie.takeDamage(5);
        assertEquals("injured zombie", zombie.toString());
    }

    @Test
    public void toStringWhenDead() {
        skeleton.takeDamage(20);
        assertEquals("dead skeleton", skeleton.toString());
    }

    @Test
    public void equalsEquals() {
        Mob mobUno = new Mob("skeleton", 20, 2);
        assertTrue(skeleton.equals(mobUno));
        assertFalse(skeleton.equals(zombie));
    }

    @Test
    public void isInjuredWhenInjured() {
        assertFalse(skeleton.isInjured());
        skeleton.takeDamage(5);
        assertTrue(skeleton.isInjured());
    }

    @Test
    public void isAliveWhenLive() {
        assertTrue(skeleton.isAlive());
        skeleton.takeDamage(20);
        assertFalse(skeleton.isAlive());
    }

    @Test
    public void takeDamageDamages() {
        skeleton.takeDamage(5);
        assertEquals(15, skeleton.getHealth());
    }

    @Test
    public void getCurrentStrengthReturnsCurrentStrength() {
        assertEquals(2, skeleton.getCurrentStrength());
        skeleton.takeDamage(10);
        assertEquals(1, skeleton.getCurrentStrength());
    }

    @Test
    public void attackWontLetMobAttackItself() {
        assertEquals("A mob cannot attack itself!", skeleton.attack(skeleton));
    }

    @Test
    public void attackRejectsDeadAttacker() {
        // Kill skeleton.
        skeleton.takeDamage(skeleton.getMaxHealth());

        assertEquals("The dead skeleton cannot attack the healthy zombie.",
                skeleton.attack(zombie));
    }

    @Test
    public void attackRejectsDeadVictim() {
        // Kill zombie.
        zombie.takeDamage(zombie.getMaxHealth());

        assertEquals("The zombie is already dead.",
                skeleton.attack(zombie));
    }

    @Test
    public void attackDamagesOpponent() {
        String description = skeleton.attack(zombie);
        int expectedHealth = zombie.getMaxHealth() - skeleton.getCurrentStrength();
        String expectedResponse = "The skeleton does " + skeleton.getCurrentStrength()
                + " damage to the zombie, which now has health " + expectedHealth + ".";
        assertEquals(expectedResponse, description);
        assertEquals(expectedHealth, zombie.getHealth());
    }

    @Test
    public void attackDoesNotDamageAttacker() {
        skeleton.attack(zombie);
        assertEquals(skeleton.getMaxHealth(), skeleton.getHealth());
    }

    @Test
    public void attackDamagesVictim() {
        skeleton.attack(zombie);
        assertEquals(zombie.getMaxHealth() - skeleton.getCurrentStrength(), zombie.getHealth());
    }

    @Test
    public void attackKillsVictim() {
        // Weaken zombie so one attack will finish it.
        zombie.takeDamage(zombie.getMaxHealth() - skeleton.getCurrentStrength());

        String description = skeleton.attack(zombie);
        assertEquals(
                "The skeleton does " + skeleton.getCurrentStrength() + " damage to the zombie, which is now dead.",
                description);
        assertEquals(0, zombie.getHealth());
        assertFalse(zombie.isAlive());
    }

    @Test
    public void attackDoesNotMakeVictimsHealthNegative() {
        // Weaken zombie so it has just one health point remaining.
        zombie.takeDamage(zombie.getMaxHealth() - 1);

        String description = skeleton.attack(zombie);
        assertEquals(
                "The skeleton does 1 damage to the zombie, which is now dead.",
                description);
        assertEquals(0, zombie.getHealth());
        assertFalse(zombie.isAlive());
    }
}