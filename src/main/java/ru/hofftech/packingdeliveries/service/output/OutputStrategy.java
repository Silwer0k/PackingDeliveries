package ru.hofftech.packingdeliveries.service.output;

import java.util.ArrayList;
import ru.hofftech.packingdeliveries.model.Outputable;

public interface OutputStrategy {
    boolean doOutput(ArrayList<? extends Outputable> toOutput);
}
