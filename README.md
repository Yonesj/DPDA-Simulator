# DPDA-Simulator: Deterministic Pushdown Automata Explorer

This is a Java-based tool designed to construct and simulate Deterministic Pushdown Automata (DPDA). This application allows users to visually create DPDAs and verify whether a given string is part of the context-free language recognized by the automaton. This project uses JavaFX for its graphical user interface, offering an intuitive and interactive experience.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Features

- **Graphical Interface**: User-friendly JavaFX-based UI for creating and visualizing DPDAs.
- **DPDA Construction**: Add states, transitions, and define stack behaviors with ease.
- **String Validation**: Check if a given string is accepted by the constructed DPDA.
- **Interactive Simulation**: Step-by-step simulation to observe how strings are processed.
- **Save/Load Automata**: Save your automata for future use or load previously saved DPDAs.

## Installation

### Prerequisites

- **Java JDK 17** or higher.
- **JavaFX SDK**: Ensure that JavaFX is set up on your system. Instructions can be found [here](https://openjfx.io/openjfx-docs/).

### Steps

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Yonesj/DPDA-Simulator.git

IntelliJ IDEA will automatically import the project and download the necessary dependencies. but in case you don't have intelliJ, do following steps:

2. **Navigate to the Project Directory:**
   ```bash
   cd DPDA-Simulator
   
3. **Build and Run the Project Using Maven Wrapper:**
- For Unix-based systems (macOS, Linux):
  ```bash
  ./mvnw clean install
  ./mvnw javafx:run
   
- For Windows:
   ```bash
  .\mvnw.cmd clean install
   .\mvnw.cmd javafx:run

## License

This project is licensed under the [MIT License](LICENSE). You may use, distribute, and modify this code under the terms of the license.

