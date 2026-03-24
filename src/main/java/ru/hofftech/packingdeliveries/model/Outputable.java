package ru.hofftech.packingdeliveries.model;

import ru.hofftech.packingdeliveries.model.jsonDataContract.JsonDataContract;

public interface Outputable {
    String toOutputValue();
    JsonDataContract toJsonDataContract();
}
