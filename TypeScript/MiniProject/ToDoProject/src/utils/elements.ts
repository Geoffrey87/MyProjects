export function getElement<T extends HTMLElement>(query: string): T {
    const element = document.querySelector<T>(query);
    if (element === null) throw new Error (`Element not found: ${query}`);

    return element;
}

export const elements = {
    form: getElement<HTMLFormElement>("form"),
    todoInput: getElement<HTMLInputElement>("#todo-input"),
    clearTodoInputButton: getElement<HTMLInputElement>("#clear-todo-input"),
    todoContainer: getElement<HTMLDataListElement>("#todo-container"),
    itemsLeft: getElement<HTMLParagraphElement>("#clear-completed"),
    clearCompleted: getElement<HTMLButtonElement>("#clear-completed"),
    errorPopup: {
        container: getElement<HTMLDivElement>("#error-popup"),
        message: getElement<HTMLSpanElement>("#error-message"),
        closeButton: getElement<HTMLButtonElement>("#close-error-popup"),
    }   
}  
