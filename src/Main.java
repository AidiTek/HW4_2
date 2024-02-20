import java.util.Random;
import java.util.SortedMap;

import static java.lang.Math.random;

public class Main {
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {160, 210, 90, 200, 200, 200, 400, 250};
    public static int[] heroesDamage = {20, 15, 10, 0, 10, 15, 5, 0};
    public static String[] ourHeroes = {"Warrior", "Magician", "Archer", "Madic", "Lucky", "Thor", "Golem", "Witcher"};
    public static String[] heroesAttackType = {"Physical", "Magical", "Piercing", "Heal", "Luck", "Staning", "TakingDamge", "Enlivens"};
    public static int roundNumber = 0;
    public static double luckChance = 0.3;


    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }


    public static void medicalTreatment() {
        if (heroesHealth[3] > 0) {
            int heroToHealIndex = -1;
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                    heroToHealIndex = i;
                    break;
                }
            }
            if (heroToHealIndex != -1) {
                heroesHealth[heroToHealIndex] += 20;
                System.out.println("Medic heals hero " + ourHeroes[heroToHealIndex] + " for 20 health points");
            }
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        medicalTreatment();
        heroesAttack();
        showStatistics();
    }

    public static void bossAttacks() {
        Random random = new Random();
        double isLucky = random.nextDouble();
        boolean isStunning = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (isStunning) {
                    System.out.println("Thor stunned the boss");
                    break;
                }
                int partOfDamage = bossDamage / 5;
                heroesHealth[6] -= partOfDamage;
                if (heroesHealth[6] < 0) {
                    heroesHealth[6] = 0;
                }
                heroesHealth[i] = heroesHealth[i] - (bossDamage - partOfDamage);
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
                if (isLucky <= luckChance && ourHeroes[i].equals("Lucky")) {
                    heroesHealth[4] = heroesHealth[4] + bossDamage;
                    System.out.println("Lucky dodged the boss's attack");
                }
                if (heroesHealth[i] == 0) {
                    heroesHealth[i] = heroesHealth[7] + heroesHealth[i];
                    heroesHealth[7] = 0;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);

                }
                bossHealth = bossHealth - damage;
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health:" + bossHealth + "|Damage:"
                + bossDamage + "|Defence:" + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(ourHeroes[i] + " health:" + heroesHealth[i] + "|Damage:"
                    + heroesDamage[i]);
        }
    }
}