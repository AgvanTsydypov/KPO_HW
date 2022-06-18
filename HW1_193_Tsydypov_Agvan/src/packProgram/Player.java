/**
 * @author <a href="mailto:avtsydypov@edu.hse.ru"> Agvan Tsydypov</a>
 */
package packProgram;

public class Player {
    int[] position = new int[2];
    private double _money;
    private int _w;
    private int _h;
    private int _lenght;
    private int current;
    private double bankInfo;
    private boolean debt;
    private double debtMoney;
    private String _name;
    public Player(int w, int h, double money, String name)
    {
        _money = money;
        _name = name;
        position[0] = 0;
        position[1] = 0;
        _w = w;
        _h = h;
        _lenght = _w*2 + _h*2 - 4;
        current = 0;
        bankInfo = 0;
        debt = false;
        debtMoney = 0;
    }
    //одолженные деньги
    public void adddebtmoney(double dm)
    {
        debtMoney = dm;
    }
    //возвращает одолженные деньги
    public double getdebtmoney()
    {
        return debtMoney;
    }
    //является ли данный игрок должником банка
    public boolean getDebt()
    {
        return debt;
    }
    //изменяет статус должника
    public void changeDebt(boolean d)
    {
        debt = d;
    }
    //записывает истторию покупок
    public void bank(double s)
    {
        bankInfo += s;
    }
    //возвращает число денег потрасенные на улучшения и покупки
    public double getBankInfo()
    {
        return bankInfo;
    }
    //возвращает имяя игрока
    public String getname()
    {
        return _name;
    }
    //метод считает координаты на игровой доске
    public void playerTurn(int cubecube)
    {
        current += cubecube;
        if(current >= _lenght)
            current -= _lenght;
        if(current >= 0 && current <= _w - 1)
        {
            position[1] = 0;
            position[0] = current;
        }
        if(current > _w - 1 && current <= _w + _h - 2)
        {
            position[0] = _w - 1;
            position[1] = current - _w + 1;
        }
        if(current > _w + _h - 2 && current <= _w + _h + _w - 3)
        {
            position[1] = _h - 1;
            position[0] = _w - (current - _w - _h + 2) - 1;
        }
        if(current > _w + _h + _w - 3)
        {
            position[1] = _h - (current - _w - _h - _w + 3) - 1;
            position[0] = 0;
        }
    }
    //возвращает позицию на игровой доске
    public int[] getPos()
    {
        return position;
    }
    //обновлляет количество денег
    public void updateMoney(double m) {
        _money += m;
        if(_money < 0)
            _money = 0;
    }
    //возвращает деньги
    public double moneyInfo()
    {
        return _money;
    }
    //коротккаяяя информация о игроке
    public String playerInfo()
    {
        if (_name == "You")
            return _name + "r position is " + position[0] + " " + position[1] + "; Money:" + _money;
        else
            return _name + " position is " + position[0] + " " + position[1] + "; Money:" + _money;
    }
}