# AVL Tree Visualizer

An interactive **AVL Tree Visualizer** built using **React (Frontend)** and **Java (Backend)**.  
This project helps visualize how AVL Trees maintain balance using rotations.

The visualizer demonstrates **AVL insertion, balance factor calculation, and rotations (LL, RR, LR, RL)** with animated tree updates.

---

## Features

- AVL Tree insertion visualization
- Balance factor display for every node
- Automatic AVL balancing
- Rotation detection:
  - LL Rotation
  - RR Rotation
  - LR Rotation
  - RL Rotation
- Step-by-step balancing messages
- Node highlighting
- Path highlighting from root to inserted node
- Rotation arrows
- Smooth animation of nodes
- Zoom and drag tree interaction

---

## Tech Stack

### Frontend
- React.js
- react-d3-tree
- CSS

### Backend
- Java
- Java HttpServer API

---

## Project Structure

```
avl-tree-visualizer
│
├── avl-backend
│   ├── Server.java
│   ├── AVLTree.java
│   └── AVLNode.java
│
├── avl-frontend
│   ├── public
│   ├── src
│   │   ├── App.js
│   │   ├── App.css
│   │   └── index.js
│   └── package.json
│
└── README.md
```

---

## Project Documentation

Detailed documentation:

[AVL Tree Documentation](docs/AVLTree-document.pdf)


## How to Run the Project

### 1. Clone the Repository

```
git clone https://github.com/your-username/avl-tree-visualizer.git
cd avl-tree-visualizer
```

---

## Run Backend

Navigate to backend folder:

```
cd avl-backend
```

Compile Java files:

```
javac *.java
```

Run the server:

```
java Server
```

Backend will start at:

```
http://localhost:9000
```

---

## Run Frontend

Navigate to frontend folder:

```
cd avl-frontend
```

Install dependencies:

```
npm install
```

Start React app:

```
npm start
```

The application will run at:

```
http://localhost:3000
```

---

## Example AVL Rotation

### Insert Sequence

```
6 → 5 → 4
```

### Before Balancing

```
    6
   /
  5
 /
4
```

### After LL Rotation

```
   5
  / \
 4   6
```

The visualizer shows this rotation automatically.

---



---

## Future Improvements

- AVL deletion visualization
- Search operation animation
- Tree statistics panel
- Operation history panel
- Performance visualization
- Interactive step-by-step algorithm explanation

---

## Author

**Laxman Reddy Saddi**

B.Tech – Computer Science Engineering (AI & ML)  
ACE Engineering College

---

## License

This project is open-source and available under the **MIT License**.
