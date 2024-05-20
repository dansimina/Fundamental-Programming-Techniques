package data.access;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * Generic class implementing the operations performed on the database
 *
 * @param <T> the type of objects processed
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM `");
        sb.append(type.getSimpleName());
        sb.append("`");
        return sb.toString();
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Retrieve all records from the database.
     *
     * @return a List with all the found records.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        List<T> list = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    /**
     * Retrieve the record with the ID provided as a parameter from the database.
     *
     * @return the object with the entered ID if it exists, otherwise return null.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).getFirst();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    private List<T> createObjectsWithSetters(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == type.getDeclaredFields().length)
                break;
        }
        if (ctor != null) {
            try {
                while (resultSet.next()) {
                    ctor.setAccessible(true);
                    Object[] objects = new Object[type.getDeclaredFields().length];
                    int i = 0;
                    for (Field field : type.getDeclaredFields()) {
                        String fieldName = field.getName();
                        objects[i] = resultSet.getObject(fieldName);
                        i++;
                    }
                    T instance = (T) ctor.newInstance(objects);
                    list.add(instance);
                }
            } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                     InvocationTargetException | SQLException e) {
                e.printStackTrace();
            }
            return list;
        } else {
            return createObjectsWithSetters(resultSet);
        }
    }

    private String createInsertIntoQuery(T t) {
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!fieldName.equals("id")) {
                fieldNames.append(",");
                fieldNames.append(fieldName);
                try {
                    Object fieldValue = field.get(t);
                    fieldValues.append(",\"");
                    fieldValues.append(fieldValue);
                    fieldValues.append("\"");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `");
        sb.append(type.getSimpleName());
        sb.append("`(");
        sb.append(fieldNames.substring(1));
        sb.append(") ");
        sb.append("VALUES");
        sb.append("(");
        sb.append(fieldValues.substring(1));
        sb.append(")");
        return sb.toString();
    }

    private T insertId(T t, int id) {
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == type.getDeclaredFields().length)
                break;
        }
        if (ctor != null) {
            ctor.setAccessible(true);
            Object[] objects = new Object[type.getDeclaredFields().length];

            int i = 0;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (!field.getName().equals("id")) {
                        objects[i] = field.get(t);
                    } else {
                        objects[i] = id;
                    }
                    i++;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                return (T) ctor.newInstance(objects);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * Insert the object provided as a parameter into the database.
     *
     * @return the object if it has been successfully inserted; otherwise, return null.
     */
    public T insert(T t) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement insertStatement = null;

        String query = createInsertIntoQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertStatement.executeUpdate();
            resultSet = insertStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return insertId(t, resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    private String createUpdateQuery(T t) {
        StringBuilder setString = new StringBuilder();
        int id = -1;

        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!fieldName.equals("id")) {
                try {
                    setString.append(", ");
                    setString.append(fieldName);
                    setString.append(" = ");
                    setString.append("\"");
                    setString.append(field.get(t));
                    setString.append("\"");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    id = field.getInt(t);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName());
        sb.append("` SET ");
        sb.append(setString.substring(2));
        sb.append(" WHERE ");
        sb.append("id = \"");
        sb.append(id);
        sb.append("\"");
        return sb.toString();
    }

    /**
     * Update the data for the object provided as a parameter in the database.
     *
     * @return the updated object if the update was successful; otherwise, return null.
     */
    public T update(T t) {
        // TODO:
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement updateStatement = null;

        String query = createUpdateQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            updateStatement = connection.prepareStatement(query);
            updateStatement.executeUpdate();

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createDeleteQuery(T t) {
        int id;
        try {
            Field field = t.getClass().getDeclaredField("id");
            field.setAccessible(true);
            id = field.getInt(t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE ");
        sb.append("id = \"");
        sb.append(id);
        sb.append("\"");
        return sb.toString();
    }

    /**
     * Delete the object provided as a parameter from the database.
     *
     * @return the deleted object if the deletion was successful; otherwise, return null.
     */
    public T delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement updateStatement = null;

        String query = createDeleteQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            updateStatement = connection.prepareStatement(query);
            updateStatement.executeUpdate();

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
