/**
 * @author <a href="mailto:avtsydypov@edu.hse.ru"> Agvan Tsydypov</a>
 */
package packProgram;
import java.util.Random;
import java.util.Scanner;

public class Monopoliya {
    private static Random rnd = new Random();
    public static void main(String[] args) {
        int width;
        int height;
        double money;
        if(args.length != 3)
        {
            System.out.println("3 arguments: height width money");
            return;
        }
        try {
            height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            money = Double.parseDouble(args[2]);
            if(height > 30 || height < 6) throw new Exception();
            if(width > 30 || width < 6) throw new Exception();
            if(money > 15000 || money < 500) throw new Exception();
        }
        catch (Exception ex)
        {
            System.out.println("6 <= height <= 30\n6 <= width <= 30\n500 <= money <= 15000");
            return;
        }
        Map map = new Map(width,height);
        map.fillMap();
        Player user = new Player(width,height,money, "You");
        Player bot = new Player(width,height,money, "Bot");
        playerTurn(user,bot,map,rnd.nextInt(2));
    }
    //симуляция игры
    public static void playerTurn(Player p1,Player p2,Map map,int j) {
        if (j == 0)
            System.out.println(p1.getname() + " go first");
        else
            System.out.println(p2.getname() + " goes first");
        double creditCoeff = (rnd.nextInt(199) + 2) / 1000.0;
        double debtCoeff = (rnd.nextInt(21) + 10) / 10.0;
        double penaltyCoeff = (rnd.nextInt(10) + 1) / 100.0;
        System.out.println("creditCoeff: " + creditCoeff + "\n"
                + "debtCoeff: " + debtCoeff + "\n"
                + "penaltyCoeff: " + penaltyCoeff);
        Scanner scan = new Scanner(System.in);
        System.out.println(p1.playerInfo());
        System.out.println(p2.playerInfo());
        while (p1.moneyInfo() != 0 && p2.moneyInfo() != 0) {
            int cube1 = rnd.nextInt(6) + 1;
            int cube2 = rnd.nextInt(6) + 1;
            map.display();
            j = j % 2;
            if (j == 0) {
                System.out.println("Your turn!!!");
                int[] posP1 = p1.getPos();
                System.out.println(p1.getname() + " throw " + cube1 + " and " + cube2);
                p1.playerTurn(cube1 + cube2);
                posP1 = p1.getPos();
                String str;

                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[T]") {
                    int taxiShift = rnd.nextInt(3) + 3;
                    p1.playerTurn(taxiShift);
                    System.out.println("You are shifted forward by " + taxiShift + " cells");
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[M]") {
                    FieldShop f = (FieldShop) map.getField(posP1[0], posP1[1]);
                    System.out.println("You are in your shop " +
                            posP1[0] + " " + posP1[1] + ". " +
                            "Would you like to upgrade it for " +
                            +f.upgradePrice() + "$? " +
                            "Input ‘Yes’ if you agree or ‘No’ otherwise");
                    do {
                        str = scan.nextLine();
                    } while (!str.equals("Yes") && !str.equals("No"));
                    if (str.equals("Yes") && p1.moneyInfo() < f.upgradePrice())
                        System.out.println("You cant upgrade this shop");
                    if (str.equals("Yes") && p1.moneyInfo() > f.upgradePrice()) {
                        p1.updateMoney(-f.upgradePrice());
                        f.upgradeShop();
                        map.changeMap(f, posP1[0], posP1[1]);
                        p1.bank(f.upgradePrice());
                    }
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[S]") {
                    FieldShop f = (FieldShop) map.getField(posP1[0], posP1[1]);
                    System.out.println("You are in shop cell " +
                            posP1[0] + " " + posP1[1] +
                            " This shop has no owner. Would you like to buy  it for " +
                            f.getPrice() +
                            "$? Input ‘Yes’ if you agree or ‘No’ otherwise");
                    do {
                        str = scan.nextLine().toString();
                    } while (!str.equals("Yes") && !str.equals("No"));
                    if (str.equals("Yes") && p1.moneyInfo() < f.getPrice())
                        System.out.println("You cant buy this shop");
                    if (str.equals("Yes") && p1.moneyInfo() > f.getPrice()) {
                        f.changeName("[M]");
                        map.changeMap(f, posP1[0], posP1[1]);
                        p1.updateMoney(-f.getPrice());
                        p1.bank(f.getPrice());
                    }
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[E]") {
                    System.out.println("Just relax there");
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[$]") {
                    if (p1.getDebt() == false) {
                        System.out.println("You are in the bank office. Would you like to get a credit? Input 'Yes' or ’No’");
                        do {
                            str = scan.nextLine();
                        } while (!str.equals("Yes") && !str.equals("No"));
                        double v;
                        if (p1.getBankInfo() <= 0)
                            System.out.println("You cant get money from bank");
                        if (str.equals("Yes") && p1.getBankInfo() > 0) {
                            System.out.println("How many you want to get? Max: " + p1.getBankInfo() * creditCoeff);
                            do {
                                v = scan.nextInt();
                            } while (!(v <= p1.getBankInfo() * creditCoeff && v >= 0));
                            p1.updateMoney(v);
                            p1.adddebtmoney(v);
                            p1.changeDebt(true);
                        }
                    } else {
                        System.out.println("You are a debtor of the bank you need to return the money");
                        p1.changeDebt(false);
                        p1.updateMoney(-p1.getdebtmoney() * debtCoeff);
                    }
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[%]") {
                    System.out.println("You are in penalty cell, you need to pay " + penaltyCoeff * p1.moneyInfo() + "$");
                    p1.updateMoney(-penaltyCoeff * p1.moneyInfo());
                }
                if (p1.getname() == "You" && map.getField(posP1[0], posP1[1]).getName() == "[O]") {
                    FieldShop f = (FieldShop) map.getField(posP1[0], posP1[1]);
                    System.out.println("You are in opponent's shop you need to pay " + f.getCompensation() + "$");
                    p1.updateMoney(-f.getCompensation());
                    p2.updateMoney(f.getCompensation());
                }

                System.out.println(p1.playerInfo());
                System.out.println(p2.playerInfo());
                do {
                    System.out.println("write 'ok' to end the turn");
                    str = scan.nextLine();
                } while (!str.equals("ok"));
            }
            if (j == 1){
                int[] posP2 = p2.getPos();
                System.out.println("Now is bot's turn!");
                cube1 = rnd.nextInt(6) + 1;
                cube2 = rnd.nextInt(6) + 1;
                System.out.println(p2.getname() + " throw " + cube1 + " and " + cube2);
                p2.playerTurn(cube1 + cube2);
                posP2 = p2.getPos();

                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[T]") {
                    int taxiShift = rnd.nextInt(3) + 3;
                    p2.playerTurn(taxiShift);
                    System.out.println("Bot is shifted forward by " + taxiShift + " cells");
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[M]") {
                    FieldShop f = (FieldShop) map.getField(posP2[0], posP2[1]);
                    System.out.println("Bot is in your shop " +
                            posP2[0] + " " + posP2[1] + ". " +
                            "Bot pays you " +
                            +f.getCompensation() + "$");
                    p1.updateMoney(f.getCompensation());
                    p2.updateMoney(-f.getCompensation());
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[O]") {
                    FieldShop f = (FieldShop) map.getField(posP2[0], posP2[1]);
                    System.out.println("Bot is on his shop " +
                            posP2[0] + " " + posP2[1] + ".");
                    if (rnd.nextInt(2) == 0 && p2.moneyInfo() > f.upgradePrice()) {
                        System.out.println("Bot upgrades his shop");
                        p2.updateMoney(-f.upgradePrice());
                        f.upgradeShop();
                        map.changeMap(f, posP2[0], posP2[1]);
                        p2.bank(f.upgradePrice());
                    } else {
                        System.out.println("Bot does not upgrade his shop ");
                    }
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[S]") {
                    FieldShop f = (FieldShop) map.getField(posP2[0], posP2[1]);
                    System.out.println("Bot is in shop cell " +
                            posP2[0] + " " + posP2[1] +
                            " This shop has no owner. " +
                            f.getPrice() + "$");
                    if (rnd.nextInt(2) == 0 && p2.moneyInfo() > f.getPrice()) {
                        System.out.println("Bot buys this shop");
                        f.changeName("[O]");
                        map.changeMap(f, posP2[0], posP2[1]);
                        p2.updateMoney(-f.getPrice());
                        p2.bank(f.getPrice());
                    } else {
                        System.out.println("Bot doesn't buy this shop");
                    }
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[E]") {
                    System.out.println("Bot just relax there");
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[$]") {
                    System.out.println("Bot just relax there [$]");
                }
                if (p2.getname() == "Bot" && map.getField(posP2[0], posP2[1]).getName() == "[%]") {
                    System.out.println("Bot is in penalty cell, Bot need to pay " + penaltyCoeff * p2.moneyInfo() + "$");
                    p2.updateMoney(-penaltyCoeff * p2.moneyInfo());
                }
            }
            j += 1;
        }
        if (p1.moneyInfo() == 0)
            System.out.println(p2.getname() + " wins!!!");
        else
            System.out.println(p1.getname() + " wins!!!");
    }
}