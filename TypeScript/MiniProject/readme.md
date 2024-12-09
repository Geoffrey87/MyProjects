
# Todo Application

A simple, dynamic todo list application built with vanilla TypeScript and DOM manipulation. This project allows users to add, manage, and track their tasks with features like real-time task rendering, task completion tracking, and error handling.

## Features

- **Add Tasks**: Create new tasks with a title.
- **Mark as Complete**: Mark tasks as completed using checkboxes.
- **Delete Tasks**: Remove individual tasks from the list.
- **Clear Completed**: Remove all completed tasks at once.
- **Error Handling**: Prevent empty tasks and show error messages.
- **Dynamic Updates**: Real-time updates of task count and rendering.

---

## Project Structure

```plaintext
.
├── store
│   └── todos.ts       # Stores the list of todos
├── types
│   └── todos.ts       # Defines the Todo type
├── utils
│   ├── elements.ts    # Manages DOM element retrieval
│   ├── render-todos.ts # Handles rendering of todos
│   └── update-items.ts # Updates the count of incomplete tasks
├── index.ts           # Main entry point for the app
└── README.md          # Documentation
```

---

## Setup

### Prerequisites
- Node.js (v16 or newer)
- npm (v7 or newer)

### Installation

1. Clone the repository:
   ```bash
   git clone C:\Users\Lloyd\OneDrive\Desktop\MyProjects\MyProjects\TypeScript\MiniProject.git
   cd ToDoProject
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open your browser and go to:
   ```
   http://localhost:
   ```

---

## Scripts

- **`npm run dev`**: Starts the development server.
- **`npm run build`**: Builds the project for production.
- **`npm run start`**: Runs the production build.

---

## File Descriptions

### `index.ts`
The main entry point of the application, which:
- Handles form submission for adding new tasks.
- Clears input and hides error popups.
- Handles deletion of completed tasks.

### `store/todos.ts`
Stores the list of todos as an array of `Todo` objects.

### `types/todos.ts`
Defines the `Todo` type with properties:
- `id` (number): Unique identifier.
- `title` (string): Task description.
- `completed` (boolean): Completion status.

### `utils/elements.ts`
Utility for:
- Querying and caching DOM elements.
- Providing easy access to elements like the form, input, error popup, and task container.

### `utils/render-todos.ts`
Handles:
- Rendering the list of tasks dynamically.
- Updating the DOM with the current tasks.
- Attaching event listeners to task checkboxes and delete buttons.

### `utils/update-items.ts`
Updates the count of incomplete tasks and displays it in the UI.

---

## How It Works

1. **Adding Tasks**:
   - Users type a task in the input field and submit it.
   - Tasks are added to an in-memory array (`todos`) and rendered in the task container.

2. **Marking as Complete**:
   - Tasks can be marked as complete by toggling a checkbox.
   - The task's `completed` property is updated in real-time.

3. **Deleting Tasks**:
   - Users can delete tasks individually using the "Delete" button.
   - Completed tasks can be cleared in bulk using the "Clear Completed" button.

4. **Error Handling**:
   - If the user tries to submit an empty task, an error popup is displayed.

---

## Example Usage

1. Open the app.
2. Add a task like "Buy groceries."
3. Mark it as completed or delete it.
4. Add more tasks and clear completed tasks in bulk.

---


### Based on a project from a Udemy course: Fundamentals of Web Development with JavaScript and TypeScript by Lucas Barake.
