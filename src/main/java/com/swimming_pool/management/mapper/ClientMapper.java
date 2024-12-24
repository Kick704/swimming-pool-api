package com.swimming_pool.management.mapper;

import com.swimming_pool.management.model.dto.request.ClientCreationDTO;
import com.swimming_pool.management.model.dto.request.ClientUpdateDTO;
import com.swimming_pool.management.model.dto.response.ClientDetailsDTO;
import com.swimming_pool.management.model.dto.response.ClientSummaryDTO;
import com.swimming_pool.management.model.entity.Client;
import com.swimming_pool.management.util.ClientDataUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Маппер для преобразований между сущностью {@link Client} и связанных с ним DTO
 */
@Mapper(componentModel = "spring", imports = ClientDataUtils.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    /**
     * Маппинг из сущности в DTO с краткой информацией о клиенте
     *
     * @param client сущность клиента
     * @return DTO c краткой информацией о клиенте
     */
    ClientSummaryDTO toSummaryDTO(Client client);

    /**
     * Маппинг из списка сущностей в список DTO с краткой информацией о клиентах
     *
     * @param clients список сущностей клиентов
     * @return список DTO c краткой информацией о клиентах
     */
    List<ClientSummaryDTO> toSummaryDTOList(List<Client> clients);

    /**
     * Маппинг из сущности в DTO с детальной информацией о клиенте
     *
     * @param client сущность клиента
     * @return DTO c детальной информацией о клиенте
     */
    @Mapping(target = "phone", expression = "java(ClientDataUtils.formatPhoneForDTO(client.getPhone()))")
    ClientDetailsDTO toDetailsDTO(Client client);

    /**
     * Маппинг DTO для создания клиента в сущность
     *
     * @param clientCreationDTO DTO для создания клиента
     * @return сущность клиента
     */
    @Mapping(target = "phone", expression = "java(ClientDataUtils.formatPhoneForEntity(clientCreationDTO.getPhone()))")
    Client toEntity(ClientCreationDTO clientCreationDTO);

    /**
     * Обновление сущности на основе DTO, игнорируя null поля
     *
     * @param clientUpdateDTO DTO, проинициализированные поля которого будут обновлены в сущности
     * @param entity обновляемая сущность
     */
    @Mapping(target = "phone", expression = "java(ClientDataUtils.formatPhoneForEntity(clientUpdateDTO.getPhone()))")
    void updateEntityFromDto(ClientUpdateDTO clientUpdateDTO, @MappingTarget Client entity);

}
