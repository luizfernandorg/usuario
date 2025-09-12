
# User Microservice

This project marks the beginning of a suite of four microservices designed to work together. 
This microservice integrates Spring Security to manage authentication and authorization, 
enabling user creation, login, and token retrieval. It also supports searching and deleting 
users by email. Additionally, it provides functionality to create and update addresses and phone numbers.

#### Other microservices for this project:
##### https://github.com/luizfernandorg/notificacao
##### https://github.com/luizfernandorg/agendador-tarefas
##### https://github.com/luizfernandorg/bff-agendador-tarefas BFF (Backend for Frontend)

## API Documentation

#### Save the user

```http
  POST /usuario
```

| Body   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| nome | `String` | user name |
| email | `String` | user e-mail |
| senha | `String` | user password |
| enderecos | `list` | user addresses |
| telefones | `list` | user phone numbers |

#### User addresses fields 

| Body   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| rua | `String` | address name |
| numero | `Long` | address number |
| complemento | `String` | complement |
| cidade | `String` | address city |
| estado | `String` | address state |
| cep | `String` | zip code |

#### User phone number fields

| Body   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| numero | `String` | phone number |
| ddd | `String` | area code |

#### Logging

```http
  POST /usuario/login
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| email | `String` | user email |
| senha | `String` | user password |

#### Retrieving user by email

```http
  GET /usuario
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| email | `String` | user email |

## Installation

To install this project, follow the steps below. Before starting the service, make sure the PostgreSQL database named db_usuario has been created. This service does not include a frontend page, it's a Java backend only. Therefore, using Postman is recommended for testing.

```bash
  git clone https://github.com/luizfernandorg/usuario.git
  cd usuario
  ./gradlew clean build; ./gradlew bootRun
```
    
