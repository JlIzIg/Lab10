package com.example.lab10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class AnimalsController {

    /**
     * инициализация элементов на форме
     **/
    @FXML
    Button buttonLoadData;
    @FXML
    Button buttonAddData;
    @FXML
    public TableView<Animal> table1;
    private ObservableList<Animal> data;
    @FXML
    TableColumn<Animal, Integer> id;
    @FXML
    TableColumn<Animal, String> name;
    @FXML
    TableColumn<Animal, String> latinName;
    @FXML
    TableColumn<Animal, String> animalType;
    @FXML
    TableColumn<Animal, String> activeTime;
    @FXML
    TableColumn<Animal, Double> lenMin;
    @FXML
    TableColumn<Animal, Double> lenMax;
    @FXML
    TableColumn<Animal, Double> wgMin;
    @FXML
    TableColumn<Animal, Double> wgMax;
    @FXML
    TableColumn<Animal, Double> lifespan;
    @FXML
    TableColumn<Animal, String> habitat;
    @FXML
    TableColumn<Animal, String> diet;
    @FXML
    TableColumn<Animal, String> geoRange;
    @FXML
    TableColumn<Animal, String> imageLink;
    @FXML
    TitledPane header;
    @FXML
    Button Bird;
    @FXML
    Button Mammal;
    @FXML
    Button Reptile;
    @FXML
    Button Marsupial;
    @FXML
    Button Amphibian;
    @FXML
    Button Find;
    @FXML
    TextField nameOfAnimal;
    /**
     * Инициализация переменной соеденения с бд
     **/
    private Connection connection = null;

    /**
     * обработчик события нажатия на кнопку подключиться к бд
     */
    public void ConnectDB(ActionEvent actionEvent) {
        if (dbConnection(Constants.SERVER + "/mydb", "root", "NoFear@Dinar2021"))
            header.setText("Connection is successful!");
        else
            header.setText("Some troubles.Ooops...Connection isn't successful!");
    }

    /**
     * обработчик события нажатия на кнопку загдузки данных с бд в таблицу
     **/
    public void LoadData(ActionEvent actionEvent) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from amimals;");
            data = FXCollections.observableArrayList();
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
            while (resultSet.next()) {
                data.add(new Animal(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("latinName"), resultSet.getString("animalType"), resultSet.getString("activeTime"), resultSet.getDouble("lenMin"), resultSet.getDouble("lenMax"), resultSet.getDouble("wgMin"), resultSet.getDouble("wgMax"), resultSet.getDouble("lifespan"), resultSet.getString("habitat"), resultSet.getString("diet"), resultSet.getString("geoRange"), resultSet.getString("imageLink")));
            }
            table1.setItems(data);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            header.setText("Error while loading data!!!");
            e.printStackTrace();
        }
    }

    /**
     * обработчик события нажатия на кнопку добавления данных в бд (одна случайная запись)
     **/
    public void AddData(ActionEvent actionEvent) {

        String name;
        String latinName;
        String animalType;
        String activeTime;
        Double lenMin;
        Double lenMax;
        Double wgMin;
        Double wgMax;
        Double lifespan;
        String habitat;
        String diet;
        String geoRange;
        String imageLink;

        try {
            Animals animals = new Animals();
            Animal animal = animals.getRandomAnimal();
            name = animal.getName();
            latinName = animal.getLatinName();
            animalType = animal.getAnimalType();
            activeTime = animal.getActiveTime();
            lenMin = animal.getLenMin();
            lenMax = animal.getLenMax();
            wgMin = animal.getWgMin();
            wgMax = animal.getWgMax();
            lifespan = animal.getLifespan();
            habitat = animal.getHabitat();
            diet = animal.getDiet();
            geoRange = animal.getGeoRange();
            imageLink = animal.getImageLink();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "NoFear@Dinar2021");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            connection = getConnection(Constants.URL, properties);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from amimals;");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into amimals(name, latinName, animalType, activeTime, lenMin, lenMax, wgMin, wgMax, lifespan, habitat, diet, geoRange, imageLink) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, latinName);
            preparedStatement.setString(3, animalType);
            preparedStatement.setString(4, activeTime);
            preparedStatement.setDouble(5, lenMin);
            preparedStatement.setDouble(6, lenMax);
            preparedStatement.setDouble(7, wgMin);
            preparedStatement.setDouble(8, wgMax);
            preparedStatement.setDouble(9, lifespan);
            preparedStatement.setString(10, habitat);
            preparedStatement.setString(11, diet);
            preparedStatement.setString(12, geoRange);
            preparedStatement.setString(13, imageLink);
            int result = preparedStatement.executeUpdate();
            resultSet.close();
            statement.close();
            LoadData(actionEvent);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    // connection.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * обработчик события закрытия соединения
     **/
    public void Exit(ActionEvent actionEvent) {
        if (connection != null) {
            try {
                connection.close();
                header.setText("Disconnected successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * метод подключение к бд
     */
    private boolean dbConnection(String conn, String login, String password) {
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            String url = "jdbc:mysql://" + conn;
            Properties properties = new Properties();
            properties.setProperty("user", login);
            properties.setProperty("password", password);
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            connection = getConnection(url, properties);
            System.out.println("Connection ID" + connection.toString());
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * обработчик события выбора животных разных типов
     */
    public void select(ActionEvent actionEvent) {
        String selectBirds = "select * from amimals where animalType = 'Bird'; ";
        String selectMammals = "select * from amimals where animalType ='Mammal'; ";
        String selectReptiles = "select * from amimals where animalType ='Reptile'; ";
        String selectMarsupials = "select * from amimals where animalType ='Marsupial'; ";
        String selectAmphibians = "select * from amimals where animalType ='Amphibian'; ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            if (actionEvent.getSource() == Bird) {
                resultSet = statement.executeQuery(selectBirds);
            } else if (actionEvent.getSource() == Mammal) {
                resultSet = statement.executeQuery(selectMammals);
            } else if (actionEvent.getSource() == Reptile) {
                resultSet = statement.executeQuery(selectReptiles);
            } else if (actionEvent.getSource() == Marsupial) {
                resultSet = statement.executeQuery(selectMarsupials);
            } else if (actionEvent.getSource() == Amphibian) {
                resultSet = statement.executeQuery(selectAmphibians);
            }
            data = FXCollections.observableArrayList();
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
            while (resultSet.next()) {
                data.add(new Animal(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("latinName"), resultSet.getString("animalType"), resultSet.getString("activeTime"), resultSet.getDouble("lenMin"), resultSet.getDouble("lenMax"), resultSet.getDouble("wgMin"), resultSet.getDouble("wgMax"), resultSet.getDouble("lifespan"), resultSet.getString("habitat"), resultSet.getString("diet"), resultSet.getString("geoRange"), resultSet.getString("imageLink")));
            }
            table1.setItems(data);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            header.setText("Error while loading data!!!");
            e.printStackTrace();
        }
    }

    /**
     * удаление последней записи
     **/
    public void delete(ActionEvent actionEvent) throws SQLException {
        String deleteString = "delete from amimals where id = (select x.id from (select max(t.id) as id  from amimals t) x);";
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "NoFear@Dinar2021");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            connection = getConnection(Constants.URL, properties);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from amimals;");
            PreparedStatement preparedStatement = connection.prepareStatement(deleteString);
            int result = preparedStatement.executeUpdate();
            resultSet.close();
            statement.close();
            LoadData(actionEvent);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    // connection.close();
                } catch (Exception e) {
                    header.setText("Error while deleting data!!!");
                }
            }
        }
    }

    /**
     * бработчик события поиск информации по названию животного
     */
    public void find(ActionEvent actionEvent) {

        String animalName = nameOfAnimal.getText().trim();
        String selectAnimal = "select * from amimals where name = '" + animalName + "';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            resultSet = statement.executeQuery(selectAnimal);
            data = FXCollections.observableArrayList();
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
            while (resultSet.next()) {
                data.add(new Animal(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("latinName"), resultSet.getString("animalType"), resultSet.getString("activeTime"), resultSet.getDouble("lenMin"), resultSet.getDouble("lenMax"), resultSet.getDouble("wgMin"), resultSet.getDouble("wgMax"), resultSet.getDouble("lifespan"), resultSet.getString("habitat"), resultSet.getString("diet"), resultSet.getString("geoRange"), resultSet.getString("imageLink")));
            }
            table1.setItems(data);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            header.setText("Error while loading data!!!");
            e.printStackTrace();
        }
    }
}