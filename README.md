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
- Separação de perfis (`ROLE_ADMIN` e `ROLE_USER`)
- Redirecionamento inteligente após login (Admin vai para o painel, Usuário vai para a vitrine)

### 🔁 Recuperação de Senha
- Envio de e-mail com token único
- Token com expiração automática
- Validação de token (válido, expirado ou inválido)
- Redefinição segura de senha

### 🛒 Carrinho de Compras (Novo!)
- Carrinho de compras persistido na sessão do usuário
- Adicionar produtos diretamente da vitrine ou dos detalhes do produto
- Alterar quantidade e remover itens dinamicamente
- Finalizar pedido vinculando a um aluno específico
- Design responsivo e amigável

---

### 📚 Gestão Acadêmica (Painel Administrativo)
Acesso exclusivo para administradores gerenciarem:
- 👨‍🎓 **Alunos**, 📖 **Cursos**, 👨‍🏫 **Professores**, 🧩 **Disciplinas**
- 📦 **Produtos**, 🏷️ **Categorias**, 🧾 **Pedidos**, 📊 **Relatórios**

---

## 🧱 Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)**:

```
📦 inovatech
┣ 📂 controller   → Camada de entrada (HTTP) e mapeamento de rotas
┣ 📂 service      → Regras de negócio e escopo de sessão (Carrinho)
┣ 📂 repository   → Acesso ao banco (JPA)
┣ 📂 entity       → Modelos de dados mapeados no banco
┣ 📂 dto          → Transferência de dados
┣ 📂 security     → Configurações de autenticação/autorização e sucesso de login
```

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
| Bootstrap 5 | Estilização, Design Responsivo e Componentes (UI/UX) |

---

## ⚙️ Como Testar o Sistema Passo a Passo (Tudo Mastigado)

Se você recebeu este projeto para testar, siga os passos abaixo para deixar tudo rodando na sua máquina.

### ✅ Pré-requisitos
1. **Java 17 ou 21** instalado
2. **PostgreSQL** instalado e rodando
3. (Opcional) Git para clonar

### 🗄️ Passo 1: Configurar Banco de Dados

Crie um banco de dados vazio no PostgreSQL. O nome padrão esperado é `inova_techdb2`, rodando na porta `5433` (ajuste se o seu for na 5432).

```sql
CREATE DATABASE inova_techdb2;
```

Na raiz do projeto, renomeie (ou copie) o arquivo `src/main/resources/application.properties.example` para `application.properties` e preencha com os dados do seu banco:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/inova_techdb2
spring.datasource.username=postgres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```
> **Nota:** Deixe o `ddl-auto=update`! Ele vai criar as tabelas do zero automaticamente para você.

### ▶️ Passo 2: Rodar a Aplicação

Abra o terminal na pasta do projeto e rode o comando:
```bash
# Windows
.\mvnw spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Aguarde o log mostrar que a aplicação iniciou na porta 8080. Abra o navegador em: **http://localhost:8080**

### 🧪 Passo 3: Testar os Dois Perfis (Admin vs Usuário)

O sistema conta com um injetor de dados (`DataSeeder.java`) que cria dois usuários automaticamente na primeira vez que você roda a aplicação. 

#### 👨‍💻 Testando como ADMINISTRADOR
1. Clique em **"Acessar sistema"**
2. Use as credenciais:
   - **Login:** `admin`
   - **Senha:** `admin123`
3. Você será redirecionado para o **Painel Administrativo** (`/home`).
4. Teste criar um aluno, criar uma categoria, criar um produto (com URL de imagem válida) e ver a listagem de pedidos.
5. Vá em **"Relatórios"** e teste a **Geração de PDF** ultrarrápida dos alunos cadastrados.

#### 🛒 Testando como USUÁRIO COMUM (O Carrinho)
1. Saia da conta de admin (clique em "Sair" na navbar).
2. Clique em **"Acessar sistema"** novamente e use as credenciais:
   - **Login:** `usuario@email.com`
   - **Senha:** `123456`
3. Você será redirecionado de volta para a vitrine, mas agora notará que o botão de "Acessar sistema" mudou para um **ícone de Carrinho** e um botão de "Sair".
4. Vá em "Produtos", clique em **"Adicionar ao carrinho"**.
5. Um balão vermelho com o número de itens aparecerá no ícone do carrinho no menu superior.
6. Clique no ícone do carrinho.
7. Teste alterar a quantidade, veja o subtotal mudando e o total geral calculando corretamente.
8. Selecione um aluno no select (para quem é o pedido) e clique em **"Finalizar"**.
9. Você verá a tela de sucesso! (Se você relogar como Admin, verá esse pedido lá no painel).

---

## 🔒 Segurança Adicional Implementada

* **Controle por Rotas:** Um usuário comum (`ROLE_USER`) que tentar acessar `/home` ou qualquer rota administrativa receberá um erro de acesso negado (403). O admin (`ROLE_ADMIN`) tem acesso irrestrito aos painéis de CRUD.
* O sistema não expõe endpoints do banco.

---

## 📄 Licença
Este projeto está sob a licença MIT.

---

## 👨‍💻 Autor

Desenvolvido por:
**Yuri Ribeiro Abe**
🔗 [https://github.com/YuriAbe](https://github.com/YuriAbe)
