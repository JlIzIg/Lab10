package com.example.lab10;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Animals implements Serializable { //NOPMD - suppressed RedundantFieldInitializer - TODO explain reason for suppression
    private final static long serialVersionUID = 5265231543584980253L;
    private List<Animal> animals;

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
        final InputStream inputStream = new URL(url).openStream();
        try { //NOPMD - suppressed UseTryWithResources - TODO explain reason for suppression
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            int i;
            while ((i = bufferedReader.read()) != -1) {
                jsonIn.append((char) i);
            }
        } finally {

            inputStream.close();
        }
        ObjectMapper om = new ObjectMapper();
        Animal animal = om.readValue(jsonIn.toString(), Animal.class);
        return animal;
    }

    /**
     * обавление экземпляра класса в ArrayList
     */
    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Поиск по слову
     **/
    public List<Animal> filterByWord(String str, List<Animal> animals) {
        List<Animal> temp = new ArrayList<>();
        for (Animal word : animals) {
            if (word.getHabitat().toLowerCase().contains(str.toLowerCase())) {
                temp.add(word);
            }
        }
        return temp;
    }

    public Animals getRandomAnimals(int n) throws IOException {
        Animals animals = new Animals();
        List<Animal> animalList = new ArrayList<>();
        int i;
        for (i = 0; i <= n; i++) {
            Animal animal = this.loadByURL("https://zoo-animal-api.herokuapp.com/animals/rand/");
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
