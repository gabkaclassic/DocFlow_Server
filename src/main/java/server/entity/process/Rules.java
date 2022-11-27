package server.entity.process;

import server.entity.process.step.Step;

/**
 * Права участника для работы с документом
 * READ - только просмотр документов, возможность добавлять ресурсы и комментарии
 * CHANGE - изменение документов, добавление новых, а также все возможности READ
 * CONTROL - переход на новый шаг, возврат на предыдущий, завершение процесса, а также все возможности CHANGE
 * @see Step
 * */
public enum Rules {
    CHANGE, READ, CONTROL
}
