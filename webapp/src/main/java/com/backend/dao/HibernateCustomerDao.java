package com.backend.dao;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.entity.Customer;
import com.backend.sorting.SortOrder;

@Repository
public class HibernateCustomerDao implements CustomerDao {

    private EntityManager entityManager;

    @Autowired
    public HibernateCustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> getAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery("SELECT * FROM customer", Customer.class).getResultList();
    }

    @Override
    public List<Customer> getAll(SortOrder sortOrder) {
        // sortOrder#sortParams{new SortParam(first_name, true), new SortParam(last_name, false)}
        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery("SELECT * FROM customer" + sortOrder.getSqlOrder(),Customer.class)
                .getResultList();
    }

    @Override
    public List<Customer> getAll(SortOrder sortOrder, int offset, int recordsPerPage) {
        final String sql = "SELECT * FROM customer " + sortOrder.getSqlOrder() + " LIMIT :offset, :rowcount";
        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery(sql, Customer.class)
                .setParameter("offset", offset, IntegerType.INSTANCE)
                .setParameter("rowcount", recordsPerPage, IntegerType.INSTANCE)
                .getResultList();
    }

    @Override
    public int countTotalRecords() {
        Session session = entityManager.unwrap(Session.class);
        // INT >>> BigInteger
        // Number >>> BigDecimal
        return (int)session.createNativeQuery("SELECT COUNT(*) counter FROM customer")
                .addScalar("counter", IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public Customer get(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Customer.class, id);
    }

    @Override
    public void save(Customer customer) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(customer);
    }

    @Override
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.createQuery("DELETE FROM Customer WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Customer> search(String keyword) {
        // search by firstName || lastName with case insensitive
        Session session = entityManager.unwrap(Session.class);

        // additional: mark insensitive
        String sql = "SELECT * FROM customer \n"
                + "WHERE first_name LIKE :keyword\n"
                + "OR last_name LIKE :keyword";

        return session.createNativeQuery(sql, Customer.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

}