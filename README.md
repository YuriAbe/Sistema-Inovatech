# Projeto-Inovatech

## 🎓 Sistema Acadêmico Inovatech

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Build](https://img.shields.io/badge/build-passing-success)
![Database](https://img.shields.io/badge/database-PostgreSQL-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

O **Sistema Inovatech** é uma aplicação web desenvolvida com **Spring Boot**, focada na **gestão acadêmica completa**, oferecendo controle de usuários e gerenciamento de entidades educacionais como alunos, cursos, professores e disciplinas.

O sistema foi projetado com foco em **segurança, organização e escalabilidade**, seguindo boas práticas de arquitetura e desenvolvimento.

---

## 🚀 Funcionalidades

### 🔐 Autenticação e Segurança
- Cadastro de usuários
- Login com autenticação segura (Spring Security)
- Criptografia de senha com BCrypt
- Controle de perfis (Roles)

### 🔁 Recuperação de Senha
- Envio de e-mail com token único
- Token com expiração automática
- Validação de token (válido, expirado ou inválido)
- Redefinição segura de senha
- Invalidação automática do token após uso
- Página dedicada para token expirado com redirecionamento automático

---

### 📚 Gestão Acadêmica (CRUD Completo)

O sistema permite o gerenciamento completo das seguintes entidades:

- 👨‍🎓 **Alunos**
- 📖 **Cursos**
- 👨‍🏫 **Professores**
- 🧩 **Disciplinas**
- 👤 **Usuários**

Operações disponíveis:
- Criar
- Listar
- Atualizar
- Remover

---

## 🧱 Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)** com separação clara de responsabilidades:

```

📦 inovatech
┣ 📂 controller   → Camada de entrada (HTTP)
┣ 📂 service      → Regras de negócio
┣ 📂 repository   → Acesso ao banco (JPA)
┣ 📂 entity       → Modelos de dados
┣ 📂 dto          → Transferência de dados
┣ 📂 security     → Configurações de autenticação/autorização

````

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|----------|----------|
| Java 21 | Linguagem principal |
| Spring Boot 3.x | Framework principal |
| Spring Security | Autenticação e autorização |
| Spring Data JPA | Persistência de dados |
| PostgreSQL | Banco de dados |
| Thymeleaf | Renderização de páginas (MVC) |
| Spring Mail | Envio de e-mails |
| Lombok | Redução de boilerplate |
| Maven | Gerenciamento de dependências |

---

## 📸 Telas do Sistema

🚧 *Em breve...*

> Aqui serão adicionadas imagens das telas do sistema:
- Tela de login
- Cadastro de usuários
- Recuperação de senha
- Dashboard
- CRUDs do sistema

---

## ⚙️ Configuração do Projeto

### ✅ Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

---

### 📥 Clonar o repositório

```bash
git clone https://github.com/YuriAbe/Sistema-Inovatech.git
cd Sistema-Inovatech
````

---

### 🗄️ Configurar banco de dados

**1. Crie o banco no PostgreSQL** (via `psql` ou pgAdmin):

```sql
CREATE DATABASE inovatech;
```

**2. Configure as credenciais.** Copie o arquivo de exemplo e ajuste usuário/senha:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inovatech
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

> O `application.properties` real é ignorado pelo Git — não versione senhas.
> Com `ddl-auto=update`, o Hibernate cria/atualiza as tabelas automaticamente a
> partir das entidades, garantindo o **CRUD funcional** sobre o PostgreSQL.

**3. Dados de teste automáticos.** Na primeira execução, a classe
`config/DataSeeder.java` (um `CommandLineRunner`) popula o banco com dados
aleatórios — categorias, produtos, alunos e um usuário administrador — **somente
quando as tabelas estão vazias** (seguro rodar várias vezes).

**Login padrão criado pelo seed:**

| Login   | Senha      | Perfil      |
|---------|------------|-------------|
| `admin` | `admin123` | ROLE_ADMIN  |

> A senha é gravada com BCrypt. Para limpar e gerar novos dados, basta esvaziar
> as tabelas (ou recriar o banco) e rodar a aplicação novamente.

---

### 📧 Configurar envio de e-mail

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app
```

---

### ▶️ Executar aplicação

```bash
mvn spring-boot:run
```

---

## 🧪 Testes recomendados

* ✅ Cadastro e login de usuários
* ✅ Fluxo de recuperação de senha
* ✅ Expiração de token
* ✅ CRUD completo das entidades
* ✅ Controle de acesso por perfil

---

## 🔒 Segurança

* Senhas criptografadas com BCrypt
* Tokens únicos e temporários
* Invalidação automática de token
* Proteção de rotas com Spring Security

---

## 🚧 Status do Projeto

🚧 **Em desenvolvimento**

Próximas melhorias planejadas:

* Interface mais moderna (UI/UX)
* Logs e monitoramento

---

## 📄 Licença

Este projeto está sob a licença MIT.

---

## 👨‍💻 Autor

Desenvolvido por:

**Yuri Ribeiro Abe**
🔗 [https://github.com/YuriAbe](https://github.com/YuriAbe)
