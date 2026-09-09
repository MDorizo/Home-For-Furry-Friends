# Home for Furry Friends — Sistema Veterinário

> **Sistema Desktop de Gerenciamento Veterinário**  
> Projeto prático de desenvolvimento para centralização e controle de atendimentos, tutores, pets, vacinas e procedimentos clínicos.

---

## Sumário
- [Visão Geral](#visao-geral)
- [Problema vs. Solução](#problema-vs-solucao)
- [Funcionalidades Principais](#funcionalidades-principais)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- [Modelo de Dados & Entidades](#modelo-de-dados-entidades)
- [Banco de Dados](#banco-de-dados)
- [Regras de Negócio & Validações](#regras-de-negocio-validacoes)
- [Fluxo Principal de Atendimento](#fluxo-principal-de-atendimento)
- [Guia de Identidade Visual (UI)](#guia-de-identidade-visual-ui)
- [Escopo e Limitações](#escopo-e-limitacoes)
- [Como Executar o Projeto](#como-executar-o-projeto)

---

<a id="visao-geral"></a>
## Visão Geral

A **Home for Furry Friends** é uma clínica veterinária destinada ao atendimento de cães, gatos e outros animais domésticos. O sistema **Veterinaria** foi concebido como uma aplicação desktop Java para modernizar a rotina operacional da clínica, substituindo o uso de fichas físicas por um banco de dados relacional seguro e organizado.

### Benefícios Esperados
- **Centralização:** Todos os dados da clínica em um único ambiente.
- **Consulta Rápida:** Listagens por módulo (tutores, pets, vacinas por pet, etc.) sem precisar de fichas físicas.
- **Redução de Erros:** Cadastros estruturados em vez de anotações manuais.
- **Eficiência:** Eliminação gradual do uso de papéis e fichas físicas.

---

<a id="problema-vs-solucao"></a>
## Problema vs. Solução

| Cenário Anterior (Manual) | Solução com o Sistema Veterinaria |
| :--- | :--- |
| Perda ou dificuldade de localização de prontuários. | Cadastro digital estruturado por tutor, pet e atendimento. |
| Dificuldade para acompanhar o histórico de vacinação. | Controle de doses aplicadas e agendamento de próximas doses. |
| Perda de vínculo entre tutor e pet. | Relacionamento $1:N$ bem estruturado entre Tutor e Pets. |
| Falta de controle financeiro dos atendimentos. | Registro do valor de cada consulta no momento do atendimento. |
| Falta de identificação do veterinário responsável. | Vínculo explícito do CRMV/Veterinário em cada atendimento. |

---

<a id="funcionalidades-principais"></a>
## Funcionalidades Principais

- **Gerenciamento de Tutores (CRUD):** Cadastro completo (criar, listar, atualizar, excluir).
- **Gerenciamento de Pets (CRUD):** Registro de animais vinculados diretamente a um tutor responsável (criar, listar, atualizar, excluir).
- **Gerenciamento de Veterinários (CRUD):** Controle de profissionais habilitados e seus CRMVs (criar, listar, atualizar, excluir).
- **Registro de Atendimentos** *(somente criação e listagem — sem edição/exclusão ainda)*:
  - Seleção de Pet e Veterinário responsável.
  - Descrição da consulta e diagnóstico em texto livre.
  - Valor total informado manualmente pelo usuário.
- **Cadastro de Procedimentos e Exames:** catálogos próprios (nome, descrição, valor), cadastrados de forma independente — ainda não vinculados a um atendimento específico.
- **Controle de Vacinação:** Registro de vacinas aplicadas e data prevista para próxima dose.

---

<a id="tecnologias-utilizadas"></a>
## Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java** | Linguagem principal da aplicação |
| **Java Swing** | Construção da Interface Gráfica de Usuário (GUI) Desktop |
| **MySQL** | Sistema Gerenciador de Banco de Dados Relacional (SGBD) |
| **JDBC** | API de conectividade entre a aplicação Java e o banco MySQL |
| **SQL** | Criação, manipulação e estruturação de dados |

---

<a id="arquitetura-do-sistema"></a>
## Arquitetura do Sistema

O sistema adota uma arquitetura em camadas simples, promovendo a separação entre a interface com o usuário, as regras de domínio e a camada de persistência:

```
┌──────────────────────────────────────────────────┐
│                Java Swing (GUI)                  │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│              Classes Java / Domínio              │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│             JDBC / Acesso ao Banco               │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│               MySQL / Banco de Dados             │
└──────────────────────────────────────────────────┘
```

---

<a id="modelo-de-dados-entidades"></a>
## Modelo de Dados & Entidades

### Entidades do Domínio

1. **TUTOR:** `id` (PK), `nome`, `cpf`, `telefone`, `email`, `endereco`
2. **PET:** `id` (PK), `tutor_id` (FK), `nome`, `especie`, `raca`, `sexo`, `data_nascimento`, `peso`
3. **VETERINARIO:** `id` (PK), `nome`, `crmv`, `telefone`, `especialidade`
4. **ATENDIMENTO:** `id` (PK), `pet_id` (FK), `veterinario_id` (FK), `data`, `hora`, `descricao`, `diagnostico`, `valor`
5. **PROCEDIMENTO:** `id` (PK), `nome`, `descricao`, `valor`
6. **EXAME:** `id` (PK), `nome`, `descricao`, `valor`
7. **VACINA:** `id` (PK), `pet_id` (FK), `nome`, `data_aplicacao`, `proxima_dose`

### Relacionamentos e Multiplicidades

- **Tutor → Pet:** $1 : 0..*$ *(Um tutor pode possuir múltiplos pets)*
- **Pet → Atendimento:** $1 : 0..*$ *(Um pet pode passar por múltiplos atendimentos)*
- **Veterinário → Atendimento:** $1 : 0..*$ *(Um veterinário pode realizar múltiplos atendimentos)*
- **Pet → Vacina:** $1 : 0..*$ *(Um pet pode ter vários registros de vacina)*
  
### Diagrama de Classes

![Diagrama de Classes](imagens/mermaid-diagram-2026-09-02-182657.png)

---

<a id="banco-de-dados"></a>
### Banco de Dados

```text
CREATE DATABASE IF NOT EXISTS veterinaria;
USE veterinaria;

-- 1. Tabela TUTOR
CREATE TABLE IF NOT EXISTS tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200)
);

-- 2. Tabela PET
CREATE TABLE IF NOT EXISTS pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tutor_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raca VARCHAR(50),
    sexo CHAR(1),
    data_nascimento DATE,
    peso DECIMAL,
    FOREIGN KEY (tutor_id) REFERENCES tutor(id) ON DELETE CASCADE
);

-- 3. Tabela VETERINARIO
CREATE TABLE IF NOT EXISTS veterinario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crmv VARCHAR(20) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    especialidade VARCHAR(100)
);

-- 4. Tabela ATENDIMENTO
CREATE TABLE IF NOT EXISTS atendimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    veterinario_id INT NOT NULL,
    data_atendimento DATE NOT NULL,
    hora_atendimento TIME NOT NULL,
    descricao TEXT,
    diagnostico TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0,
    FOREIGN KEY (pet_id) REFERENCES pet(id),
    FOREIGN KEY (veterinario_id) REFERENCES veterinario(id)
);

-- 5. Tabela PROCEDIMENTO
CREATE TABLE IF NOT EXISTS procedimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0
);

-- 6. Tabela EXAME
CREATE TABLE IF NOT EXISTS exame (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0
);

-- 7. Tabela VACINA
CREATE TABLE IF NOT EXISTS vacina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_aplicacao DATE NOT NULL,
    proxima_dose DATE,
    FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);

SELECT * FROM atendimento;
SELECT * FROM exame;
SELECT * FROM pet;
SELECT * FROM procedimento;
SELECT * FROM tutor;
SELECT * FROM vacina;
SELECT * FROM veterinario;
```

---

<a id="regras-de-negocio-validacoes"></a>
## Regras de Negócio & Validações

### Regras de Negócio (RN)
- **RN01:** Cada tutor possui ID único; o CPF é utilizado para identificação.
- **RN02:** Um tutor pode ter vários pets; cada pet pertence obrigatoriamente a um tutor.
- **RN03:** Cada pet possui um identificador único.
- **RN04/RN05:** Cada atendimento está associado a exatamente um pet e um veterinário responsável.
- **RN06:** Data e hora do atendimento são preenchidas automaticamente pelo sistema no momento do registro.
- **RN11:** O valor do atendimento é informado manualmente pelo usuário no momento do registro.

> **Planejado, ainda não implementado:** vínculo de múltiplos procedimentos e exames a um mesmo atendimento (com quantidades, resultados e valores) e cálculo automático do valor consolidado a partir desses vínculos. As tabelas associativas (`ATENDIMENTO_PROCEDIMENTO` e `ATENDIMENTO_EXAME`) já existem no banco, aguardando a implementação da funcionalidade na aplicação.

### Validações Obrigatórias
- **Tutor:** Nome e CPF obrigatórios.
- **Pet:** Nome e espécie obrigatórios; pet sempre vinculado a um tutor.
- **Veterinário:** Nome e CRMV obrigatórios.
- **Atendimento:** Pet, Veterinário e Valor obrigatórios.
- **Procedimentos / Exames:** Nome obrigatório.
- **Vacina:** Pet, nome da vacina e data de aplicação obrigatórios.

> **Planejado, ainda não implementado:** validação de formato de e-mail do tutor, e bloqueio de valores negativos em peso, valor do atendimento, procedimentos e exames — atualmente qualquer valor numérico é aceito.

---

<a id="fluxo-principal-de-atendimento"></a>
## Fluxo Principal de Atendimento

```
[01] Cadastro do Tutor → [02] Cadastro do Pet → [03] Vínculo Pet → Tutor                                                                    
                                                              ↓
[06] Finalização ← [05] Registro de Atendimento ← [04] Seleção do Veterinário
       │                  ├── Descrição
       ↓                  ├── Diagnóstico
[07] Histórico do Pet     └── Valor (informado manualmente)
```

1. **Cadastro do Tutor:** Inclusão/consulta dos dados do responsável.
2. **Cadastro do Pet:** Registro dos dados do animal.
3. **Vínculo:** Associação do pet ao seu tutor.
4. **Seleção do Veterinário:** Escolha do profissional responsável pela consulta.
5. **Atendimento:** Inclusão de descrição, diagnóstico e valor da consulta.
6. **Finalização:** Confirmação e gravação do atendimento.
7. **Consulta ao Histórico:** Cada módulo (atendimentos, vacinas) mantém sua própria listagem consultável; ainda não há uma tela consolidada de histórico por pet.

---

<a id="guia-de-identidade-visual-ui"></a>
## Guia de Identidade Visual (UI)

Para a construção das telas Java Swing, adota-se a seguinte paleta de cores:

| Elemento | Cor | Hexadecimal |
| :--- | :--- | :--- |
| **Cor Principal** | Verde Veterinário | `#2E7D6B` |
| **Principal Escura** | Verde Escuro | `#205B4E` |
| **Destaque** | Azul Claro | `#5BB8C5` |
| **Fundo Principal** | Branco Gelo | `#F7FAF9` |
| **Cards / Painéis** | Branco | `#FFFFFF` |
| **Texto Principal** | Cinza Escuro | `#263238` |
| **Texto Secundário** | Cinza | `#607D7B` |
| **Bordas** | Cinza Claro | `#D9E5E2` |
| **Sucesso** | Verde | `#43A047` |
| **Erro / Excluir** | Vermelho | `#D9534F` |
| **Aviso** | Laranja | `#F4A261` |

---

<a id="escopo-e-limitacoes"></a>
## Escopo e Limitações

### Escopo Inicial
- CRUDs completos para Tutores, Pets e Veterinários.
- Registro (criação e listagem) de Atendimentos, com edição/exclusão planejadas para uma próxima etapa.
- Interface desktop em Java Swing com conexão via JDBC ao MySQL.

### Fora do Escopo Inicial (Versões Futuras)
- Vínculo de múltiplos procedimentos e exames a um atendimento, com cálculo automático do valor consolidado.
- Edição e exclusão de Atendimentos, Procedimentos, Exames e Vacinas já registrados.
- Validações de formato (e-mail) e de valores não-negativos (peso, valores monetários).
- Aplicativo Mobile e Sistema Web.
- Telemedicina e agendamento/pagamento online.
- Controle de estoque de medicamentos.
- Folha de pagamento e gestão RH.
- Envio de SMS / Notificações.
- Diagnóstico automatizado por Inteligência Artificial.

---

<a id="como-executar-o-projeto"></a>
## Como Executar o Projeto

### Pré-requisitos
- JDK 21 instalado.
- MySQL Server rodando localmente.
- VS Code com a extensão **Extension Pack for Java** (ou uma IDE como IntelliJ/Eclipse, que já vêm com suporte a Maven).

### Passo a passo
1. **Banco de dados:** execute o script `src/main/java/veterinaria/banco.sql` no seu MySQL para criar o banco `veterinaria` e todas as tabelas.
2. **Credenciais:** confira usuário/senha em `Conexao.java` (padrão: `root` sem senha) e ajuste se necessário.
3. **Abrir o projeto:** abra a pasta `Clinica_Veterinaria_J` (a que contém o `pom.xml`) no VS Code.
4. **Executar:** abra `src/main/java/veterinaria/Main.java` e clique no botão **Run** que aparece acima do método `main`. A extensão Java compila e baixa as dependências (driver do MySQL) automaticamente na primeira vez.

> Se tiver o Maven instalado e no PATH, também é possível compilar e rodar via terminal, dentro de `Clinica_Veterinaria_J`:
> ```
> mvn compile exec:java -D"exec.mainClass=veterinaria.Main"
> ```

---

## Licença e Considerações Finais

Documentação referente à **Etapa 1** do projeto prático de desenvolvimento do sistema desktop **Home for Furry Friends**. Servirá como guia técnico para implementação do código-fonte e apresentação acadêmica.
