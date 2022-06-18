/**
 * @author <a href="mailto:avtsydypov@edu.hse.ru"> Agvan Tsydypov</a>
 */
package packProgram;
import  java.util.Random;
public class FieldT extends Field{
    public static Random rnd = new Random();
    private int taxiDistance;
    public FieldT(String name)
    {
        super(name);
        taxiDistance = rnd.nextInt(3) + 3;
    }
}
