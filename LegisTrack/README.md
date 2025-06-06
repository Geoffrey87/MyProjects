# Government Simulator - README

## Introduction

Government Simulator is a web application designed to simulate the process of proposing, voting on, and passing laws within a legislative system. Users can create political parties, propose laws, and generate simulated votes based on predefined rules. The application provides an interactive experience that models real-world government decision-making.

The simulator works like this: after clicking "Let’s get started," we are redirected to a page showing the current Portuguese government (currently in a caretaker status). From there, we can edit, create, or delete any political party.
Next, we can propose laws, which must always be associated with a party, and then generate the votes. The party proposing the law will always vote in favor, but to make things a bit more unpredictable, the remaining votes are randomly assigned between abstention, in favor, or against.
Just like in real life, once a law is created and voted on, it can no longer be edited.

## Features

- **User Identification**: Generates a unique user ID stored in `localStorage` and `sessionStorage`.
- **Party Management**: Users can create and manage political parties.
- **Law Proposal**: Users can propose new laws under their registered parties.
- **Voting Simulation**: Laws are voted on by political parties based on predefined probabilities.
- **Vote Calculation & Results**: The app calculates the voting outcome and determines whether a law is approved or rejected.
- **REST API Integration**: The frontend interacts with a backend via a REST API for data management.

## Installation & Setup

### Technologies used

- **React.js + Tailwind CSS** for frontend development
- **Java 17 + Spring Boot** for backend development
- **PostgreSQL** for database management
- **Docker/Docker-Compose** for deploy
- **AWS RDS and Elastic BeanStalk** for DB and cloud deploy

### Running the Backend

1. Clone the repository.
2. Navigate to the backend directory.
3. Set up the database and update application properties.
4. Run the backend using:

   ```sh
   mvn spring-boot:run
   ```

### Running the Frontend

1. Navigate to the frontend directory.
2. Install dependencies:

   ```sh
   npm install
   ```

3. Start the React application:

   ```sh
   npm start
   ```

## How It Works

### 1. User Identification
When a user accesses the site, a unique `userId` is generated and stored in `localStorage`. If the user revisits the site, the same ID is used to persist user-related data.

### 2. Party Management
Users can create political parties associated with their `userId`. Each party has a name, a number of deputies (representatives), and belongs to a user.

### 3. Proposing Laws
Users can propose laws by selecting a political party they own. Each law has:
- A **description**
- A **proposing party**
- A **unique identifier**
- A **date proposed**

### 4. Generating Votes
When a law is submitted for voting, the system:

#### Step 1: Identify the Proposing Party
- The party that proposed the law is automatically registered as voting **IN FAVOR**.

#### Step 2: Selecting Other Parties for Voting
- A list of other parties (excluding the proposing party) is retrieved.
- Only parties that have not yet voted are considered.

#### Step 3: Vote Distribution (Randomized)
The available parties are shuffled, and votes are assigned based on predefined probabilities:

- **40% of parties vote IN FAVOR** ✅
- **35% of parties vote AGAINST** ❌
- **25% of parties ABSTAIN** ⚖️

#### Why These Percentages?
The vote distribution is designed to mimic real-world political decision-making where:

- **40% IN FAVOR**: In legislative processes, there is often a tendency for laws to have significant support, especially if proposed by a major party.
- **35% AGAINST**: A considerable opposition exists, reflecting the nature of political disagreements where a large portion of parties reject new laws based on ideological differences.
- **25% ABSTENTION**: Some parties choose to abstain due to strategic reasons, neutrality, or lack of direct impact from the proposed law.

These values provide a realistic yet unpredictable simulation, ensuring that not all laws are passed, and that political tension and uncertainty are incorporated into the voting system.

### 5. Vote Results & Law Approval
- If the number of deputies voting **IN FAVOR** exceeds those **AGAINST**, the law is **approved**.
- Otherwise, the law is **rejected**.

## Conclusion
Government Simulator provides an funny way to understand how legislative processes work. With randomized voting and a structured approval system, users can simulate real-world decision-making in a government setting. 
