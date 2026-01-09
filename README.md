# Geekmonkey Task Management

Geekmonkey Task Management is a Jira-like agile task management portal for internal Finance + Operations teams. The UI is server-rendered with Thymeleaf and the backend is Spring Boot with MongoDB.

## Features
- Multi-login authentication with roles (ADMIN, MANAGER, MEMBER)
- Team management with role-based access
- Projects, backlog, sprints, and kanban board
- Timesheet logging and weekly summaries
- Sprint performance dashboards and carryover reporting
- EN + हिंदी internationalization
- Geekmonkey brand theme

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Web MVC
- Spring Security (session login + role-based access)
- Spring Data MongoDB
- Thymeleaf
- Lombok
- Maven

## Setup
### 1) Start MongoDB (Docker)
```bash
docker-compose up -d
```

### 2) Run the application
```bash
mvn spring-boot:run
```

The app will start at http://localhost:8080.

## Default Logins
- Admin: `admin@geekmonkey.local` / `Admin@123`
- Manager: `manager@geekmonkey.local` / `Manager@123`
- Member: `member@geekmonkey.local` / `Member@123`

## Notes
- Seed data is created on first run via `CommandLineRunner`.
- Task carryover requires a reason when closing a sprint.
