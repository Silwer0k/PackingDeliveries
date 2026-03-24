package ru.hofftech.packingdeliveries.model;

import ru.hofftech.packingdeliveries.model.jsonDataContract.JsonDataContract;

public interface Outputable {
    public String toOutputValue();
    public JsonDataContract toJsonDataContract();
}
