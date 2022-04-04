package org.example.dao;

import org.example.exceptions.PersonNotFoundException;
import org.example.models.Person;
import org.example.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static Connection connection;

    @Autowired
    public PersonDAO(DataBaseService dataBaseService) {
        connection = dataBaseService.openDataBase();
    }

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId( resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge( resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return people;
    }
    public Person getPersonById(Integer id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person WHERE id = ?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new PersonNotFoundException("Person with id: " + id + " does not exists");

            person = new Person(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getInt("age"), resultSet.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person(name, age, email) VALUES (?, ?, ?); ");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Integer id, Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                            "UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?");
            preparedStatement.setInt(4, id);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(Integer id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM person WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}