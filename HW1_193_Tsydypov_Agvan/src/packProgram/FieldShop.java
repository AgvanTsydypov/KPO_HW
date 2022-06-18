/**
 * @author <a href="mailto:avtsydypov@edu.hse.ru"> Agvan Tsydypov</a>
 */
package packProgram;
import java.util.Random;
public class FieldShop extends Field {
    public static Random rnd = new Random();
    private double N;
    private double K;
    private double compensationCoeff;
    private double improvementCoeff;

    public FieldShop(String name) {
        super(name);
        N = rnd.nextInt(451) + 50;
        K = (rnd.nextInt(5) + 5 /10.0) * N;
        compensationCoeff = (rnd.nextInt(10) + 1)/10.0;
        improvementCoeff = (rnd.nextInt(20) + 1)/10.0;
    }
    //возвращает стоимоссть магазина
    public double getPrice()
    {
        return N;
    }
    //улучшение магазина
    public void upgradeShop()
    {
        N = N + improvementCoeff * N;
        K = K + compensationCoeff * K;
    }
    //увелечение цены
    public double upgradePrice()
    {
        return N * improvementCoeff;
    }
    //компенсация магазина
    public double getCompensation()
    {
        return K;
    }
    //смена названия магазина
    public void changeName(String str) {
        _name = str;
    }
}