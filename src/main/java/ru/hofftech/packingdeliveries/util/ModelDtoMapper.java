package ru.hofftech.packingdeliveries.util;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import ru.hofftech.packingdeliveries.model.CargoPackage;
import ru.hofftech.packingdeliveries.model.CargoPackagePosition;
import ru.hofftech.packingdeliveries.model.Outputable;
import ru.hofftech.packingdeliveries.model.Truck;
import ru.hofftech.packingdeliveries.model.dto.BaseDto;
import ru.hofftech.packingdeliveries.model.dto.CargoPackageDto;
import ru.hofftech.packingdeliveries.model.dto.CargoPackagePositionDto;
import ru.hofftech.packingdeliveries.model.dto.TruckDto;

/**
 * Интерфейс маппера для преобразования моделей предметной области в DTO и обратно.
 * <p>
 * Использует механизмы MapStruct для автоматической генерации реализации.
 * Поддерживает полиморфное сопоставление через {@link SubclassMapping} для иерархий
 * интерфейсов {@link Outputable} и {@link BaseDto}.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface ModelDtoMapper {

    /**
     * Преобразует любую бизнес-модель, реализующую {@link Outputable}, в соответствующий DTO.
     *
     * @param outputable объект бизнес-логики
     * @return объект переноса данных (DTO)
     */
    @SubclassMapping(source = Truck.class, target = TruckDto.class)
    @SubclassMapping(source = CargoPackage.class, target = CargoPackageDto.class)
    @SubclassMapping(source = CargoPackagePosition.class, target = CargoPackagePositionDto.class)
    BaseDto toDto(Outputable outputable);

    /**
     * Преобразует список моделей в список соответствующих DTO.
     *
     * @param modelsList список объектов бизнес-моделей
     * @return список DTO
     */
    default List<? extends BaseDto> toDtoList(List<? extends Outputable> modelsList) {
        return modelsList.stream().map(this::toDto).toList();
    }

    /**
     * Выполняет обратное преобразование из DTO в бизнес-модель.
     * Использует инвертированную конфигурацию метода {@link #toDto}.
     *
     * @param dto объект переноса данных
     * @return восстановленный объект бизнес-модели
     */
    @InheritInverseConfiguration(name = "toDto")
    Outputable toModel(BaseDto dto);

    /**
     * Преобразует список DTO в список бизнес-моделей заданного типа.
     *
     * @param dtoList список DTO
     * @param tClass  целевой класс модели
     * @param <S>     тип исходного DTO
     * @param <T>     тип целевой модели
     * @return список моделей типа T
     */
    default <S extends BaseDto, T extends Outputable> List<T> toModelList(List<S> dtoList, Class<T> tClass) {
        return dtoList.stream()
                .map((elem) -> {
                    //noinspection unchecked
                    return (T) toModel(elem);
                })
                .toList();
    }

    /** Преобразует DTO грузовика в модель {@link Truck}. */
    Truck toTruck(TruckDto truckDC);

    /** Преобразует модель {@link Truck} в {@link TruckDto}. */
    TruckDto toTruckDto(Truck truck);

    /** Преобразует DTO позиции в модель {@link CargoPackagePosition}. */
    CargoPackagePosition toCargoPackagePosition(CargoPackagePositionDto cargoPackagePositionDto);

    /** Преобразует модель позиции в {@link CargoPackagePositionDto}. */
    CargoPackagePositionDto toCargoPackagePositionDto(CargoPackagePosition packagePosition);

    /** Преобразует DTO посылки в модель {@link CargoPackage}. */
    CargoPackage toCargoPackage(CargoPackageDto packageDto);

    /**
     * Преобразует модель посылки в DTO.
     * <p>
     * Поле {@code shape} в DTO заполняется результатом вызова метода {@link CargoPackage#packageShape()}.
     *
     * @param cargoPackage исходная модель посылки
     * @return заполненный объект {@link CargoPackageDto}
     */
    @Mapping(target = "shape", expression = "java(cargoPackage.packageShape())")
    CargoPackageDto toCargoPackageDto(CargoPackage cargoPackage);
}
