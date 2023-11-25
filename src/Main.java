import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Piškvorky
 */
public class Main {
    /** Pole v ktorom je uložený obsah hracej plochy **/
    public static char[][] charArea = new char[3][3];

    /** Premenná v ktorej je uložené označenie hráča **/
    public static char player;

    /** Premenná v ktorej je uložené kolo hry **/
    public static int round = 1;

    /** Prepravka slúži na priradenie súradníc hracieho poľa **/
    public static int[] crate = new int[2];

    /**
     * Metóda vykreslí hraciu plochu
     */
    public static void drawArea() {
        for (int i = 0; i < charArea.length; i++) {
            for (int j = 0; j < charArea[0].length; j++) {
                System.out.print(charArea[i][j]);
                if (!(j == charArea[0].length - 1)) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (!(i == charArea.length - 1)) {
                System.out.println("-----");
            }
        }
    }

    /**
     * Metóda dekóduje číslu ktoré zadal užívatel na pozíciu v
     * hracom poli a uloží ju do prepravky
     * @param number - číslo, ktoré zadal používateľ
     */
    public static void assignCoordinates(int number) {
        switch (number) {
            case 1 -> {crate[0] = 0; crate[1] = 0;}
            case 2 -> {crate[0] = 0; crate[1] = 1;}
            case 3 -> {crate[0] = 0; crate[1] = 2;}
            case 4 -> {crate[0] = 1; crate[1] = 0;}
            case 5 -> {crate[0] = 1; crate[1] = 1;}
            case 6 -> {crate[0] = 1; crate[1] = 2;}
            case 7 -> {crate[0] = 2; crate[1] = 0;}
            case 8 -> {crate[0] = 2; crate[1] = 1;}
            case 9 -> {crate[0] = 2; crate[1] = 2;}
        }
    }

    /**
     * Metóda zisťuje či nastala výhra
     * @param area - hracie pole
     * @return - vracia true v prípade výhry
     */
    public static boolean isWinning(char[][] area) {
        // Kontrola riadkov, stĺpcov a uhlopriečok pre výhru
        for (int i = 0; i < area.length; i++) {
            if (area[i][0] != ' ' && area[i][0] == area[i][1] && area[i][1] == area[i][2]) {
                return true; // Výhra v riadku
            }
            if (area[0][i] != ' ' && area[0][i] == area[1][i] && area[1][i] == area[2][i]) {
                return true; // Výhra v stĺpci
            }
        }

        if (area[0][0] != ' ' && area[0][0] == area[1][1] && area[1][1] == area[2][2]) {
            return true; // Výhra na hlavnej diagonále
        }

        return area[0][2] != ' ' && area[0][2] == area[1][1] && area[1][1] == area[2][0]; // Výhra na vedľajšej diagonále
    }

    public static void main(String[] args) {
        System.out.println("Vytaj v hre PISKVORKY!!!");
        System.out.println("Hru hraješ tak že po vyzvaní zadáš číslo od 1 po 9");

        // naplnenie poľa ' '
        for (char[] chars : charArea) {
            Arrays.fill(chars, ' ');
        }

        String userInput;
        Scanner scanner = new Scanner(System.in);

        // vykreslenie hracej plochy
        drawArea();


        while (true) {
            // nastavenie hráča
            if (round % 2 == 0) {
                player = 'O';
            } else {
                player = 'X';
            }

            System.out.println("Hráč " + player + " zadaj číslo od 1-9 alebo q pre koniec: ");

            userInput = scanner.nextLine();

            // ukončenie hry po stlačení q
            if (userInput.equals("q")) {
                break;
            }

            try {
                int number = Integer.parseInt(userInput);

                if (number > charArea.length*charArea.length || number < 1) {
                    throw new Exception("Zadal si číslo ktoré je mimo rozsah 1-9!!!");
                } else {
                    assignCoordinates(number);
                }

                if (charArea[crate[0]][crate[1]] == ' ') {
                    charArea[crate[0]][crate[1]] = player;
                    // vykreslenie hracej plochy
                    drawArea();
                    // testovanie výhry
                    if (isWinning(charArea)) {
                        System.out.println("Hráč " + player + " vyhral. Gratulujem!!");
                        break;
                    }
                    // zisťovanie remízy
                    if (round == charArea.length * charArea[0].length) {
                        System.out.println("REMÍZA");
                        break;
                    }
                    round++;
                } else {
                    System.out.println("Táto pozícia je už obsadená!!!");
                }

            } catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("Zadal si text!!!, Zadávaj iba čísla od 1-9 alebo q pre koniec");
                } else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}