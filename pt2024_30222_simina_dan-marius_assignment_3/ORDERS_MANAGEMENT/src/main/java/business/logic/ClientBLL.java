package business.logic;

import data.access.ClientDAO;
import data.access.validators.ClientAgeValidator;
import data.access.validators.EmailValidator;
import data.access.validators.Validator;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class implements validation and database operations for the "client" data type.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new ClientAgeValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Retrieve all records from the database.
     *
     * @return a List with all the found records.
     */
    public List<Client> findClientAll() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("The client table is empty");
        }
        return clients;
    }

    /**
     * Retrieve the record with the ID provided as a parameter from the database.
     *
     * @return the object with the entered ID if it exists, otherwise return null.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, insert it into the database.
     *
     * @return the object if it has been successfully inserted; otherwise, return null.
     */
    public Client insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.insert(client);
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, update it in the database.
     *
     * @return the updated object if the update was successful; otherwise, return null.
     */
    public Client updateClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }

        return clientDAO.update(client);
    }

    /**
     * Delete the object provided as a parameter from the database.
     *
     * @return the deleted object if the deletion was successful; otherwise, return null.
     */
    public Client deleteClient(Client client) {
        return clientDAO.delete(client);
    }
}
