package com.example.lab10;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Animals implements Serializable {
    private List<Animal> animals = null;
    private final static long serialVersionUID = 5265231543584980253L;

    public Animals() {
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setResults(List<Animal> animals) {
        this.animals = animals;
    }

    /**
     * Реализация загрузки данных отдельным потоком из JSON
     **/
    public Animal loadByURL(String url) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        InputStream is = null;
        is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            int cp;
            while ((cp = rd.read()) != -1) {
                jsonIn.append((char) cp);
            }
        } finally {
            is.close();
        }
        ObjectMapper om = new ObjectMapper();
        Animal animal = om.readValue(jsonIn.toString(), Animal.class);
        return animal;
    }

    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Поиск по слову
     **/
    public List<Animal> filterByWord(String str, List<Animal> animals) {
        List<Animal> temp = new ArrayList<>();
        for (Animal word : animals) {
            if (word.getHabitat().toLowerCase().contains(str.toLowerCase()))
                temp.add(word);
        }
        return temp;
    }

    public Animals getRandomAnimals(int n) throws IOException {
        Animals animals = new Animals();
        List<Animal> animalList = new ArrayList<>();
        int i;
        System.out.println("List of zoo animals");
        for (i = 0; i <= n; i++) {
            Animal animal = this.loadByURL("https://zoo-animal-api.herokuapp.com/animals/rand/");
            System.out.println(animal);
            animalList.add(animal);
        }
        animals.setResults(animalList);
        return animals;
    }

    /**
     * Получение одного случайного животного
     **/
    public Animal getRandomAnimal() throws IOException {
        Animal animal = this.loadByURL("https://zoo-animal-api.herokuapp.com/animals/rand/");
        return animal;
    }


    public void sortByName() {
        this.animals.sort(Animal.byName);
    }

    public void sortByNameReverse() {
        this.animals.sort(Animal.byNameReverse);
    }

    public void sortById() {
        this.animals.sort(Animal.byId);
    }

    public void sortByIdReverse() {
        this.animals.sort(Animal.byIdReverse);
    }

    public void sortByWeight() {
        this.animals.sort(Animal.byWeight);
    }

    @Override
    public String toString() {
        return "Animals{" +
                ", animals=" + animals +
                '}' + "\n";
    }
}
