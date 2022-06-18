import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchLogic {
    //метод изменяющий таблицу по показаниям в поиске
    public static ObservableList<Person> fillTable(String str)
    {
        ObservableList<Person> personsSearch = FXCollections.observableArrayList();
        String[] strMas = str.split(" ");
        for (int i = 0; i < Controller.persons.size(); i++)
        {
            String fio = Controller.persons.get(i).getName() + " " + Controller.persons.get(i).getLastname() + " " + Controller.persons.get(i).getSurname();
            int count = 0;
            for(int j = 0; j < strMas.length; j++)
                if(fio.contains(strMas[j]))
                    count += 1;
                if(count == strMas.length)
                    personsSearch.add(Controller.persons.get(i));
        }
        return personsSearch;
    }
}
