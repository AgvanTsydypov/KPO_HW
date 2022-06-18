/**
 * @author <a href="mailto:avtsydypov@edu.hse.ru"> Agvan Tsydypov</a>
 */
package packProgram;

import java.util.Random;
public class Map {
    private static Random rnd = new Random();
    private int _width;
    private int _height;
    private Field[][] map;
    public Map(int width, int height)
    {
        _width = width;
        _height = height;
        map = new Field[_height][_width];
    }
    //возвращает клетку поля
    public Field getField(int x, int y)
    {
        return map[y][x];
    }
    //заполняет поле
    public void fillMap()
    {
        int w = _width - 2;
        int h = _height - 2;
        for (int i = 0;i < _height; i++){
            for (int j = 0;j< _width ;j++){
                map[i][j] = new Field("[E]");
            }
        }
        for (int i = 1;i < _height - 1; i++){
            for (int j = 1;j< _width - 1;j++){
                map[i][j] = new Field("   ");
            }
        }
        fillLine(0);
        fillLine(_height - 1);
        fillColumn(0);
        fillColumn(_width - 1);
    }
    //заполняет верхнюю и нижнию полосу карты
    public void fillLine(int line)
    {
        int w = _width - 2;
        map[line][rnd.nextInt(_width - 2) + 1] = new Field$("[$]");
        int k = rnd.nextInt(2) + 1;
        if(w - k >= 0)
        {
            w -= k;
            do{
                k -= 1;
                int pos = rnd.nextInt(_width - 2) + 1;
                if(map[line][pos].getName() == "[E]")
                    map[line][pos] = new FieldT("[T]");
            }while (k != 0);
        }
        k = rnd.nextInt(2) + 1;
        if(w - k >= 0)
        {
            w -= k;
            do{
                k -= 1;
                int pos = rnd.nextInt(_width - 2) + 1;
                if(map[line][pos].getName() == "[E]")
                    map[line][pos] = new FieldPenaltyCell("[%]");
            }while (k != 0);
        }
        for (int i = 1; i < _width - 1; i++)
        {
            if(map[line][i].getName() == "[E]")
                map[line][i] = new FieldShop("[S]");
        }
    }
    //запполняет правую и левую полосы карты
    public void fillColumn(int column)
    {
        int w = _height - 2;
        map[rnd.nextInt(_height - 2) + 1][column] = new Field$("[$]");
        int k = rnd.nextInt(2) + 1;
        if(w - k >= 0)
        {
            w -= k;
            do{
                k -= 1;
                int pos = rnd.nextInt(_height - 2) + 1;
                if(map[pos][column].getName() == "[E]")
                    map[pos][column] = new FieldT("[T]");
            }while (k != 0);
        }
        k = rnd.nextInt(2) + 1;
        if(w - k >= 0)
        {
            w -= k;
            do{
                k -= 1;
                int pos = rnd.nextInt(_height - 2) + 1;
                if(map[pos][column].getName() == "[E]")
                    map[pos][column] = new FieldPenaltyCell("[%]");
            }while (k != 0);
        }
        for (int i = 1; i < _height - 1; i++)
        {
            if(map[i][column].getName() == "[E]")
                map[i][column] = new FieldShop("[S]");
        }
    }
    //отображает карту
    public void display()
    {
        for (int i = 0;i < _height; i++){
            for (int j = 0;j < _width;j++){
                System.out.print(map[i][j].getName());
            }
            System.out.println();
        }
    }
    //изменяет карту
    public void changeMap(Field f,int x,int y)
    {
        map[y][x] = f;
    }
}