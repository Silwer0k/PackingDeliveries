package ru.hofftech.packingdeliveries.service.output;

import java.util.List;
import ru.hofftech.packingdeliveries.model.Outputable;

public interface OutputStrategy {
    boolean doOutput(List<? extends Outputable> toOutput);
}
