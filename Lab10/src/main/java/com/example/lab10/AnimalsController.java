package com.example.lab10;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AnimalsController { //NOPMD - suppressed AtLeastOneConstructor - TODO explain reason for suppression
    /**
     * инициализация элемента таблица
     **/
    @FXML
    public TableView<Animal> table1;

    /**
     * инициализация кнопки добавления данных
     **/
    @FXML
    Button buttonAddData;

    /***инициализация колонки id**/
    @FXML
    TableColumn<Animal, Integer> id;
    /***инициализация колонки name**/
    @FXML
    TableColumn<Animal, String> name;

    /***инициализация колонки latinName**/
    @FXML
    TableColumn<Animal, String> latinName;

    /***инициализация колонки animalType**/

    @FXML
    TableColumn<Animal, String> animalType;

    /***инициализация колонки activeTime**/
    @FXML
    TableColumn<Animal, String> activeTime;

    /***инициализация колонки lenMin**/
    @FXML
    TableColumn<Animal, Double> lenMin;
    /***инициализация колонки lenMax**/
    @FXML
    TableColumn<Animal, Double> lenMax;
    /***инициализация колонки wgMin**/
    @FXML
    TableColumn<Animal, Double> wgMin;

    /***инициализация колонки wgMax**/
    @FXML
    TableColumn<Animal, Double> wgMax;
    /***инициализация колонки lifespan**/
    @FXML
    TableColumn<Animal, Double> lifespan;
    /***инициализация колонки habitat**/
    @FXML
    TableColumn<Animal, String> habitat;
    /***инициализация колонки diet**/
    @FXML
    TableColumn<Animal, String> diet;
    /***инициализация колонки geoRange**/
    @FXML
    TableColumn<Animal, String> geoRange;
    /***инициализация колонки imageLink**/
    @FXML
    TableColumn<Animal, String> imageLink;

    /***инициализация элемента header**/
    @FXML
    TitledPane header;

    /***инициализация кнопки вывода птиц**/
    @FXML
    Button bird;
    /***инициализация кнопки вывода млекопетающих**/
    @FXML
    Button mammal;
    /***инициализация кнопки вывода рептилий**/
    @FXML
    Button reptile;
    /***инициализация кнопки вывода marsupial**/
    @FXML
    Button marsupial;

    /***инициализация кнопки вывода амфибий**/
    @FXML
    Button amphibian;

    /***инициализация кнопки поиска животного**/
    @FXML
    Button findButton;
    /***инициализация поля для поиска животного**/
    @FXML
    TextField nameOfAnimal;
    /***инициализация кнопки поиска загрузки данных**/

    @FXML
    Button buttonLoadData;

    /**
     * экземпляр класса DataBaseAnimals
     **/
    DataBaseAnimals dataBaseAnimals;
    /***поле connection*/
    private Connection connection;

    /**
     * обработчик события нажатия на кнопку подключиться к бд
     */
    public void connectDB(ActionEvent actionEvent) {
        if (dataBaseAnimals.dbConnection(Constants.SERVER + "/mydb", "root", "NoFear@Dinar2021"))
            header.setText("Connection is successful!");
        else
            header.setText("Some troubles.Ooops...Connection isn't successful!");
    }

    /**
     * метод заполняющий таблицу значениями
     **/
    public void setTable1(ObservableList data) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        latinName.setCellValueFactory(new PropertyValueFactory<>("latinName"));
        animalType.setCellValueFactory(new PropertyValueFactory<>("animalType"));
        activeTime.setCellValueFactory(new PropertyValueFactory<>("activeTime"));
        lenMin.setCellValueFactory(new PropertyValueFactory<>("lenMin"));
        lenMax.setCellValueFactory(new PropertyValueFactory<>("lenMax"));
        wgMin.setCellValueFactory(new PropertyValueFactory<>("wgMin"));
        wgMax.setCellValueFactory(new PropertyValueFactory<>("wgMax"));
        lifespan.setCellValueFactory(new PropertyValueFactory<>("lifespan"));
        habitat.setCellValueFactory(new PropertyValueFactory<>("habitat"));
        diet.setCellValueFactory(new PropertyValueFactory<>("diet"));
        geoRange.setCellValueFactory(new PropertyValueFactory<>("geoRange"));
        imageLink.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        table1.setItems(data);
    }

    /**
     * обработчик события нажатия на кнопку загдузки данных с бд в таблицу
     **/
    public void loadData(ActionEvent actionEvent) {
        dataBaseAnimals.loadData();
        setTable1(dataBaseAnimals.getData());
    }

    /**
     * обработчик события нажатия на кнопку добавления данных в бд (одна случайная запись)
     **/
    public void addData(ActionEvent actionEvent) {
        dataBaseAnimals.addData();
        loadData(actionEvent);

    }

    /**
     * обработчик события закрытия соединения
     **/
    public void exit(ActionEvent actionEvent) throws SQLException {
        dataBaseAnimals.close();
        header.setText("Disconnected successfully");
    }

    /**
     * обработчик события выбора животных разных типов
     */
    public void select(ActionEvent actionEvent) {
        dataBaseAnimals.select(((Button) actionEvent.getSource()).getText());
        setTable1(dataBaseAnimals.getData());
    }

    /**
     * удаление последней записи
     **/
    public void delete(ActionEvent actionEvent) throws SQLException {
        dataBaseAnimals.delete();
        loadData(actionEvent);

    }

    /**
     * бработчик события поиск информации по названию животного
     */
    public void find(ActionEvent actionEvent) {
        String animalName = nameOfAnimal.getText().trim();
        dataBaseAnimals.find(animalName);
        setTable1(dataBaseAnimals.getData());
    }

    /**
     * инициализатор
     **/
    public void initialize() {
        connection = null;
        dataBaseAnimals = new DataBaseAnimals();
    }
}