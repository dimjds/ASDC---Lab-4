public class Main {
    public static void main(String[] args) {          // Главный метод программы
        int[] shape = {100, 100};                    // Создание одномерного массива shape размера 2 со значениями 100 и 100
        int[][] ranges = {{25, 75}, {25, 75}};      // Создание двумерного массива ranges размера 2х2 со значениями {{25, 75}, {25, 75}}

        MultiDimensionalArray arr = new MultiDimensionalArray(shape, ranges); // Создание объекта класса MultiDimensionalArray с параметрами shape и ranges
        arr.testSpeed();                          // Вызов метода testSpeed() объекта arr
    }
}

class MultiDimensionalArray { // объявляем класс MultiDimensionalArray
    // объявляем приватные поля класса
    private int[] shape;        // размерность массива
    private int[][] ranges;   // диапазоны индексов
    private int[] data;     // данные массива

    public MultiDimensionalArray(int[] shape, int[][] ranges) {         // объявляем конструктор класса
        // инициализируем поля класса с помощью переданных в конструктор параметров
        this.shape = shape;
        this.ranges = ranges;
        // создаем массив с размером, равным произведению значений входного массива shape
        this.data = new int[shape[0] * shape[1]];   // Создание одномерного массива data размера shape[0] * shape[1]
    }


    public int directAccess(int i, int j) {         // Метод для прямого доступа к элементу многомерного массива по его индексам i и j.
        return this.data[i * this.shape[1] + j];    // Возвращает значение элемента, находящегося в ячейке с указанными индексами.
    }                                               // Используется формула i * shape[1] + j для вычисления индекса элемента в одномерном массиве data.

    public int ayliffAccess(int[] indices) {
        int idx = 0;        // объявление переменной idx и присвоение ей начального значения 0
        for (int i = 0; i < this.shape.length; i++) {       // цикл по длине размерности массива
            idx *= this.shape[i];       // умножение текущего индекса на размерность текущей оси
            idx += indices[i];       // добавление индекса текущей оси к общему индексу
        }
        return this.data[idx];      // возврат элемента массива по полученному индексу
    }


    // Объявление метода calculateIndices с возвращаемым значением - массивом int[]
    public int[] calculateIndices(int idx) {    // Объявление метода calculateIndices с возвращаемым значением - массивом int[]
        int[] indices = new int[this.shape.length];     // Создание массива indices размером shape.length
        for (int i = this.shape.length - 1; i >= 0; i--) {      // Цикл от последнего элемента массива shape до нулевого
            /** Вычисление индекса для текущей размерности
             Элемент массива shape[i] - размерность, idx - линейный индекс
             Остаток от деления на размерность i позволяет вычислить индекс в измерении i % */
            indices[i] = idx % this.shape[i];
            idx /= this.shape[i];   // Деление индекса на размерность i, чтобы получить линейный индекс для измерений 0, 1, ... i-1

        }
        return indices;     // Возврат массива индексов

    }

    public int columnAccess(int i, int j) {     // Метод для доступа к элементу массива по столбцу, где i - номер строки, j - номер столбца
        return this.data[j * this.shape[0] + i];    // Используем формулу для вычисления индекса элемента в одномерном массиве, где j - номер столбца, shape[0] - количество строк
    }

    public int rowAccess(int i, int j) {        // Метод для доступа к элементу массива по строке, где i - номер строки, j - номер столбца
        return this.data[i * this.shape[1] + j];        // Используем формулу для вычисления индекса элемента в одномерном массиве, где i - номер строки, shape[1] - количество столбцов
    }


    public void testSpeed() {
        long start, end;    // объявляем переменные для хранения времени начала и конца теста

        start = System.nanoTime();      // сохраняем текущее время начала теста в переменную start
        for (int i = 0; i < this.shape[0]; i++) {       // цикл по первому измерению массива
            for (int j = 0; j < this.shape[1]; j++) {   // цикл по второму измерению массива
                this.directAccess(i, j);        // обращаемся к элементу массива через метод directAccess()
            }
        }
        end = System.nanoTime();        // сохраняем текущее время конца теста в переменную end
        System.out.println("Время прямого доступа в нано.сек: " + (end - start));         // выводим время выполнения теста на экран


        start = System.nanoTime();      // Установить текущее время в переменную start, используя метод nanoTime() класса System

        for (int i = this.ranges[0][0]; i < this.ranges[0][1]; i++) {       // Перебор элементов массива с использованием диапазона значений ranges для вызова метода ayliffAccess
            for (int j = this.ranges[1][0]; j < this.ranges[1][1]; j++) {
                this.ayliffAccess(new int[]{i, j});         // Вызвать метод ayliffAccess с текущими индексами i и j в качестве параметра
            }
        }
        end = System.nanoTime();        // Установить текущее время в переменную end, используя метод nanoTime() класса System
        System.out.println("Время доступа через Айлиффу в нано.сек: " + (end - start));         // Вывести время выполнения метода ayliffAccess в консоль


        start = System.nanoTime();      // Получаем текущее время в наносекундах и сохраняем его в переменной start
        for (int i = 0; i < this.shape[1]; i++) {       // Проходимся по каждому столбцу матрицы (this.shape[1]) и по каждой строке (this.shape[0]) в этом столбце
            for (int j = 0; j < this.shape[0]; j++) {
                this.columnAccess(i, j);    // Доступ к элементу матрицы по столбцу i и строке j
            }
        }
        end = System.nanoTime();        // Получаем текущее время в наносекундах и сохраняем его в переменной end
        System.out.println("Время доступа к столбцам в нано.сек: " + (end - start)); // Выводим в консоль время выполнения операции доступа к элементам матрицы по столбцам


        start = System.nanoTime();    // Устанавливаем начальное время выполнения
        for (int i = 0; i < this.shape[0]; i++) {   // Итерируемся по строкам и столбцам массива, вызывая метод доступа по строке
            for (int j = 0; j < this.shape[1]; j++) {   /** Начало цикла по переменной j с нуля до значения, меньшего чем количество столбцов в массиве
             Вызываем метод directAccess класса MultiDimensionalArray, передавая i и j в качестве аргументов */
                this.rowAccess(i, j);   // и возвращаемое значение игнорируем
            }
        }
        end = System.nanoTime();    // Устанавливаем конечное время выполнения и выводим разницу между начальным и конечным временем в миллисекундах
        System.out.println("Время доступа к строке в нано.сек: " + (end - start));
    }
}
