package data.access;

import java.util.List;

import model.Client;

public class ClientDAO extends AbstractDAO<Client> {
    public ClientDAO() {
        super();
    }

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    @Override
    public Client findById(int id) {
        return super.findById(id);
    }

    @Override
    public Client insert(Client client) {
        return super.insert(client);
    }

    @Override
    public Client update(Client client) {
        return super.update(client);
    }

    @Override
    public Client delete(Client client) {
        return super.delete(client);
    }
}
