# CustomerConnect

API REST desenvolvida com Spring Boot para gerenciamento de clientes.

O projeto foi construido com foco em aprendizado de backend, cobrindo operacoes de CRUD, paginacao, filtros, tratamento de erros HTTP e validacoes de regra de negocio.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Maven

## Funcionalidades

- Criar clientes
- Listar clientes com paginacao
- Filtrar clientes por CPF e email
- Atualizar clientes por id
- Remover clientes por id
- Impedir duplicidade de CPF e email
- Retornar status HTTP coerentes para erro de validacao e conflito de dados

## Estrutura do projeto

O projeto segue uma separacao simples por responsabilidade:

- `controller`: recebe as requisicoes HTTP e monta as respostas
- `service`: concentra as regras de negocio
- `repository`: faz o acesso aos dados com Spring Data JPA
- `entity`: representa a tabela de clientes
- `dto`: define os objetos de entrada e saida da API

## Endpoints

### Criar cliente

`POST /customers`

Exemplo de body:

```json
{
  "name": "Maria Silva",
  "cpf": "12345678900",
  "email": "maria@email.com",
  "phoneNumber": "11999999999"
}
```

Resposta esperada:

- `201 Created` quando o cliente for criado com sucesso
- `409 Conflict` quando CPF ou email ja estiverem cadastrados

### Listar clientes

`GET /customers`

Parametros suportados:

- `page` - pagina desejada. Padrao: `0`
- `pageSize` - quantidade de registros por pagina. Padrao: `10`
- `orderBy` - ordenacao por data de criacao: `asc` ou `desc`
- `cpf` - filtro opcional por CPF
- `email` - filtro opcional por email

Exemplo:

```http
GET /customers?page=0&pageSize=10&orderBy=desc&cpf=12345678900
```

Resposta esperada:

- `200 OK` com lista paginada de clientes
- `400 Bad Request` quando `page`, `pageSize` ou `orderBy` forem invalidos

### Atualizar cliente

`PUT /customers/{customerId}`

Exemplo de body:

```json
{
  "name": "Maria Souza",
  "email": "maria.souza@email.com"
}
```

Comportamento atual:

- atualiza apenas os campos enviados no body
- atualiza o campo de data de modificacao
- retorna `404 Not Found` se o cliente nao existir
- retorna `409 Conflict` se o novo CPF ou email ja pertencer a outro cliente

### Deletar cliente

`DELETE /customers/{customerId}`

Resposta esperada:

- `204 No Content` quando o cliente for removido com sucesso
- `404 Not Found` quando o cliente nao existir

## Regras de negocio importantes

- `id` e gerado automaticamente pelo banco
- `cpf` deve ser unico
- `email` deve ser unico
- a unicidade e protegida tanto na aplicacao quanto no banco de dados
- parametros invalidos de paginacao retornam erro `400 Bad Request`

## Como executar o projeto

### Pre-requisitos

- Java 17 instalado
- Maven Wrapper disponivel no projeto

### Passos

1. Clone o repositorio
2. Entre na pasta do projeto
3. Execute o comando:

```bash
./mvnw spring-boot:run
```

No Windows, use:

```bat
mvnw.cmd spring-boot:run
```

A aplicacao sera iniciada localmente.

## Banco de dados

O projeto usa H2 como banco em memoria durante a execucao.

Se desejar, voce pode evoluir o projeto depois para PostgreSQL ou MySQL.

## Aprendizados praticados neste projeto

- construcao de API REST com Spring Boot
- modelagem basica de entidade
- uso de DTOs
- paginacao com `PageRequest`
- filtros com `@Query`
- validacao de entrada
- tratamento de erros HTTP
- prevencao de duplicidade de dados
- organizacao em camadas

## Melhorias futuras

- adicionar testes unitarios e de integracao
- criar exceptions customizadas para a API
- adicionar documentacao com Swagger/OpenAPI
- integrar com banco relacional persistente
- implementar validacoes com Bean Validation
- criar endpoint para buscar cliente por id

## Autor

Projeto desenvolvido por [Danillo Santos].
