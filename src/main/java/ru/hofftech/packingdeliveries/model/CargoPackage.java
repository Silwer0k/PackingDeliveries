package ru.hofftech.packingdeliveries.model;

import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hofftech.packingdeliveries.util.Default;

/**
 * Представляет модель посылки, имеющую определенную форму,
 * размеры и символьный маркер для визуализации.
 */
public class CargoPackage implements Outputable {
    private static final Logger log = LoggerFactory.getLogger(CargoPackage.class);
    private static final char markerForCustomShape = 'x';
    private final char marker;
    private final int width;
    private final int depth;
    private final char[][] packageMatrix;
    private final String name;

    /**
     * Создает стандартную посылку на основе её веса (числового представления маркера).
     * <p>
     * Форма посылки заполняется автоматически: количество заполненных ячеек
     * в матрице соответствует числовому значению символа маркера. Имя генерируется
     * вида "Посылка тип *маркер*"
     *
     * @param marker символ, обозначающий тип и вес посылки (например, '3')
     * @param width  ширина матрицы посылки
     * @param depth  глубина (высота) матрицы посылки
     */
    public CargoPackage(char marker, int width, int depth) {
        this.marker = marker;
        this.width = width;
        this.depth = depth;
        this.name = generateDefaultName(marker);
        int weight = Character.getNumericValue(this.marker);

        int curWeight = 0;
        packageMatrix = new char[this.depth][this.width];
        for (int row = this.depth - 1; row >= 0; row--) {
            for (int col = 0; col < this.width; col++) {
                curWeight++;
                packageMatrix[row][col] = (curWeight <= weight) ? this.marker : Truck.emptySpaceMarker;
            }
        }
        log.info(
                "Создан объект посылки с параметрами: Marker = {}, Width = {}, Depth = {}, Name = {}",
                this.marker,
                this.width,
                this.depth,
                this.name);
    }

    /**
     * Создает посылку с произвольной формой на основе переданной строки.
     *
     * @param name   пользовательское имя посылки
     * @param shape  строковое представление формы, где 'x' или маркер
     *               обозначают тело посылки, а остальные символы — пустоту
     * @param marker основной символ отображения данной посылки
     */
    @Default
    public CargoPackage(String name, String shape, char marker) {
        this.name = name;
        this.marker = marker;
        String[] shapeLines = shape.replace("\\n", "\n").split("\\n");
        this.depth = shapeLines.length;
        this.width = Arrays.stream(shapeLines)
                .map(String::length)
                .max(Integer::compare)
                .get();
        packageMatrix = new char[this.depth][this.width];
        for (int row = this.depth - 1; row >= 0; row--) {
            for (int col = 0; col < this.width; col++) {
                char shapeMarker = shapeLines[row].charAt(col);
                packageMatrix[row][col] = (shapeMarker == marker || shapeMarker == markerForCustomShape)
                        ? marker
                        : Truck.emptySpaceMarker;
            }
        }
        log.info(
                "Создана нестандартная посылка по форме с параметрами:Marker = {}, Width = {}, Depth = {}, Name = {}",
                this.marker,
                this.width,
                this.depth,
                this.name);
    }

    /**
     * @return маркер-символ данной посылки
     */
    public char getMarker() {
        return marker;
    }

    /**
     * @return ширина занимаемой посылкой области
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return глубина занимаемой посылкой области
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return название посылки
     */
    public String getName() {
        return name;
    }

    /**
     * Формирует строковое визуальное представление формы посылки.
     *
     * @return строка, представляющая матрицу посылки с переносами строк
     */
    public String packageShape() {
        StringBuilder shape = new StringBuilder();
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                shape.append(packageMatrix[row][col]);
            }
            shape.append('\n');
        }
        return shape.toString();
    }

    /**
     * Формирует строковое визуальное представление формы посылки.
     *
     * @return строка, представляющая матрицу посылки с переносами строк
     */
    public int volume() {
        return (int) Arrays.stream(packageMatrix)
                .flatMapToInt(row -> new String(row).chars())
                .mapToObj(cell -> (char) cell)
                .filter(cell -> !cell.equals(Truck.emptySpaceMarker))
                .count();
    }

    /**
     * Возвращает конкретную строку матрицы посылки.
     *
     * @param row индекс строки (от 0 до depth-1)
     * @return массив символов, представляющий ряд посылки
     */
    public char[] packageMatrixRow(int row) {
        return packageMatrix[row];
    }

    /**
     * @return краткое описание посылки в формате "маркер:ширина,глубина"
     */
    public String toString() {
        return String.format("%c:%d,%d", marker, width, depth);
    }

    /**
     * Проверяет корректность строкового шаблона для создания нестандартной формы.
     * <p>
     * Шаблон считается валидным, если он не пуст, содержит только разрешенные
     * символы (' ', 'x', '\n') и имеет одинаковую длину строк (прямоугольная структура).
     *
     * @param shape строка с шаблоном формы
     * @return {@code true}, если форма валидна; {@code false} в противном случае
     */
    public static boolean checkCustomShape(String shape) {
        if (shape.isEmpty()) {
            log.error("Передоваемая форма груза пуста!");
            return false;
        }
        String shapeToCheck = shape.replace("\\n", "\n");
        for (char c : shapeToCheck.toCharArray()) {
            if (c != Truck.emptySpaceMarker && c != markerForCustomShape && c != '\n') {
                log.error("Форма посылки может быть задана только символами \"\\n\", \" \", \"x\"");
                return false;
            }
        }
        if (Arrays.stream(shapeToCheck.split("\n"))
                        .map(String::length)
                        .distinct()
                        .toList()
                        .size()
                != 1) {
            log.error("Передана неравномерная структура формы посылки. Каждый уровень должен иметь одинаковую длину");
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toOutputValue() {
        return String.format("Name: %s\nShape:\n%sMarker:%s\n", name, packageShape(), marker);
    }

    private String generateDefaultName(char marker) {
        return String.format("Посылка тип %s", marker);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CargoPackage that = (CargoPackage) o;
        return marker == that.marker
                && width == that.width
                && depth == that.depth
                && Objects.equals(name, that.name)
                && packageShape().equals(that.packageShape());
    }
}
