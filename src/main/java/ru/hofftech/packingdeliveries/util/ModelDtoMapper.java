package ru.hofftech.packingdeliveries.util;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
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

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface ModelDtoMapper {

    @SubclassMapping(source = Truck.class, target = TruckDto.class)
    @SubclassMapping(source = CargoPackage.class, target = CargoPackageDto.class)
    @SubclassMapping(source = CargoPackagePosition.class, target = CargoPackagePositionDto.class)
    BaseDto toDto(Outputable outputable);

    default List<? extends BaseDto> toDtoList(List<? extends Outputable> modelsList) {
        return modelsList.stream().map(this::toDto).toList();
    }

    @InheritInverseConfiguration(name = "toDto")
    Outputable toModel(BaseDto dto);

    default <S extends BaseDto, T extends Outputable> List<T> toModelList(List<S> dtoList, Class<T> tClass) {
        return dtoList.stream()
                .map((elem) -> {
                    //noinspection unchecked
                    return (T) toModel(elem);
                })
                .toList();
    }

    Truck toTruck(TruckDto truckDC);

    TruckDto toTruckDto(Truck truck);

    CargoPackagePosition toCargoPackagePosition(CargoPackagePositionDto cargoPackagePositionDto);

    CargoPackagePositionDto toCargoPackagePositionDto(CargoPackagePosition packagePosition);

    CargoPackage toCargoPackage(CargoPackageDto packageDto);

    CargoPackageDto toCargoPackageDto(CargoPackage cargoPackage);
}
