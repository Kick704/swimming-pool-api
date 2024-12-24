package com.swimming_pool.management.model.entity;

import com.swimming_pool.management.exception_handler.ErrorCode;
import com.swimming_pool.management.exception_handler.SwimmingPoolManagementException;
import com.swimming_pool.management.model.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность записи на бассейн
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    /**
     * Клиент, для которого добавлена запись
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Дата и время посещения бассейна
     */
    @Column(name = "datetime")
    private LocalDateTime dateTime;

    /**
     * Статус записи
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    private Order(@NonNull Builder builder) {
        setClient(builder.client);
        setDateTime(builder.dateTime);
        setStatus(builder.status);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id) && Objects.equals(dateTime, order.dateTime) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }

    /**
     * Билдер для создания сущности записи
     */
    public static final class Builder {
        private Client client;
        private LocalDateTime dateTime;
        private OrderStatus status;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder client(Client val) {
            client = val;
            return this;
        }

        public Builder dateTime(LocalDateTime val) {
            dateTime = val;
            return this;
        }

        public Builder status(OrderStatus val) {
            status = val;
            return this;
        }

        public Order build() {
            if (client == null || dateTime == null || status == null) {
                throw new SwimmingPoolManagementException(
                        ErrorCode.INTERNAL_SERVER_ERROR,
                        "Order: обязательные поля (client, dateTime, status) должны быть заполнены"
                );
            }
            return new Order(this);
        }
    }

}
