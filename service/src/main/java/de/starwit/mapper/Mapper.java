package de.starwit.mapper;

import de.starwit.persistence.entity.AbstractEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class Mapper {

    static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

    @SuppressWarnings("deprecation")
    public static <E extends AbstractEntity<Long>, T extends AbstractEntity<Long>> T convert(
        E entity,
        Class<T> clazz,
        String... ignoredFields
    ) {
        T dto;
        try {
            dto = clazz.newInstance();
            if (entity != null && dto != null) {
                BeanUtils.copyProperties(entity, dto, ignoredFields);
                dto.setId(entity.getId());
                return dto;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Error creating new instance of dto");
        }
        return null;
    }

    public static <E extends AbstractEntity<Long>, T extends AbstractEntity<Long>> List<T> convertList(
        List<E> entities,
        Class<T> clazz,
        String... ignoredFields
    ) {
        if (entities != null) {
            List<T> dtos = new ArrayList<T>();
            for (E entity : entities) {
                dtos.add(Mapper.convert(entity, clazz, ignoredFields));
            }
            return dtos;
        }
        return null;
    }

    public static <E extends AbstractEntity<Long>, T extends AbstractEntity<Long>> Set<T> convertSet(
        Set<E> entities,
        Class<T> clazz,
        String... ignoredFields
    ) {
        if (entities != null) {
            Set<T> dtos = new HashSet<T>();
            for (E entity : entities) {
                dtos.add(Mapper.convert(entity, clazz, ignoredFields));
            }
            return dtos;
        }
        return null;
    }
}
