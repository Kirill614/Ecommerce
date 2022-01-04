package kirill.ecommerce.converter;

import java.util.function.Function;

public class BaseConverter<E, D>{
    private Function<E, D> fromEntity;
    private Function<D, E> fromDto;

    public BaseConverter(Function<E, D> fromEntity, Function<D, E> fromDto){
        this.fromEntity = fromEntity;
        this.fromDto = fromDto;
    }

    public D convertFromEntity(E entity){
        return fromEntity.apply(entity);
    }

    public E convertFromDto(D dto){
        return fromDto.apply(dto);
    }
}
