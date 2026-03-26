package ru.hofftech.packingdeliveries.service.output;

import java.util.List;
import ru.hofftech.packingdeliveries.model.Outputable;

public class ConsoleOutput implements OutputStrategy {

    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        toOutput.forEach(obj -> System.out.println(obj.toOutputValue()));
        return true;
    }
}
