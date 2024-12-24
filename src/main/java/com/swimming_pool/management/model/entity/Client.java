package com.swimming_pool.management.model.entity;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

/**
 * Сущность клиента бассейна
 */
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    /**
     * ФИО клиента
     */
    @Column(name = "name")
    private String name;

    /**
     * Номер телефона клиента
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Email клиента
     */
    @Column(name = "email")
    private String email;

    /**
     * Список записей клиента
     */
    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() {
    }

    private Client(Builder builder) {
        setName(builder.name);
        setPhone(builder.phone);
        setEmail(builder.email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) &&
                Objects.equals(phone, client.phone) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, email);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Билдер для создания сущности клиента
     */
    public static final class Builder {
        private String name;
        private String phone;
        private String email;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Client build() {
            if (name == null || phone == null) {
                throw new SwimmingPoolManagementException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "Client: обязательные поля (name, phone) должны быть заполнены"
                );
            }
            return new Client(this);
        }
    }

}
