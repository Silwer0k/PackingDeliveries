package ru.hofftech.packingdeliveries.service.output;

import java.util.List;
import ru.hofftech.packingdeliveries.model.Outputable;

/**
 * Стратегия вывода данных в консоль.
 * <p>
 * Реализует последовательный вывод текстового представления каждого объекта
 * из списка в стандартный поток {@link System#out}.
 */
public class ConsoleOutput implements OutputStrategy {

    /**
     * Выводит список объектов в консоль.
     * <p>
     * Для каждого элемента списка вызывается метод {@link Outputable#toOutputValue()}.
     *
     * @param toOutput список объектов, реализующих {@link Outputable}
     * @return всегда возвращает {@code true}, так как вывод в консоль считается гарантированным
     */
    @Override
    public boolean doOutput(List<? extends Outputable> toOutput) {
        toOutput.forEach(obj -> System.out.println(obj.toOutputValue()));
        return true;
    }
}
