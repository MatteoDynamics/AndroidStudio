import java.util.Random;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
interface Player
{
    public Choice show();
}
enum Choice
{
    Papier,
    Kamien,
    Nozyce
}
class Donkey implements Player
{
    public int choice = 0;
    public Choice show()
    {
        System.out.printf("Kamien\n");
        return Choice.Kamien;
    }

}
class Monkey implements Player
{
    private int choice = 0;
    public Choice show()
    {
        Random rand = new Random();
        int n = rand.nextInt(33);
        if (n<=11) {
            System.out.println("Papier\n");
            return Choice.Papier;
        }
         else if (n <= 22 && n > 11) {
            System.out.println("Kamien\n");
            return Choice.Kamien;
        } else {
            System.out.println("Nozyce\n");
            return Choice.Nozyce;
        }
    }

}

class Parrot implements Player
{
    private int choice = 0;
    public Choice show()
    {
        Random rand = new Random();
        int n = rand.nextInt(2);
        if (n !=1) {
            System.out.println("Papier\n");
            return Choice.Papier;
        }
        System.out.println("Kamien\n");
        return Choice.Kamien;
    }
}
class Judge {
    private Player p1;
    private Player p2;

    Judge(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void play() {
        System.out.println("Player 1's choice:");
        Choice res1 = p1.show();
        System.out.println("Player 2's choice:");
        Choice res2 = p2.show();
        if(res1 == res2) {
            System.out.println("Draw");
        } else if (res1 == Choice.Papier && res2 == Choice.Kamien) {
            System.out.println("Player 1 wins");
        } else if (res1 == Choice.Kamien && res2 == Choice.Nozyce) {
            System.out.println("Player 1 wins");
        } else if (res1 == Choice.Nozyce && res2 == Choice.Papier) {
            System.out.println("Player 1 wins");
        }else System.out.println("Player 2 wins");
    }
}
public class Main {
    public static void main(String[] args) {

        Donkey d = new Donkey();
        Monkey m = new Monkey();
        Parrot p = new Parrot();
        Judge j = new Judge(m,m);
        j.play();
    }
}