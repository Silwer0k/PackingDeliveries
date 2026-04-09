package ru.hofftech.packingdeliveries.service.output;

import java.util.List;
import ru.hofftech.packingdeliveries.model.Outputable;

/**
 * Интерфейс, определяющий стратегию вывода данных.
 * <p>
 * Служит для реализации различных механизмов отображения или сохранения объектов,
 * поддерживающих интерфейс {@link Outputable} (например, вывод в JSON-файл,
 * текстовый отчет или графический интерфейс мессенджера).
 */
public interface OutputStrategy {
    /**
     * Выполняет вывод переданного списка объектов.
     *
     * @param toOutput список объектов, реализующих {@link Outputable},
     *                 которые необходимо вывести в поток вывода
     * @return {@code true}, если операция вывода завершена успешно;
     *         {@code false} в случае возникновения ошибок (например, проблем с доступом к файлу)
     */
    boolean doOutput(List<? extends Outputable> toOutput);
}
