package com.epam.andrii_loievets.skipass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class SkiPassDemo {

    private SkiPassSystem system;
    private SkiPass skiPassCard;

    public void prepareSystem() {
        system = SkiPassSystem.getInstance();
        system.init();
    }

    public SkiPassSystem getSystem() {
        return system;
    }

    public boolean passTurnstile(Turnstile turnstile, SkiPass card) {
        if (turnstile == null) {
            throw new IllegalArgumentException("Turnstile is null");
        }

        if (card == null) {
            throw new IllegalArgumentException("Ski-pass is null");
        }

        return turnstile.makePass(card);
    }

    public SkiPass getSeasonSkiPass() {

        // Assume the season starts on 2014-December-1
        Calendar calendar = new GregorianCalendar(2014, 11, 1, 0, 0, 0);
        Date activationDate = calendar.getTime();

        // Assume the season ends on 2015-March-31
        calendar.add(Calendar.MONTH, 3);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        Date expirationDate = calendar.getTime();

        return system.createSkiPass("SEASON", activationDate, expirationDate, 0);
    }

    public SkiPass getLimitedSkiPass(int numPassages) {

        // Assume limited ski-pass is valid during the year
        Calendar calendar = new GregorianCalendar();
        Date activationDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 1);
        Date expirationDate = calendar.getTime();
        return system.createSkiPass("LIMITED", activationDate, expirationDate, numPassages);
    }

    public SkiPass getDaysSkiPass(int numDays) {
        if (numDays <= 0) {
            throw new IllegalArgumentException("Cannot create card for non-positive number of days");
        }

        Calendar calendar = new GregorianCalendar();
        Date activationDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, numDays);
        Date expirationDate = calendar.getTime();

        return system.createSkiPass("HOURLY", activationDate, expirationDate, 0);
    }

    /**
     *
     * @param currentDate date and time of the order
     * @return ski-pass for a period 9:00 - 13:00
     */
    public SkiPass getFirstHalfDaySkiPass(Date currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date is null");
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date activationDate = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, 4);

        Date expirationDate = calendar.getTime();

        if (expirationDate.before(currentDate)) {
            return null;
        } else {
            return system.createSkiPass("HOURLY", activationDate,
                    expirationDate, 0);
        }
    }

    public SkiPass getSecondHalfDaySkiPass(Date currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date is null");
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date activationDate = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, 4);

        Date expirationDate = calendar.getTime();

        if (expirationDate.before(currentDate)) {
            return null;
        } else {
            return system.createSkiPass("HOURLY", activationDate,
                    expirationDate, 0);
        }
    }

    public void buySkiPassMenu(Scanner scanner) {

        // Delete old card
        skiPassCard = null;

        System.out.println("Choose ski-pass type from the list:");
        System.out.println("1 - LIMITED:"
                + "    limited number of passages, valid for 1 year");
        System.out.println("2 - HOURLY:"
                + "    unlimited number of passages, limited time");
        System.out.println("3 - SEASON:"
                + "    unlimited number of passages during the whole season");

        int option;

        option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Enter the number of passages: ");
                int numPassages = scanner.nextInt();
                skiPassCard = getLimitedSkiPass(numPassages);
                break;
            case 2:
                System.out.println("1 - 1 day or more");
                System.out.println("2 - first half of the day 9:00 - 13:00");
                System.out.println("3 - second half of the day 13:00 - 17:00");

                int hourlyCardType = scanner.nextInt();

                switch (hourlyCardType) {
                    case 1:
                        System.out.println("Enter the number of days:");
                        int numDays = scanner.nextInt();
                        skiPassCard = getDaysSkiPass(numDays);
                        break;
                    case 2:
                        skiPassCard = getFirstHalfDaySkiPass(new Date());
                        break;
                    case 3:
                        skiPassCard = getSecondHalfDaySkiPass(new Date());
                        break;
                    default:
                        System.out.println("Wrong option! Please try again.");
                        break;
                }
                break;

            case 3:
                skiPassCard = getSeasonSkiPass();
                break;
            default:
                System.out.println("Wrong option! Please try again.");
                break;
        }

        if (skiPassCard == null) {
            System.out.println("Cannot sell a ski-pass! Please try again.");
        }
    }

    public void menu(Scanner scanner) {
        System.out.println("Welcome to Ski-pass system!");
        System.out.println("---------------------------");
        int option = -1;

        while (option != 0) {
            System.out.println("\n1 - buy a ski-pass");
            System.out.println("2 - make 1 passage");
            System.out.println("3 - show ski-pass info");
            System.out.println("0 - exit");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    buySkiPassMenu(scanner);
                    showSkiPassInfo();
                    break;
                case 2:
                    if (skiPassCard == null) {
                        System.out.println("Please buy a ski-pass first!");
                    } else {
                        if (passTurnstile(system.getTurnstile(0), skiPassCard)) {
                            System.out.println("1 passage was made!");
                        } else {
                            System.out.println("Cannot make a passage!"
                                    + "Please check your card.");
                        }
                        showSkiPassInfo();
                    }
                    break;
                case 3:
                    showSkiPassInfo();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Wrong option! Please try again.");
                    break;
            }
        }

        System.out.println("Thank you for using our system!");
        System.out.println("Have a nice day!");
    }

    public void showSkiPassInfo() {
        if (skiPassCard == null) {
            System.out.println("\nYou don't have a ski-pass card!");
        } else {
            System.out.println("\nSki-pass info:");
            System.out.println("ID\t" + skiPassCard.getID());
            System.out.println("Card type\t" + skiPassCard.getCardType());
            System.out.println("Activated on\t" + skiPassCard.getActivationDate());
            System.out.println("Expired on\t" + skiPassCard.getExpirationDate());
            if (skiPassCard instanceof LimitedSkiPass) {
                System.out.println("Passages left\t"
                        + ((LimitedSkiPass) skiPassCard).getNumPassages());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SkiPassDemo spDemo = new SkiPassDemo();

        spDemo.prepareSystem();
        spDemo.menu(scanner);

        scanner.close();
    }
}
