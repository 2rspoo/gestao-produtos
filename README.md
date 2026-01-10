Grupo 136

Contribuições:
Camila Rabello Spoo Goshima - Discord: camilaspoo - 11 973091025
Rodrigo Rabello Spoo - Discord: srsinistro9459 - 11 981046096

Vídeo:
https://www.youtube.com/watch?v=oYuT7maHG5g

Repositório:
https://github.com/2rspoo/gestao-produtos

## 🍟 Gestão de Produtos 

Este microsserviço é responsável pelo gerenciamento do catálogo de produtos da lanchonete. Ele permite a administração de **Categorias** (Lanche, Bebida, Sobremesa, Acompanhamento) e **Produtos**, servindo como fonte de dados para a montagem dos pedidos.

O projeto segue a **Arquitetura Hexagonal**, garantindo que as regras de negócio do domínio (Preço, Categoria, Descrição) não dependam de frameworks externos.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)
![Coverage](https://img.shields.io/badge/Coverage-Jacoco-success)
![Build](https://img.shields.io/badge/Build-Maven-blue)

## 🏛️ Arquitetura

A aplicação está estruturada em camadas concêntricas:

* **Domain:** Entidades Core (`Product`, `Category`) e interfaces de portas (Ports).
* **Application (Use Cases):** Regras de negócio como `CreateProduct`, `UpdateProduct`, `ListProductsByCategory`.
* **Infrastructure (Adapters):**
    * **In:** Controladores REST (`ProductController`, `CategoryController`).
    * **Out:** Persistência via **JPA/Hibernate** (Banco de Dados Relacional).

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.1
* **Banco de Dados:** PostgreSQL / MySQL / H2 (JPA)
* **Documentação:** SpringDoc OpenAPI (Swagger)
* **Qualidade:** JaCoCo (Cobertura), SonarQube
* **Testes:** JUnit 5, Mockito

## 🚀 Como Rodar o Projeto

## Pré-requisitos
* Java 21 SDK
* Maven
* Docker (para banco de dados e Sonar)

## Configuração de Ambiente
Configure as credenciais do banco de dados no arquivo `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/cardapio
spring.datasource.username=seu_user
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

## 🧪 Testes e Qualidade
O projeto mantém uma alta cobertura de testes, validando tanto as entidades JPA quanto os Controladores e Use Cases.
Rodar Testes (Unitários + Integração)
Bashmvn clean test

Relatório de Cobertura (JaCoCo)
Verifique a porcentagem de código testado em:target/site/jacoco/index.html
http://localhost:63342/gestao-produtos/cardapio/target/site/jacoco/index.html?_ijt=hfdvd8gbn6jp0dbhkknosktkhn&_ij_reload=RELOAD_ON_SAVE
<img width="1290" height="233" alt="image" src="https://github.com/user-attachments/assets/0f9e1a23-1d96-4c1f-b6b9-ed5de3aa234c" />

##🔌 API Endpoints 

<img width="553" height="180" alt="image" src="https://github.com/user-attachments/assets/339ac6d8-48e4-4fcf-82c3-ead8ee3e7cd1" />

## Acesso ao Frontend da Aplicação:
Abra o arquivo index.html, webhook ou stress.html diretamente no seu navegador. As interfaces carregarão os dados da API.

## Acesso a Documentação da API (Swagger UI):
A documentação interativa completa da API está disponível em:
http://localhost:30001/swagger-ui.html





